package h.jpc.vhome.parents.fragment.community_hotspot;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import h.jpc.vhome.parents.fragment.community_hotspot.activity.CommentActivity;

public class MyGridView extends GridView {
    private OnTouchBlankListener OnTouchBlankListener;

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //用来解决listview中gridview图片只显示一行
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    //定义回调接口
    public interface OnTouchBlankListener {
        void OnTouchBlank();
    }
    //设置空白处点击事件方法
    public void setOnTouchBlankListener(OnTouchBlankListener listener) {
        OnTouchBlankListener = listener;
        Intent comment = new Intent();
        comment.setClass(this.getContext(), CommentActivity.class);
        this.getContext().startActivity(comment);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (OnTouchBlankListener != null) {
            //这句很关键，是获取用户点击是第几个子项还是空白处
            int motionPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            //当用户点击是空白处时并抬起手指时，执行回调方法
            if (motionPosition == INVALID_POSITION && ev.getAction() == MotionEvent.ACTION_UP) {
                OnTouchBlankListener.OnTouchBlank();
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }
}
