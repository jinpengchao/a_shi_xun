package com.vhome.vhome.parents.fragment.myself.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决个人展示页底部的listView和上面的布局滑动问题
 */
public class UnScrollListView extends ListView {

    public UnScrollListView(Context context) {
        super(context);
    }

    public UnScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);//这里返回的是刚写好的expandSpec

    }
}