package h.jpc.vhome.children.fragment;

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

import java.io.File;
import java.util.UUID;
import java.util.zip.Inflater;

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
import h.jpc.vhome.chat.activity.SearchForAddFriendActivity;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.children.fragment.dialog.MyDialog;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends Fragment {
    private ImageView blurImageView;
    private ImageView header;
    private RelativeLayout myRelation;
    private RelativeLayout changePwd;
    private RelativeLayout about_me;
    private RelativeLayout add_relation;
    private Button logout;
    private MyDialog myDialog;
    private TextView ids;
    private TextView myName;
    private ImageView sexs;
    public static String header_phone;
    private SharedPreferences sp2;
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
        View view = inflater.inflate(R.layout.fragment_children_myself, null);
        asyncDownOp();
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        myName = (TextView) view.findViewById(R.id.children_name);
        myRelation = (RelativeLayout) view.findViewById(R.id.my_relation);
        changePwd = (RelativeLayout) view.findViewById(R.id.changepwd2);
        about_me = (RelativeLayout) view.findViewById(R.id.about_vhome);
        add_relation = (RelativeLayout) view.findViewById(R.id.add_relation);
        logout = (Button) view.findViewById(R.id.logout_btn);
        header = (ImageView) view.findViewById(R.id.children_head);
        ids = (TextView) view.findViewById(R.id.children_id);
        sexs = (ImageView) view.findViewById(R.id.children_sex);
        myRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ContactsActivity.class));
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PersonalActivity.class));
            }
        });
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        about_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
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
        add_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchForAddFriendActivity.class);
                intent.setFlags(1);
                getActivity().startActivity(intent);
            }
        });
        initData();
        initMyselfInfo();
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
        String id = sp2.getString("id","");
        String nickName = sp2.getString("nickName","");
        String sex = sp2.getString("sex","");
        String headImg = sp2.getString("headImg","");
        myName.setText(nickName);
        ids.setText(header_phone);
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
            Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
            intent.setClass(getActivity(), MainActivity.class);
            startActivity(intent);
            //应用页面跳转动画
            getActivity().finish();
            getActivity().overridePendingTransition(
                    R.anim.in,//进入动画
                    R.anim.out//出去动画
            );
        }
    }
}
