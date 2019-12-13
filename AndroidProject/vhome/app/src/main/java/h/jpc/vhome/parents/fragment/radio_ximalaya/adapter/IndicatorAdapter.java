package h.jpc.vhome.parents.fragment.radio_ximalaya.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;


import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.radio_ximalaya.RadioActivity;


public class IndicatorAdapter extends CommonNavigatorAdapter {
    private final  String[] mTitles;
    private OnIndicatorTapClickListener mOnTabClickListener;

    public IndicatorAdapter(RadioActivity context) {
        mTitles=context.getResources().getStringArray(R.array.indicator_title);
    }

    @Override
    public int getCount() {
        if(mTitles!=null)
        {
            return mTitles.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView=new ColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        colorTransitionPagerTitleView.setTextSize(18);
        colorTransitionPagerTitleView.setText(mTitles[index]);
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnTabClickListener!=null){
                    mOnTabClickListener.OnTabClick(index);
                }
            }
        });
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
       LinePagerIndicator indicator=new LinePagerIndicator(context);
       indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
       indicator.setColors(Color.parseColor("#ffffff"));
       return indicator;
    }
    public void setOnIndicatorTapClickListener(OnIndicatorTapClickListener listener){
        this.mOnTabClickListener=listener;
    }
    public interface OnIndicatorTapClickListener{
        void OnTabClick(int index);
    }
}
