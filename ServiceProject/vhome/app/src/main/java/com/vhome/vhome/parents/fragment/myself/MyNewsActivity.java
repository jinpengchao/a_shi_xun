package com.vhome.vhome.parents.fragment.myself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.fragment.MessageFragment;
import com.vhome.vhome.parents.fragment.fragment.PrivateMessageFragment;
import com.vhome.chat.ui.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MyNewsActivity extends BaseActivity {
    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_news);
        setTabHost();
    }
    public void setTabHost(){
        //获取FragmentTabHost对象
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化参数
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent
        );
        //tab1
        TabHost.TabSpec tabSpec1 = fragmentTabHost
                .newTabSpec("tag1")
                .setIndicator(getTabSpaceView("tag1","提示"));
        fragmentTabHost.addTab(tabSpec1,
                MessageFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2","私信"));
        fragmentTabHost.addTab(tabSpec2,
                PrivateMessageFragment.class,
                null
        );

        fragmentTabHost.setCurrentTab(0);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
    }
    public View getTabSpaceView(String tag, String title){
        //加载布局文件
        LayoutInflater layoutInflater1 = getLayoutInflater();
        View view = layoutInflater1.inflate(R.layout.tab_space_parentc,null);
        //Text对象
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        textViewMap.put(tag,textView);
        return view;
    }
}
