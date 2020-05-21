package com.vhome.vhome.parents.fragment.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;

public class HotspotFragment extends Fragment {
    private final static String TAG = "HotspotFragment";
    private RecyclerView recyclerView;
    private MyClickListener listener;
    private View view;
    private Handler handler;
    private List<PostBean> list = new ArrayList<>();
    private int POST_STATUS = 1;
    private Button addPost;
    private int addPostCode = 100;
    private HotSpotAdapter adapter;
    private SmartRefreshLayout srl;
    private List<PostBean> loadList = new ArrayList<>();
    private int loadNum = 0;
    private TextView tvEmpty;
    private int firstPosition ; //滑动以后的可见的第一条数据
    private int top;//滑动以后的第一条item的可见部分距离top的像素值

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_spot,null);
        getViews();
        registerListener();
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    View topView = mLayoutManager.getChildAt(0); //获取第一个可视的item
                    if (topView != null) {
                        top = topView.getTop();
                        firstPosition = mLayoutManager.getPosition(topView);
                    }
                    Log.i(TAG, "onScrollStateChanged: topView=" + topView
                            + "  top=" + top
                            + "  firstposition=" + firstPosition);
                }
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(loadNum >= list.size()){
                    srl.finishLoadMoreWithNoMoreData();
                }else {
                    loadMoreData();
                    srl.finishLoadMore();
                }
            }
        });
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                list = gson.fromJson(data,new TypeToken<List<PostBean>>(){}.getType());
                //设置加载的数据list,默认首先加载5条数据,否则加载定位数据
                if(0==loadNum){
                    if(list.size()>5){
                        for (int k=0;k<5;k++){
                            loadList.add(list.get(k));
                            loadNum++;
                        }
                    }else{
                        for (int k=0;k<list.size();k++){
                            loadList.add(list.get(k));
                            loadNum++;
                        }
                    }
                    adapter = new HotSpotAdapter(getContext(),loadList);
                    recyclerView.setAdapter(adapter);
                }else {
                    for (int k=0;k<loadNum;k++){
                        loadList.add(list.get(k));

                    }
                }

//                adapter = new HotSpotAdapter(getContext(),loadList,R.layout.item_hotspot);
//                lvHotSpot.setAdapter(adapter);
//                lvHotSpot.setEmptyView(tvEmpty);
                adapter.setOnMyLikeClick(new HotSpotAdapter.onMyLikeClick() {
                    @Override
                    public void myLikeClick(int position, int status) {
                        if(status==1){
                            delPostLike(position);
                        }else if(status==0){
                            addPostLike(position);
                        }else {
                            Log.e(TAG, "myLikeClick: 出错");
                        }
                    }

                    @Override
                    public void myCollectClick(int position, int status) {
                        if(status==1){
                            delPostCollection(position);
                        }else if(status==0){
                            addPostCollection(position);
                        }else {
                            Log.e(TAG, "myCollectClick: 出错");
                        }
                    }
                });
                adapter.setOnItemClickListener(new HotSpotAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int positon) {
                        Intent simple = new Intent();
                        simple.putExtra("post",list.get(positon));
                        simple.setClass(getContext(), CommentActivity.class);
                        startActivity(simple);
                    }
                });
                adapter.notifyDataSetChanged();
                //定位回到上一次的浏览位置
//                firstPosition=sp.getInt("firstPosition", 0);
//                top=sp.getInt("top", 0);
//                int position = linearLayoutManager.findFirstVisibleItemPosition();
//                View view = recyclerView.getChildAt(position);
//                if (view != null) {
//                    int top = view.getTop();
//                }
                linearLayoutManager.scrollToPositionWithOffset(firstPosition,top);

            }
        };
