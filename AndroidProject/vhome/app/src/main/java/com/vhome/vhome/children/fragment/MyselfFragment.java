package com.vhome.vhome.children.fragment;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyphenate.EMCallBack;
import com.vhome.chat.DemoHelper;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.fragment.dialog.MyDialog;
import com.vhome.vhome.parents.ParentMain;
import com.vhome.vhome.parents.fragment.alarm.AlarmService;
import com.vhome.vhome.parents.fragment.myself.MyAttentionsActivity;
import com.vhome.vhome.parents.fragment.myself.MyCollectionsActivity;
import com.vhome.vhome.parents.fragment.myself.MyFunsActivity;
import com.vhome.vhome.parents.fragment.myself.MyNewsActivity;
import com.vhome.vhome.parents.fragment.myself.MyPostActivity;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import com.vhome.vhome.user.entity.EventBean;
import com.vhome.vhome.user.personal.MySelfActivity;
import com.vhome.vhome.user.personal.PersonalEdit;
import com.vhome.vhome.user.personal.widget.CircleImageView;
import com.vhome.vhome.util.ConnectionUtil;
import com.vhome.chat.ui.SettingActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends BaseFragment {
    private ImageView blurImageView;
    private CircleImageView header;
    private TextView nikeName;
    private TextView ids;
    private ImageView sexs;
    private SharedPreferences sp2;
    private RelativeLayout settings;
    private RelativeLayout myResetPwd;
    private Button myLogout;
    private Dialog myDialog;
    private String TAG = "MyselfFragment";
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 5:
                    try {
                        initData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initMyselfInfo();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children_myself,null);
        getViews(view);
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initMyselfInfo();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        myResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });
        myLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_logout, null);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                Button cancle = (Button)view.findViewById(R.id.cancle);
                Button ok = (Button)view.findViewById(R.id.commit);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logout();
                        cancelNotification();
                        myDialog.dismiss();
                        getActivity().finish();
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), ChildEdit.class);
                startActivity(intent2);
            }
        });
        return view;
    }

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        return null;
    }

    private void getViews(View view) {
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        header = (CircleImageView) view.findViewById(R.id.parent_head);
        nikeName = (TextView) view.findViewById(R.id.parent_name);
        ids = (TextView) view.findViewById(R.id.parent_id);
        sexs = (ImageView) view.findViewById(R.id.parent_sex);
        settings = view.findViewById(R.id.settings);
        myLogout = view.findViewById(R.id.my_logout);
        myResetPwd = view.findViewById(R.id.my_resetpwd);
    }
    private void initData() throws IOException {
        SharedPreferences sp1 = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String phone = sp1.getString("phone","");
        String path = "/sdcard/header"+phone+"/";// sd路径
//        //刷新本地头像
//        String url1 = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
//        setPicToView(phone,returnBitMap(url1));
        Bitmap bt = BitmapFactory.decodeFile(path + "header"+phone+".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            header.setImageDrawable(drawable);
        } else {
            String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
            Glide.with(getActivity())
                    .load(url)
                    .priority(Priority.HIGH)
                    .into(header);

        }
    }
    public void initMyselfInfo(){
        Log.e("缓存的个人信息","old");
        sp2 = getActivity().getSharedPreferences("childUserInfo", MODE_PRIVATE);
        String id = sp2.getString("phone","");
        String nickName = sp2.getString("nickName","");
        String sex = sp2.getString("sex","");
        nikeName.setText(nickName);
        ids.setText(id);
        if (sex.equals("female")){
            sexs.setImageResource(R.mipmap.female);
        }else if (sex.equals("male")){
            sexs.setImageResource(R.mipmap.male);
        }else
            sexs.setImageResource(R.mipmap.unknown);
    }
    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) this.getActivity().getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }
    //退出登录
    public void Logout() {
        final Intent intent = new Intent();
        SharedPreferences sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences sp1 = getActivity().getSharedPreferences("childUserInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        SharedPreferences.Editor editor1 = sp1.edit();
        editor.clear();
        editor1.clear();
        editor1.commit();
        editor.commit();
        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
        easeLogout();
    }
    //环信退出登录
    void easeLogout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHelper.getInstance().logout(true,new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // show login screen
                        (getActivity()).finish();
                        startActivity(new Intent(getActivity(), MainActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(getActivity(), "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        initMyselfInfo();
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
