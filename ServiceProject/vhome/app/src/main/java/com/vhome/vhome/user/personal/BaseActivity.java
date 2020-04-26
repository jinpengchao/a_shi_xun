package com.vhome.vhome.user.personal;

import android.os.Bundle;
import android.view.WindowManager;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.vhome.chat.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by WZJSB-01 on 2017/12/5.
 */

public class BaseActivity extends AppCompatActivity {
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initImmersionBar();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化状态栏
     */
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()  //透明状态栏，不写默认透明色
                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
                .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
                .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .supportActionBar(true) //支持ActionBar使用
                .removeSupportAllView() //移除全部view支持
                .addTag("tag")  //给以上设置的参数打标记
                .getTag("tag")  //根据tag获得沉浸式参数
                .reset()  //重置所以沉浸式参数
                .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题，默认为false
                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
//                        LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
                    }
                })
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
                .init();  //必须调用方可沉浸式
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }
}
