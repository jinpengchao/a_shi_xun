package com.vhome.vhome.parents.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.SearchPostActivity;
import com.vhome.vhome.parents.fragment.fragment.HotspotFragment;
import com.vhome.vhome.parents.fragment.fragment.HealthFragment;
import com.vhome.vhome.parents.fragment.fragment.AttentionFragment;
import com.vhome.vhome.parents.fragment.radio_ximalaya.RadioActivity;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import com.vhome.vhome.parents.fragment.radio_ximalaya.fragment.RecommendFragment;
import com.vhome.vhome.user.personal.fragment.MyFragmentPagerAdapter;
import com.vhome.vhome.user.personal.fragment.MyPostFragment;
import com.vhome.vhome.user.personal.fragment.dummy.TabEntity;
import com.vhome.vhome.user.personal.util.widget.NoScrollViewPager;

import static android.content.Context.MODE_PRIVATE;

public class CommunityFragment extends Fragment{
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private CommonTabLayout mTablayout;
    private NoScrollViewPager mViewPager;
    private ImageView addPost;
    private ImageView searchPost;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_parent_community,null);
        initId(view);
        initListener();
        initTab();
        return view;
    }
    /**
     * 初始化tab
     */
    private void initTab() {
        fragments = getFragments();
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments, getNames());

        mTablayout.setTabData(mTabEntities);
        mViewPager.setAdapter(myFragmentPagerAdapter);
    }
    public void initListener(){
        searchPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenet  = new Intent();
                intenet.setClass(getActivity(), SearchPostActivity.class);
                startActivity(intenet);
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
                String status = sp.getString("status","默认？");
                if(!status.equals("封禁")) {
                    Intent intenet = new Intent();
                    intenet.setClass(getActivity(), NewPostActivity.class);
                    startActivity(intenet);
                }else{
                    Toast toast = Toast.makeText(getActivity(), "用户资料违规，禁止发贴", Toast.LENGTH_SHORT);
                    //使用setGravity() 方法设置设置 Toast 在屏幕上的位置，第一个参数设置重力位置，有 TOP. BOTTOM. START 和 END 等值，第二.三个参数用于水平和垂直方向上的偏移量
                    toast.setGravity(Gravity.TOP, 10, 180);
                    toast.show();
                }
            }
        });
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void initId(View view){
        mTablayout = (CommonTabLayout) view.findViewById(R.id.uc_tablayout);
        mViewPager = (NoScrollViewPager) view.findViewById(R.id.uc_viewpager);
        addPost = view.findViewById(R.id.add_spot);
        searchPost = view.findViewById(R.id.search_spot);
    }
    public String[] getNames() {
        String[] mNames = new String[]{"热闹事", "收音机", "关注"};
        for (String str : mNames) {
            mTabEntities.add(new TabEntity(String.valueOf(new Random().nextInt(200)), str));
        }

        return mNames;
    }
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HotspotFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new AttentionFragment());
        return fragments;
    }


}
