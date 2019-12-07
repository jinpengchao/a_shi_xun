package h.jpc.vhome.chat.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.ContactsActivity;
import h.jpc.vhome.chat.activity.fragment.MeFragment;

/**
 * Created by ${chenyn} on 2017/2/21.
 */

public class MeController implements View.OnClickListener {
    private MeFragment mContext;
    private int mWidth;
    private Bitmap mBitmap;

    public MeController(MeFragment context, int width) {
        this.mContext = context;
        this.mWidth = width;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                //我的关联
            case R.id.my_relation:
                mContext.startActivity(new Intent(mContext.getContext(), ContactsActivity.class));
                break;
        }
    }
}
