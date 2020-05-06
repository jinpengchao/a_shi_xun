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

public abstract class DialogChangesex extends Dialog implements View.OnClickListener{

    private Activity activity;
    private RelativeLayout man,woman,tv_cancel;

    public DialogChangesex(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_changesex);

        man = (RelativeLayout) findViewById(R.id.rl_man);
        woman = (RelativeLayout) findViewById(R.id.rl_woman);
        tv_cancel = (RelativeLayout) findViewById(R.id.tv_cancel);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);
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
            case R.id.rl_man:
                changeMan();
                this.cancel();
                break;
            case R.id.rl_woman:
                changeWoman();
                this.cancel();
                break;
            case R.id.tv_cancel:
                useCancel();
                this.cancel();
                break;
        }
    }

    public abstract void changeMan();
    public abstract void changeWoman();
    public abstract void useCancel();

}