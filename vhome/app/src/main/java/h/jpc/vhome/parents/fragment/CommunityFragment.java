package h.jpc.vhome.parents.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.fragment.HotspotFragment;
import h.jpc.vhome.parents.fragment.fragment.BFragment;
import h.jpc.vhome.parents.fragment.fragment.CFragment;
import h.jpc.vhome.parents.fragment.fragment.DFragment;

public class CommunityFragment extends Fragment {
    private Map<String,TextView> textViewMap = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_community,null);
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
                .setIndicator(getTabSpaceView("tag1","首页"));
        fragmentTabHost.addTab(tabSpec1,
                HotspotFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2","社区"));
        fragmentTabHost.addTab(tabSpec2,
                BFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec3 = fragmentTabHost
                .newTabSpec("tag3")
                .setIndicator(getTabSpaceView("tag3","子女"));
        fragmentTabHost.addTab(tabSpec3,
                CFragment.class,
                null
        );
        //tab4
        TabHost.TabSpec tabSpec4 = fragmentTabHost
                .newTabSpec("tag4")
                .setIndicator(getTabSpaceView("tag4","我"));
        fragmentTabHost.addTab(tabSpec4,
                DFragment.class,
                null
        );
        fragmentTabHost.setCurrentTab(0);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag3":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag4":
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
        button = view.findViewById(R.id.addPost);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenet  = new Intent();
                intenet.setClass(getActivity(), NewPostActivity.class);
                startActivity(intenet);
            }
        });
        Log.e("wzw","没有显示");
        return view;
    }
    public View getTabSpaceView(String tag, String title){
        //加载布局文件
        LayoutInflater layoutInflater1 = getLayoutInflater();
        View view = layoutInflater1.inflate(R.layout.tab_space_parent,null);
        //Text对象
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        textViewMap.put(tag,textView);
        return view;
    }
}
