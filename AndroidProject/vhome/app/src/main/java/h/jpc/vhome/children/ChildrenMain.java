package h.jpc.vhome.children;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.ConversationListFragment;
import h.jpc.vhome.chat.view.ScrollControlViewPager;
//import h.jpc.vhome.children.fragment.ChatFragment;
import h.jpc.vhome.children.fragment.LocationFragment;
import h.jpc.vhome.children.fragment.MyselfFragment;
import h.jpc.vhome.children.fragment.WarnFragment;

public class ChildrenMain extends AppCompatActivity {
    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_main);
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
                .setIndicator(getTabSpaceView("tag1",R.mipmap.notwarn,"提醒"));
        fragmentTabHost.addTab(tabSpec1,
                WarnFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2",R.mipmap.notchat,"聊天"));
        fragmentTabHost.addTab(tabSpec2,
                ConversationListFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec3 = fragmentTabHost
                .newTabSpec("tag3")
                .setIndicator(getTabSpaceView("tag3",R.mipmap.notlocation,"位置"));
        fragmentTabHost.addTab(tabSpec3,
                LocationFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec4 = fragmentTabHost
                .newTabSpec("tag4")
                .setIndicator(getTabSpaceView("tag4",R.mipmap.notmyself,"我的"));
        fragmentTabHost.addTab(tabSpec4,
                MyselfFragment.class,
                null
        );
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.mipmap.warn);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.childrenColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.warn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.notchat);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.notlocation);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.notmyself);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.childrenColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.notwarn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.chat);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.notlocation);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.notmyself);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.childrenColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.notwarn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.notchat);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.location);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.notmyself);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.childrenColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag4":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.notwarn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.notchat);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.notlocation);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.myself);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.childrenColor));
                        break;
                }
            }
        });
    }
    public View getTabSpaceView(String tag, int imageResId, String title){
        //加载布局文件
        LayoutInflater layoutInflater1 = getLayoutInflater();
        View view = layoutInflater1.inflate(R.layout.tab_space_children,null);
        //获取控件对象（image对象）
        ImageView imageView = view.findViewById(R.id.icon);//去tab_space去找id
        imageView.setImageResource(imageResId);
        //Text对象
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;
    }
}
