package h.jpc.vhome.parents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.ConversationListFragment;
import h.jpc.vhome.parents.fragment.CommunityFragment;
import h.jpc.vhome.parents.fragment.HomeFragment;
import h.jpc.vhome.parents.fragment.MyselfFragment;
import h.jpc.vhome.parents.fragment.alarm.AlarmService;
import h.jpc.vhome.parents.fragment.alarm.ReadAlarm;
import h.jpc.vhome.user.entity.EventBean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

public class ParentMain extends AppCompatActivity {
    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();
    private TextView coming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);
        Intent intent = new Intent();
        intent.setClass(ParentMain.this, AlarmService.class);
        startService(intent);
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
                .setIndicator(getTabSpaceView("tag1",R.mipmap.home,"首页"));
        fragmentTabHost.addTab(tabSpec1,
                HomeFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2",R.mipmap.comment,"社区"));
        fragmentTabHost.addTab(tabSpec2,
                CommunityFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec3 = fragmentTabHost
                .newTabSpec("tag3")
                .setIndicator(getTabSpaceView("tag3",R.mipmap.child,"子女"));
        fragmentTabHost.addTab(tabSpec3,
                ConversationListFragment.class,
                null
        );
        //tab4
        TabHost.TabSpec tabSpec4 = fragmentTabHost
                .newTabSpec("tag4")
                .setIndicator(getTabSpaceView("tag4",R.mipmap.me,"我"));
        fragmentTabHost.addTab(tabSpec4,
                MyselfFragment.class,
                null
        );
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.mipmap.home1);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home1);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment1);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child1);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag4":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me1);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
    }
    public View getTabSpaceView(String tag,  int imageResId,String title){
        //加载布局文件
        LayoutInflater layoutInflater1 = getLayoutInflater();
        View view = layoutInflater1.inflate(R.layout.tab_space_parent,null);
        ImageView imageView = view.findViewById(R.id.icon);//去tab_space去找id
        imageView.setImageResource(imageResId);
        //Text对象
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;
    }
    /**
     * 订阅事件(EventBean)
     * @param eventBean 发布的事件对象
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventBeanStikyEvent(EventBean eventBean){
        MyselfFragment myselfFragment = new MyselfFragment();
        myselfFragment.nikeName.setText(eventBean.getNickName());
        myselfFragment.areas.setText(eventBean.getArea());
    }
}
