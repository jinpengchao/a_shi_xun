package h.jpc.vhome.parents.fragment;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import h.jpc.vhome.MainActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.ContactsActivity;
import h.jpc.vhome.chat.activity.PersonalActivity;
import h.jpc.vhome.chat.activity.ResetPasswordActivity;
import h.jpc.vhome.chat.activity.fragment.BaseFragment;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends BaseFragment {
    private ImageView blurImageView;
    private Dialog mDialog;
    private ImageView header;
    public static TextView nikeName;
    private TextView ids;
    public static TextView areas;
    private ImageView sexs;
    private SharedPreferences sp2;
    private RelativeLayout myRelation;
    private RelativeLayout mySetting;
    private RelativeLayout myResetPwd;
    private RelativeLayout myLogout;
    private EventBus eventBus;
    public static String header_phone;
    private OkHttpClient okHttpClient;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    initData();
                    initMyselfInfo();
                    Log.e("handler","Handler");
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_myself,null);
        okHttpClient = new OkHttpClient();
        asyncDownOp();
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

        return view;
    }
    public void asyncDownOp() {
        new Thread(){
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        initMyselfInfo();
    }

    private void initData(){
        SharedPreferences sp = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        header_phone = sp.getString("phone","");
        String imgName = sp.getString("headImg","");
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+imgName;
        Log.e("img",imgName);
        Glide.with(getActivity()).load(url)
                .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);
        Glide.with(getActivity()).load(url)
                .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                .placeholder(R.drawable.rc_default_portrait)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(header);
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
            SharedPreferences sp1 = getActivity().getSharedPreferences("parentUserInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            SharedPreferences.Editor editor1 = sp1.edit();
            editor.clear();
            editor1.clear();
            editor1.commit();
            editor.commit();
//            File[] files = new File("/data/data/"+getActivity().getPackageName()+"/shared_prefs").listFiles();
//            if(null!=files){
//                deleteCache(files);
//                editor.commit();
//            }
            intent.setClass(getActivity(), MainActivity.class);
            startActivity(intent);
            //应用页面跳转动画
            getActivity().finish();
            getActivity().overridePendingTransition(
                    R.anim.in,//进入动画
                    R.anim.out//出去动画
            );
        } else {
            ToastUtil.shortToast(getActivity(), "退出失败");
        }
    }
//    public void deleteCache(File[] files){
//        boolean flag;
//        for(File itemFile : files){
//            flag = itemFile.delete();
//            if (flag == false) {
//                deleteCache(itemFile.listFiles());
//            }
//        }
//    }
}
