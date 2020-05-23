package com.vhome.vhome.parents.fragment;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

import com.bumptech.glide.signature.ObjectKey;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.vhome.chat.Constant;
import com.vhome.chat.DemoHelper;
import com.vhome.chat.ui.ChatActivity;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.fragment.dialog.MyDialog;
import com.vhome.vhome.parents.fragment.alarm.AlarmService;
import com.vhome.vhome.parents.fragment.myself.MyAttentionsActivity;
import com.vhome.vhome.parents.fragment.myself.MyCollectionsActivity;
import com.vhome.vhome.parents.fragment.myself.MyFunsActivity;
import com.vhome.vhome.parents.fragment.myself.MyNewsActivity;
import com.vhome.vhome.parents.fragment.myself.MyPostActivity;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import com.vhome.vhome.user.personal.MySelfActivity;
import com.vhome.vhome.user.personal.MyTitle;
import com.vhome.vhome.user.personal.NewLeaveMessageActivity;
import com.vhome.vhome.user.personal.widget.CircleImageView;
import com.vhome.vhome.util.ConnectionUtil;
import com.vhome.chat.ui.SettingActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MyselfFragment extends BaseFragment {
    private ImageView blurImageView;
    private RelativeLayout title;
    private CircleImageView header;
    private TextView nikeName;
    private TextView ids;
    private TextView areas;
    private ImageView sexs;
    private RelativeLayout toKefu;
    private SharedPreferences sp1;
    private SharedPreferences sp2;
    private RelativeLayout settings;
//    private RelativeLayout myResetPwd;
    private Button myLogout;
    private Dialog myDialog;
    private RelativeLayout tvMyPost;
    private RelativeLayout tvMyCollect;
    private RelativeLayout tvMyNews;
    private RelativeLayout leave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_myself,null);
        getViews(view);
        initData();
        initMyselfInfo();

        toKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "kefuxw";
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyTitle.class));
            }
        });
//        myResetPwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getActivity().startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
//            }
//        });
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
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MySelfActivity.class);
                String id = sp2.getString("id","00");
                intent.putExtra("personId",id);
                getActivity().startActivity(intent);
            }
        });
        tvMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyPostActivity.class);
                startActivity(intent);
            }
        });
        tvMyCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyCollectionsActivity.class);
                startActivity(intent);
            }
        });
        tvMyNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyNewsActivity.class);
                startActivity(intent);
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), NewLeaveMessageActivity.class);
                startActivity(intent);
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
        title = (RelativeLayout) view.findViewById(R.id.title_head);
        header = (CircleImageView) view.findViewById(R.id.parent_head);
        nikeName = (TextView) view.findViewById(R.id.parent_name);
        ids = (TextView) view.findViewById(R.id.parent_id);
//        areas = (TextView) view.findViewById(R.id.parent_area);
//        sexs = (ImageView) view.findViewById(R.id.parent_sex);
        settings = view.findViewById(R.id.settings);
        myLogout = view.findViewById(R.id.my_logout);
//        myResetPwd = view.findViewById(R.id.my_resetpwd);
//        tvAttention = view.findViewById(R.id.tv_myself_attention);
//        tvFuns = view.findViewById(R.id.tv_myself_funs);
        tvMyCollect = view.findViewById(R.id.tv_myself_mycollect);
        tvMyNews = view.findViewById(R.id.tv_myself_mynews);
        tvMyPost = view.findViewById(R.id.tv_myself_mypost);
        toKefu = view.findViewById(R.id.kefu);
        leave = view.findViewById(R.id.help);
    }
    private void initData(){
        //刷新本地头像
        sp1 = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String phone = sp1.getString("phone","");
//        String url1 = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
//        try {
//            setPicToView(phone,returnBitMap(url1));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String path = "/sdcard/header"+phone+"/";// sd路径
        Bitmap bt = BitmapFactory.decodeFile(path + "header"+phone+".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            header.setImageDrawable(drawable);
            String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
            Glide.with(getActivity())
                    .load(url)
                    .signature(new ObjectKey(UUID.randomUUID().toString()))  // 重点在这行
                    .apply(bitmapTransform(new BlurTransformation(25,3)))
                    .into(blurImageView);
        } else {
            String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
            Glide.with(getActivity())
                    .load(url)
                    .priority(Priority.HIGH)
                    .into(header);
        }

    }
    public static Bitmap returnBitMap(String url) throws IOException {
        URL imgUrl = new URL(url);
        Bitmap bitmap = null;
        final HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        return bitmap;
    }
    private static void setPicToView(String username, Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        String path = "/sdcard/header"+username+"/";
        FileOutputStream b = null;
        File file = new File(path);
        if (file.exists()){
            file.delete();
            file.mkdirs();
        }else
            file.mkdirs();// 创建文件夹
        String fileName = path + "header"+username+".jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                file.delete();
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void initMyselfInfo(){
        Log.e("缓存的个人信息","old");
        sp2 = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);

        String id = sp2.getString("phone","");
        String nickName = sp2.getString("nickName","");
        String sex = sp2.getString("sex","");
        String area = sp2.getString("area","");
        String achieve = sp2.getString("achieve","");
        String personalWord = sp2.getString("personalWord","");
        String headImg = sp1.getString("headImg","");
        nikeName.setText(nickName);
        ids.setText(personalWord);
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
        SharedPreferences sp1 = getActivity().getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        SharedPreferences.Editor editor1 = sp1.edit();
        editor.clear();
        editor1.clear();
        editor1.commit();
        editor.commit();
        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
        easeLogout();
        Intent intent2 = new Intent(getActivity(), AlarmService.class);
        getActivity().stopService(intent2);// 关闭闹钟服务
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
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initMyselfInfo();
        initData();
    }
}

