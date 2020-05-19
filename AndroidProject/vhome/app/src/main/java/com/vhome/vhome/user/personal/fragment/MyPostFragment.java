package com.vhome.vhome.user.personal.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhome.chat.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.myself.MyPostActivity;
import com.vhome.vhome.user.personal.widget.MyPostRecyclerAdapter;
import com.vhome.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;

public class MyPostFragment extends Fragment {
    private RecyclerView recyclerView;//声明RecyclerView
    private MyPostRecyclerAdapter adapter;//声明适配器
    private final static String TAG = "HotspotFragment";
    private MyClickListener listener;
    private View view;
    private Handler handler;
    private List<PostBean> list = new ArrayList<>();
    private int POST_STATUS = 1;
    private int addPostCode = 100;
    private List<PostBean> loadList = new ArrayList<>();
    private int loadNum = 0;
    private int firstPosition; //滑动以后的可见的第一条数据
    private int top;//滑动以后的第一条item的可见部分距离top的像素值
    private SharedPreferences sp;//偏好设置
    private SharedPreferences.Editor editor;

    private String personId = null;
    //============================================//
    private int mColumnCount = 1;
    private final static String PARAMS_ID = "PARAMS_ID";

    public MyPostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent idInten = getActivity().getIntent();
        personId = idInten.getStringExtra("personId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_mypost, container, false);

        getViews();
        registerListener();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        String ids = sharedPreferences.getString("id","null");

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                list = gson.fromJson(data,new TypeToken<List<PostBean>>(){}.getType());
                //设置加载的数据list,默认首先加载5条数据

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
                }else {
                    for (int k=0;k<loadNum;k++){
                        loadList.add(list.get(k));

                    }
                }

                adapter = new MyPostRecyclerAdapter(getContext(),list);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                adapter.setOnMyLikeClick(new MyPostRecyclerAdapter.onMyLikeClick() {
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
                adapter.setOnItemClickListener(new MyPostRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int i) {
                        Intent simple = new Intent();
                        simple.putExtra("post",list.get(i));
                        simple.setClass(getContext(), CommentActivity.class);
                        startActivity(simple);
                    }
                });
                adapter.notifyDataSetChanged();
                //定位回到上一次的浏览位置
                firstPosition=sp.getInt("firstPosition", 0);
                top=sp.getInt("top", 0);
                if(firstPosition!=0&&top!=0){
                    manager.scrollToPositionWithOffset(firstPosition, top);
                }

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
        getdata();
    }
    //加载数据
    public void loadMoreData(){
        //加载五条数据，不够五条时剩余数据全部加入
        if(loadNum+5>=list.size()){
            for (int i =loadNum;i<list.size();i++){
                loadList.add(list.get(i));
            }
        }else {
            for (int i =loadNum;i<loadNum+5;i++){
                loadList.add(list.get(i));
            }
        }
        loadNum = loadNum+5;
        Log.e("更新数","loadNum"+loadNum);
        adapter.notifyDataSetChanged();
    }

    public final void getdata() {
        loadNum = 0;
        list.clear();
        loadList.clear();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetMyPostServlet?personId="+personId);
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
        sp=getActivity().getPreferences(MODE_PRIVATE);
        editor=sp.edit();
        recyclerView = view.findViewById(R.id.lv_hot_spot);

    }

    private void registerListener() {
        listener = new MyClickListener();
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
        getdata();

    }

    @Override
    public void onStart() {
        super.onStart();
        getdata();
    }
    //    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
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
