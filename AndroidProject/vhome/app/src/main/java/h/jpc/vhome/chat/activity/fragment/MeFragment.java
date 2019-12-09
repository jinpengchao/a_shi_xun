package h.jpc.vhome.chat.activity.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.controller.MeController;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.chat.view.MeView;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class MeFragment extends BaseFragment {
    private View mRootView;
    public MeView mMeView;
    private MeController mMeController;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();

        mMeView = (MeView) mRootView.findViewById(R.id.me_view);
        mMeView.initModule(mDensity, mWidth);
        mMeController = new MeController(this, mWidth);
        mMeView.setListener(mMeController);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        UserInfo myInfo = JMessageClient.getMyInfo();
        myInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int i, String s, Bitmap bitmap) {
                if (i == 0) {
                    mMeView.showPhoto(bitmap);
                    mMeController.setBitmap(bitmap);
                }else {
                    mMeView.showPhoto(null);
                    mMeController.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rc_default_portrait));
                }
            }
        });
        mMeView.showNickName(myInfo);
        super.onResume();
    }

    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) this.getActivity().getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    //退出登录
    public void Logout() {
    }

}
