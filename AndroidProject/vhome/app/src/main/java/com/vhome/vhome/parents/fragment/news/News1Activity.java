package com.vhome.vhome.parents.fragment.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.vhome.chat.R;
import com.vhome.chat.ui.BaseActivity;
import com.vhome.vhome.parents.fragment.news.fragment.GuojiFragment;
import com.vhome.vhome.parents.fragment.news.fragment.GuoneiFragment;
import com.vhome.vhome.parents.fragment.news.fragment.ShehuiFragment;
import com.vhome.vhome.parents.fragment.news.fragment.TopFragment;


import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentTabHost;

public class News1Activity extends BaseActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //消去activity中的label
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_main);

        //获取FragmentTabHost对象
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);

        //初始化FragmentTabHost
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        //创建tabspec对象
        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.drawable.toutiao,"头条"));

        //自定义选项卡视图

        fragmentTabHost.addTab(tabSpec1,
                TopFragment.class,//内容页面对应的Fragment类的Class对象
                null);//传递数据时使用

        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.drawable.shehui,"社会"));

        fragmentTabHost.addTab(tabSpec2,
                ShehuiFragment.class,
                null);

        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.drawable.guonei,"国内"));

        fragmentTabHost.addTab(tabSpec3,
                GuoneiFragment.class,
                null);

        TabHost.TabSpec tabSpec4 = fragmentTabHost.newTabSpec("tag4")
                .setIndicator(getTabSpecView("tag4",R.drawable.guoji,"国际"));

        fragmentTabHost.addTab(tabSpec4,
                GuojiFragment.class,
                null);

        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.drawable.toutiao1);
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("tag2")){
                    imageViewMap.get("tag1").setImageResource(R.drawable.toutiao);
                    imageViewMap.get("tag2").setImageResource(R.drawable.shehui1);
                    imageViewMap.get("tag3").setImageResource(R.drawable.guonei);
                    imageViewMap.get("tag4").setImageResource(R.drawable.guoji);
                }else if(tabId.equals("tag3")){
                    imageViewMap.get("tag1").setImageResource(R.drawable.toutiao);
                    imageViewMap.get("tag2").setImageResource(R.drawable.shehui);
                    imageViewMap.get("tag3").setImageResource(R.drawable.guonei1);
                    imageViewMap.get("tag4").setImageResource(R.drawable.guoji);
                }else if(tabId.equals("tag1")){
                    imageViewMap.get("tag1").setImageResource(R.drawable.toutiao1);
                    imageViewMap.get("tag2").setImageResource(R.drawable.shehui);
                    imageViewMap.get("tag3").setImageResource(R.drawable.guonei);
                    imageViewMap.get("tag4").setImageResource(R.drawable.guoji);
                } else {
                    imageViewMap.get("tag1").setImageResource(R.drawable.toutiao);
                    imageViewMap.get("tag2").setImageResource(R.drawable.shehui);
                    imageViewMap.get("tag3").setImageResource(R.drawable.guonei);
                    imageViewMap.get("tag4").setImageResource(R.drawable.guoji1);
                }
            }
        });



    }

    public View getTabSpecView(String tag, int imageResId, String title){
        //加载布局文件
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tabspec_layout11,null);

        //获取控件对象
        ImageView imageView = view.findViewById(R.id.iv_icon);
        imageView.setImageResource(imageResId);

        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);

        imageViewMap.put(tag,imageView);

        return view;
    }
}
