package h.jpc.vhome.parents.fragment;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import h.jpc.vhome.MainActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.ContactsActivity;
import h.jpc.vhome.chat.activity.PersonalActivity;
import h.jpc.vhome.chat.activity.ResetPasswordActivity;
import h.jpc.vhome.chat.utils.DialogCreator;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.chat.utils.photochoose.ChoosePhoto;
import h.jpc.vhome.chat.utils.photochoose.SelectableRoundedImageView;
import h.jpc.vhome.parents.fragment.myself.SettingActivity;
import h.jpc.vhome.user.entity.EventBean;
import h.jpc.vhome.user.entity.ParentUserInfo;
import h.jpc.vhome.util.ConnectionUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends Fragment {
    private ImageView blurImageView;
    private Dialog mDialog;
    private ImageView header;
    public static TextView nikeName;
    private TextView ids;
    public static TextView areas;
    public static ImageView sexs;
    private SharedPreferences sp2;
    private RelativeLayout myRelation;
    private RelativeLayout mySetting;
    private RelativeLayout myResetPwd;
    private Button myLogout;
    private EventBus eventBus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_myself,null);


        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        header = (ImageView) view.findViewById(R.id.parent_head);
        nikeName = (TextView) view.findViewById(R.id.parent_name);
        ids = (TextView) view.findViewById(R.id.parent_id);
        areas = (TextView) view.findViewById(R.id.parent_area);
        sexs = (ImageView) view.findViewById(R.id.parent_sex);
        myRelation = view.findViewById(R.id.my_relation);
//        mySetting = view.findViewById(R.id.my_setting);
        myLogout = view.findViewById(R.id.my_logout);
        myResetPwd = view.findViewById(R.id.my_resetpwd);
        //获取EventBus对象
        eventBus = EventBus.getDefault();
        myRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ContactsActivity.class));
            }
        });
        myResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });
        myLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                cancelNotification();
                getActivity().finish();
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventBus.isRegistered(getActivity())) {
                    eventBus.unregister(getActivity());
                }
                eventBus.register(getActivity());
                getActivity().startActivity(new Intent(getActivity(), PersonalActivity.class));
            }
        });
        initMyselfInfo();
        initData();
        return view;
    }
    private void initData(){

        SharedPreferences sp = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        String imgName = sp.getString("headImg","");
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+imgName;
        Log.e("img",imgName);
        Glide.with(getActivity()).load(url)
                .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);
        Glide.with(getActivity()).load(url)
                .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(header);
    }
    public void initMyselfInfo(){
            Log.e("缓存的个人信息","old");
            sp2 = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
            String id = sp2.getString("id","");
            String nickName = sp2.getString("nickName","");
            String sex = sp2.getString("sex","");
            String area = sp2.getString("area","");
            String achieve = sp2.getString("achieve","");
            String personalWord = sp2.getString("personalWord","");
            String headImg = sp2.getString("headImg","");
            nikeName.setText(nickName);
            ids.setText(id);
            areas.setText(area);
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
        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            SharePreferenceManager.setCachedUsername(info.getUserName());
            if (info.getAvatarFile() != null) {
                SharePreferenceManager.setCachedAvatarPath(info.getAvatarFile().getAbsolutePath());
            }
            JMessageClient.logout();
            SharedPreferences sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("phone","");
            editor.putString("pwd","");
            editor.putInt("type",0);
            editor.putString("test","");
            editor.commit();

            intent.setClass(getActivity(), MainActivity.class);
            startActivity(intent);
            //应用页面跳转动画
            getActivity().overridePendingTransition(
                    R.anim.in,//进入动画
                    R.anim.out//出去动画
            );
        } else {
            ToastUtil.shortToast(getActivity(), "退出失败");
        }
    }
}
