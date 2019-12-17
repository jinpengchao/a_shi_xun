package h.jpc.vhome.chat.activity.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import h.jpc.vhome.MainActivity;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.utils.DialogCreator;
import h.jpc.vhome.chat.utils.FileHelper;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.children.ChildrenMain;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.parents.fragment.MyselfFragment;
import h.jpc.vhome.parents.fragment.alarm.AlarmService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class BaseFragment extends Fragment {
    private Dialog dialog;

    private UserInfo myInfo;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    protected int mAvatarSize;
    private Context mContext;
    public Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        //订阅接收消息,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);

    }

    public void onEventMainThread(LoginStateChangeEvent event) {
        final LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        if (myInfo != null) {
            String path;
            File avatar = myInfo.getAvatarFile();
            if (avatar != null && avatar.exists()) {
                path = avatar.getAbsolutePath();
            } else {
                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
            }
            SharePreferenceManager.setCachedUsername(myInfo.getUserName());
            SharePreferenceManager.setCachedAvatarPath(path);
            JMessageClient.logout();
        }
        switch (reason) {
            case user_logout:
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.jmui_commit_btn:
                                JMessageClient.login(SharePreferenceManager.getCachedUsername(), SharePreferenceManager.getCachedPsw(), new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (responseCode == 0) {
                                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
                                            int type = sharedPreferences.getInt("type",0);
                                            if(type == 0){
                                                Intent intent = new Intent(mContext, ParentMain.class);
                                                startActivity(intent);
                                            }
                                            if (type == 1){
                                                Intent intent = new Intent(mContext, ChildrenMain.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                                break;
                            case R.id.jmui_cancel_btn:
                                Logout();
                                cancelNotification();
                                getActivity().finish();
                        }
                    }
                };
                dialog = DialogCreator.createLogoutStatusDialog(mContext, "您的账号在其他设备上登陆", listener);
                dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
    }
    //退出登录
    public void Logout() {
        final Intent intent = new Intent();
        UserInfo info = JMessageClient.getMyInfo();
        if (null == info) {
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
            intent.setClass(getActivity(), MainActivity.class);
            startActivity(intent);
            //应用页面跳转动画
            getActivity().finish();
            getActivity().overridePendingTransition(
                    R.anim.in,//进入动画
                    R.anim.out//出去动画
            );
            Intent intent2 = new Intent(getActivity(), AlarmService.class);
            getActivity().stopService(intent2);// 关闭闹钟服务
        } else {
            ToastUtil.shortToast(getActivity(), "退出失败");
        }
    }
    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) this.getActivity().getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }
    public void deleteCache(File[] files){
        boolean flag;
        for(File itemFile : files){
            flag = itemFile.delete();
            if (flag == false) {
                deleteCache(itemFile.listFiles());
            }
        }
    }
}
