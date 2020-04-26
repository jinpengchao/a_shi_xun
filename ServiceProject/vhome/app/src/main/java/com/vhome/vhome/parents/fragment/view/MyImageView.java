package com.vhome.vhome.parents.fragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {
    private static String TAG = "MyImageView";
    private int pressId;
    private int srcId;
    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attrName = attrs.getAttributeName(i);//获取属性名称
            switch (attrName) {
                //根据属性获取资源ID
                case "press":
                    pressId = attrs.getAttributeResourceValue(i, 0);
                    break;
                case "src":
                    srcId = attrs.getAttributeResourceValue(i, 0);
                    break;
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            //按下
            case MotionEvent.ACTION_DOWN:
                if (pressId != 0)
                    this.setImageResource(pressId);
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                break;
            //抬起
            case MotionEvent.ACTION_UP:
                if (srcId != 0)
                    this.setImageResource(srcId);
                break;
        }
        return super.onTouchEvent(event);
    }
}