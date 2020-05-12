package com.vhome.vhome.user.personal.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.vhome.chat.R;

public abstract class Dialogchoosephoto extends Dialog implements View.OnClickListener{

    private Activity activity;
    private RelativeLayout tv_camera, tv_gallery, tv_cancel;

    public Dialogchoosephoto(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_postimg);

        tv_camera = (RelativeLayout) findViewById(R.id.tv_camera);
        tv_gallery = (RelativeLayout) findViewById(R.id.tv_gallery);
        tv_cancel = (RelativeLayout) findViewById(R.id.tv_cancel);
        tv_camera.setOnClickListener(this);
        tv_gallery.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_camera:
                useCamera();
                this.cancel();
                break;
            case R.id.tv_gallery:
                useGalley();
                this.cancel();
                break;
            case R.id.tv_cancel:
                useCancel();
                this.cancel();
                break;
        }
    }

    public abstract void useCamera();
    public abstract void useGalley();
    public abstract void useCancel();

}