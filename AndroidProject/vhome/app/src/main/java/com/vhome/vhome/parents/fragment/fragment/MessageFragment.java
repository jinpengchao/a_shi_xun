package com.vhome.vhome.parents.fragment.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.util.ConnectionUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MessageFragment extends Fragment{
//    private String TAG = "MessageFragment";
    private View view;
//    private ListView lvHotSpot;
//    private Handler handler;
//    private List<String> list = new ArrayList<>();
//    private int POST_STATUS = 1;
//    private ArrayAdapter adapter;
//    private SmartRefreshLayout srl;
//    private List<String> loadList = new ArrayList<>();
//    private int loadNum = 0;
//    private TextView tvEmpty;
//    private int firstPosition; //滑动以后的可见的第一条数据
//    private int top;//滑动以后的第一条item的可见部分距离top的像素值
//    private SharedPreferences sp;//偏好设置
//    private SharedPreferences.Editor editor;
//    private Gson gson = new Gson();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attention,null);
//        getViews();
//        lvHotSpot.setOnScrollListener(this);
//        tvEmpty.setText("还没有消息~");
//
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what==POST_STATUS){
//                    Bundle bundle = msg.getData();
//                    String data = bundle.getString("data");
//                    list = gson.fromJson(data,new TypeToken<List<String>>(){}.getType());
//                    //设置加载的数据list,默认首先加载5条数据
//
//                    if(list.size()>5){
//                        for (int k=0;k<5;k++){
//                            loadList.add(list.get(k));
//                            loadNum++;
//                        }
//                    }else{
//                        for (int k=0;k<list.size();k++){
//                            loadList.add(list.get(k));
//                            loadNum++;
//                        }
//                    }
//
////                    绑定adapter
//                    adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,loadList);
//                    lvHotSpot.setAdapter(adapter);
//                    lvHotSpot.setEmptyView(tvEmpty);
//
//                }
//            }
//        };
//        srl.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshData();
//                srl.finishRefresh();
//            }
//        });
//        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if(loadNum >= list.size()){
//                    srl.finishLoadMoreWithNoMoreData();
//                }else {
//                    loadMoreData();
//                    srl.finishLoadMore();
//                }
//            }
//        });

        return view;
    }
//    private void getViews() {
//        sp=getActivity().getPreferences(MODE_PRIVATE);
//        editor=sp.edit();
//        lvHotSpot = view.findViewById(R.id.lv_hot_spot);
//        srl = view.findViewById(R.id.srl);
//        tvEmpty = view.findViewById(R.id.tv_empty);
//    }
//
//    /**
//     * 获取数据
//     */
//    private void getdata() {
//        loadNum = 0;
//        list.clear();
//        loadList.clear();
//        SharedPreferences sp = getActivity().getSharedPreferences((new MyApp()).getPathInfo(), Context.MODE_PRIVATE);
//        final String personId = sp.getString("id","");
//        new Thread(){
//            @Override
//            public void run() {
//                String ip = (new MyApp()).getIp();
//                try {
//                    URL url = new URL("http://"+ip+":8080/vhome/GetMessagesServlet?personId="+personId);
//                    ConnectionUtil util = new ConnectionUtil();
//                    String data = util.getData(url);
//                    util.sendMsg(data,POST_STATUS,handler);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        getdata();
//    }
//
//    //刷新数据
//    public void refreshData(){
//        getdata();
//    }
//    //加载数据
//    public void loadMoreData(){
//        //加载五条数据，不够五条时剩余数据全部加入
//        if(loadNum+5>=list.size()){
//            for (int i =loadNum;i<list.size();i++){
//                loadList.add(list.get(i));
//            }
//        }else {
//            for (int i =loadNum;i<loadNum+5;i++){
//                loadList.add(list.get(i));
//            }
//        }
//        loadNum = loadNum+5;
//        Log.e("更新数","loadNum"+loadNum);
//        adapter.notifyDataSetChanged();
//    }
//    @Override
//    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//        if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
//            firstPosition=lvHotSpot.getFirstVisiblePosition();
//        }
//        View v=lvHotSpot.getChildAt(0);
//        top=v.getTop();
//
//        editor.putInt("firstPosition", firstPosition);
//        editor.putInt("top", top);
//        editor.commit();
//    }
//
//    @Override
//    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//
//    }
}
