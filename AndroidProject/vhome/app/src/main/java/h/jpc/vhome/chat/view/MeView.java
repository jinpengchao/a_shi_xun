package h.jpc.vhome.chat.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.IntegerCallback;
import cn.jpush.im.android.api.model.UserInfo;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.utils.DialogCreator;
import h.jpc.vhome.chat.utils.ToastUtil;

/**
 * Created by ${chenyn} on 2017/2/21.
 */

public class MeView extends LinearLayout implements SlipButton.OnChangedListener{
    private Context mContext;
    private RelativeLayout mMy_Rel;
    public SlipButton mSet_noDisturb;
    private int mWidth;
    private int mHeight;

    public MeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }


    public void initModule(float density, int width) {
        mMy_Rel = (RelativeLayout) findViewById(R.id.my_relation);

        mWidth = width;
        mHeight = (int) (190 * density);


        final Dialog dialog = DialogCreator.createLoadingDialog(mContext, mContext.getString(R.string.jmui_loading));
        dialog.show();
        //初始化是否全局免打扰
        JMessageClient.getNoDisturbGlobal(new IntegerCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, Integer value) {
                dialog.dismiss();
                if (responseCode == 0) {
                    mSet_noDisturb.setChecked(value == 1);
                } else {
                    ToastUtil.shortToast(mContext, responseMessage);
                }
            }
        });


    }

    public void setListener(OnClickListener onClickListener) {
        mMy_Rel.setOnClickListener(onClickListener);
    }

    public void showPhoto(Bitmap avatarBitmap) {
    }

    public void showNickName(UserInfo myInfo) {
    }

    @Override
    public void onChanged(int id, final boolean checkState) {
        switch (id) {
        }
    }

}
