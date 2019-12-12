package h.jpc.vhome.parents.fragment.radio_ximalaya.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import h.jpc.vhome.MyApp;


public abstract class UILoader extends FrameLayout {
    private View mSuccessView;

    public enum  UIStatus{
        SUCCESS,NONE
    }
    public UIStatus mCurrentStatus= UIStatus.NONE;
    public UILoader(@NonNull Context context) {
        this(context,null);
    }
    public UILoader(@NonNull Context context, @NonNull AttributeSet attrs) {
        this(context,attrs,0);
    }
    public UILoader(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    }
    public  void updateStatus(UIStatus status){
        mCurrentStatus=status;
        MyApp.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }
    private void init() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
        if (mSuccessView== null) {
            mSuccessView=geSuccessView(this);
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mCurrentStatus== UIStatus.SUCCESS?VISIBLE:GONE);
    }

    protected abstract View geSuccessView(ViewGroup container);

}