//        getdata();
        return view;
    }



    /**
     * 增加收藏数据
     * @param i
     */
    private void addPostCollection(int i) {
        CollectionBean collection = new CollectionBean();
        PostBean post = list.get(i);
        SharedPreferences sp = getActivity().getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
        String personId = sp.getString("id", "");
        Log.i("热点：收藏人id===", personId);
        collection.setPersonId(personId);
        collection.setPostId(post.getId());
        //首先准备收藏的数据
        Date collectDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(collectDate);
        collection.setTime(time);
        final Gson gson = new Gson();
        final String data = gson.toJson(collection);
        //开启线程保存到数据库
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + (new MyApp()).getIp() + ":8080/vhome/SaveCollectionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url, data);
                    String receive = util.getData(connection);
                    if (null != receive && !"".equals(receive)) {
                        Log.i("hotSpotAdapter", "收藏成功" + data);
                    } else {
                        Log.e("hotSpotAdapter", "收藏失败！" + data);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 删除收藏数据
     * @param position
     */
    private void delPostCollection(int position) {
        String personId = getActivity().getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE).getString("id","");
        int postId = list.get(position).getId();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveCollectServlet" +
                            "?personId="+personId+"&postId="+postId);
                    ConnectionUtil util = new ConnectionUtil();
                    String receive = util.getData(url);
                    if (receive == null) {
                        Log.e(TAG, "run: 删除收藏数据出错" );
                    }else {
                        Log.i(TAG, "run: 删除收藏数据成功");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 添加点赞数据
     * @param i
     */
    private void addPostLike(int i) {
        SharedPreferences sp = getActivity().getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
        GoodPostBean goodPost = new GoodPostBean();
        goodPost.setPostId(list.get(i).getId());
        String goodPersonId = sp.getString("id", "");
        goodPost.setGoodPersonId(goodPersonId);
        goodPost.setPublishPersonId(list.get(i).getPersonId());
        Date likeDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(likeDate);
        goodPost.setTime(time);
        Gson gson = new Gson();
        final String likeData = gson.toJson(goodPost);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/SaveGoodPostServlet");
                    ConnectionUtil connectionUtil = new ConnectionUtil();
                    HttpURLConnection connection = connectionUtil.sendData(url,likeData);
                    String receive = connectionUtil.getData(connection);
                    if (null != receive && !"".equals(receive)) {
                        Log.i("hotSpotAdapter", "点赞成功" + likeData);
                    } else {
                        Log.e("hotSpotAdapter", "点赞失败！" + likeData);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 删除点赞数据
     * @param position
     */
    private void delPostLike(int position) {
        String personId = getActivity().getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE).getString("id","");
        int postId = list.get(position).getId();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveGoodPostServlet" +
                            "?personId="+personId+"&postId="+postId);
                    ConnectionUtil util = new ConnectionUtil();
                    String receive = util.getData(url);
                    if (receive == null) {
                        Log.e(TAG, "run: 删除点赞数据出错" );
                    }else {
                        Log.i(TAG, "run: 删除点赞数据成功");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //刷新数据
    public void refreshData(){
        loadNum = 0;
        getdata();
    }
    //加载数据
    public void loadMoreData(){
        //加载5条数据，不够5条时剩余数据全部加入
        if(loadNum+5>=list.size()){
            for (int i =loadNum;i<list.size();i++){
                loadList.add(list.get(i));
                loadNum++;
            }
        }else {
            int k = loadNum;
            for (int i =k;i<k+5;i++){
                loadList.add(list.get(i));
                loadNum++;
            }
        }
        Log.e("更新数","loadNum"+loadNum);
        adapter.notifyDataSetChanged();
    }

    public final void getdata() {
        list.clear();
        loadList.clear();
        SharedPreferences sp = getActivity().getSharedPreferences((new MyApp()).getPathInfo(), MODE_PRIVATE);
        final String personId = sp.getString("id","");
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetPostsServlet?personId="+personId);
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    util.sendMsg(data,POST_STATUS,handler);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        recyclerView = view.findViewById(R.id.recyclerView);
        addPost = view.findViewById(R.id.addPost);
        srl = view.findViewById(R.id.srl);
        tvEmpty = view.findViewById(R.id.tv_empty);
    }

    private void registerListener() {
        listener = new MyClickListener();
        addPost.setOnClickListener(listener);
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.addPost:
                    Intent intenet  = new Intent();
                    intenet.setClass(getActivity(), NewPostActivity.class);
                    startActivityForResult(intenet,addPostCode);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("hotspot","调用了onResume方法");
        getdata();

    }

}
