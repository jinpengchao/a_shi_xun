package com.vhome.vhome.children.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vhome.chat.R;


public abstract class myBaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏，但是还是会显示系统的状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
    }

    /**
     * 获取布局文件ID
     */
    protected abstract int getContentViewId();

    /**
     * 设置Activity标题
     */
    public void setTitle(int resId) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_top);
        TextView textView = (TextView) layout.findViewById(R.id.tv_activity_title);
        textView.setText(resId);
    }

    /**
     * 设置点击监听器
     */
    public void setOnClickListener(View.OnClickListener listener) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_top);
        LinearLayout optionsButton = (LinearLayout) layout.findViewById(R.id.btn_activity_options);
        optionsButton.setOnClickListener(listener);
    }

    /**
     * 回退事件
     */
    public void onBack(View v) {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}