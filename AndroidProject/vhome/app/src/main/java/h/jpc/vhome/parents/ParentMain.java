package h.jpc.vhome.parents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.ConversationListFragment;
import h.jpc.vhome.parents.fragment.CommunityFragment;
import h.jpc.vhome.parents.fragment.HomeFragment;
import h.jpc.vhome.parents.fragment.MyselfFragment;
import h.jpc.vhome.parents.fragment.alarm.AlarmService;
import h.jpc.vhome.user.entity.EventBean;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ximalaya.ting.android.opensdk.auth.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;


public class ParentMain extends AppCompatActivity {

    private LayoutInflater layoutInflater;
    private ViewPager viewPager;
    private Fragment[] fragments;
    private FragmentTabHost fragmentTabHost;
    private Class[] tabFragmentArray = {HomeFragment.class, CommunityFragment.class,
            ConversationListFragment.class, MyselfFragment.class};
    private Map<String,TextView> textViewMap = new HashMap<>();
    private String[] tabStringArray = {"首页", "社区", "子女", "我的"};
    private int[] tabImageNoramlArray = {
            R.mipmap.home, R.mipmap.comm,
            R.mipmap.msg, R.mipmap.me};
    private int[] tabImageSelectedArray = {
            R.mipmap.home1, R.mipmap.comm1,
            R.mipmap.msg1, R.mipmap.me1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);
        Intent intent = new Intent();
        intent.setClass(ParentMain.this, AlarmService.class);
        startService(intent);
        setTabHost();

    }

    public void setTabHost() {
        viewPager = (ViewPager) findViewById(R.id.act_main_view_pager);
        //获取FragmentTabHost对象
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.act_main_tab_host);//安卓自定义的tabhost！！
        layoutInflater = LayoutInflater.from(this);
        //初始化参数
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.act_main_view_pager);
        int count = tabStringArray.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec;
            if (i == 0) {
                //生成一个tab标签，i=0是默认选中的
                tabSpec = fragmentTabHost
                        .newTabSpec(tabStringArray[i])
                        .setIndicator(getTabItemView("tag"+i,tabImageSelectedArray[i], tabStringArray[i]));
            } else {
                tabSpec = fragmentTabHost.newTabSpec(tabStringArray[i]).setIndicator(getTabItemView("tag"+i,tabImageNoramlArray[i], tabStringArray[i]));

            }
            //去除分割线
            fragmentTabHost.getTabWidget().setDividerDrawable(null);
            //给tabspec添加fragment
            fragmentTabHost.addTab(tabSpec, tabFragmentArray[i], null);
            //给fragmentTabHost添加点击事件
            fragmentTabHost.getTabWidget().getChildTabViewAt(i).setOnClickListener(new TabOnClickListener(fragmentTabHost, i));
        }

        /**
         * 当点击Tab时，用ViewPager对fragment进行切换，否则fragment将会叠加
         */
        textViewMap.get("tag0").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int position = fragmentTabHost.getCurrentTab();
                viewPager.setCurrentItem(position);
                switch (position){
                    case 0:
                        textViewMap.get("tag0").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case 1:
                        textViewMap.get("tag0").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case 2:
                        textViewMap.get("tag0").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case 3:
                        textViewMap.get("tag0").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
        HomeFragment homeFragment = new HomeFragment();
        CommunityFragment communityFragment = new CommunityFragment();
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        MyselfFragment myselfFragment = new MyselfFragment();
        fragments = new Fragment[]{homeFragment, communityFragment, conversationListFragment, myselfFragment};

        fragmentTabHost.setCurrentTab(0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPagerListener());
    }

    /**
     * FragmentTabHost的点击事件
     */
    class TabOnClickListener implements View.OnClickListener {

        private FragmentTabHost fragmentTabHost;
        private int index;

        public TabOnClickListener(FragmentTabHost fragmentTabHost, int index) {
            this.fragmentTabHost = fragmentTabHost;
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < fragmentTabHost.getTabWidget().getTabCount(); i++) {
                View view = fragmentTabHost.getTabWidget().getChildAt(i);
                ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                if (i == index) {
                    imageView.setImageResource(tabImageSelectedArray[i]);
                } else {
                    imageView.setImageResource(tabImageNoramlArray[i]);
                }
                fragmentTabHost.setCurrentTab(index);
            }
        }
    }

    /**
     * ViewPager适配器
     * 继承自PagerAdapter，将页面信息持续的保存在fragment manager中，方便用户返回该页面
     */

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

    /**
     * ViewPager的监听事件
     * 当前选择页面发生变化时的回调接口
     */
    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < fragmentTabHost.getTabWidget().getTabCount(); i++) {
                View view = fragmentTabHost.getTabWidget().getChildAt(i);
                ImageView image = (ImageView) view.findViewById(R.id.icon);
                TextView text = (TextView) view.findViewById(R.id.tv_title);
                if (i == position) {
                    image.setImageResource(tabImageSelectedArray[i]);
                    text.setText(tabStringArray[i]);
                } else {
                    image.setImageResource(tabImageNoramlArray[i]);
                }
            }
            fragmentTabHost.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }

    public View getTabItemView(String tag , int imageResId, String stringResId) {
        View view = layoutInflater.inflate(R.layout.tab_space_parent, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView text = (TextView) view.findViewById(R.id.tv_title);
        imageView.setImageResource(imageResId);
        text.setText(stringResId);
        textViewMap.put(tag,text);
        return view;
    }
}