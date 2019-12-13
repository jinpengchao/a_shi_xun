package h.jpc.vhome.parents.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.BaseFragment;
import h.jpc.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import h.jpc.vhome.parents.fragment.fragment.HotspotFragment;
import h.jpc.vhome.parents.fragment.fragment.HealthFragment;
import h.jpc.vhome.parents.fragment.fragment.AttentionFragment;

public class CommunityFragment extends BaseFragment {
    private Map<String,TextView> textViewMap = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_parent_community,null);
        //获取FragmentTabHost对象
        FragmentTabHost fragmentTabHost = view.findViewById(android.R.id.tabhost);
        //初始化参数
        fragmentTabHost.setup(getActivity(),
                getChildFragmentManager(),
                android.R.id.tabcontent
        );

        //tab1
        TabHost.TabSpec tabSpec1 = fragmentTabHost
                .newTabSpec("tag1")
                .setIndicator(getTabSpaceView("tag1","热闹事"));
        fragmentTabHost.addTab(tabSpec1,
                HotspotFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2","养生居"));
        fragmentTabHost.addTab(tabSpec2,
                HealthFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec3 = fragmentTabHost
                .newTabSpec("tag3")
                .setIndicator(getTabSpaceView("tag3","关注"));
        fragmentTabHost.addTab(tabSpec3,
                AttentionFragment.class,
                null
        );

        fragmentTabHost.setCurrentTab(0);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag3":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
        return view;
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
