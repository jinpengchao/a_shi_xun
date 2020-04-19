package com.vhome.vhome.parents.fragment.radio_ximalaya;

import android.os.Bundle;


import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.radio_ximalaya.adapter.IndicatorAdapter;
import com.vhome.vhome.parents.fragment.radio_ximalaya.adapter.MainContentAdapter;
import com.vhome.vhome.parents.fragment.radio_ximalaya.utils.LogUtil;


public class RadioActivity extends FragmentActivity {
    private static final String TAG="RadioActivity";
    private MagicIndicator magicIndicator;
    private ViewPager mContentPager;
    private IndicatorAdapter mIndicatorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        initView();
        initEvent();

    }
    private void initEvent() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void OnTabClick(int index) {
                LogUtil.d(TAG,"click index is -->"+index);
                mContentPager.setCurrentItem(index);
            }
        });
    }

    private void initView() {
        magicIndicator=this.findViewById(R.id.main_indicator);
        magicIndicator.setBackgroundColor(this.getResources().getColor(R.color.bg));
        //创建适配器
        mIndicatorAdapter=new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);


        mContentPager=this.findViewById(R.id.content_pager);

        FragmentManager fragmentManage=getSupportFragmentManager();
        MainContentAdapter mainContentAdapter=new MainContentAdapter(fragmentManage);
        mContentPager.setAdapter(mainContentAdapter);

        //把Viewpager和indicator绑定在一起
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mContentPager);
    }

}
