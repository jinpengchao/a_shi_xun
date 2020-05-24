package com.vhome.vhome.parents.fragment.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.adapter.AdminMessageAdapter;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.user.entity.AdminMessage;
import com.vhome.vhome.user.personal.ShowMessageDetailActivity;
import com.vhome.vhome.util.ConnectionUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PrivateMessageFragment extends Fragment implements AbsListView.OnScrollListener {
    private final static String TAG = "HotspotFragment";
    private ListView lvAdminMessage;
    private View view;
    private Handler handler;
    private List<PostBean> list = new ArrayList<>();
    private List<AdminMessage> adminMessageList = new ArrayList<>();
    private AdminMessageAdapter adapter;
    private List<AdminMessage> loadList = new ArrayList<>();
    private SharedPreferences sp;
    private TextView tvEmpty;
    private SmartRefreshLayout srl;
    private int firstPosition; //滑动以后的可见的第一条数据
    private int top;//滑动以后的第一条item的可见部分距离top的像素值
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    private int loadNum = 0;
    private String content="啊哦~数据走丢了o(╥﹏╥)o";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_message,null);
        getViews();
        lvAdminMessage.setOnScrollListener(this);
        tvEmpty.setText("还没有消息~");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Bundle c = msg.getData();
                        String datac = c.getString("data1");
                        Gson gsonc = new Gson();
                        list = gsonc.fromJson(datac,new TypeToken<List<PostBean>>(){}.getType());;
                        break;
                    case 2:
                        Bundle d = msg.getData();
                        String datad = d.getString("data2");
                        content = datad;
                        break;
                    case 3:
                        Bundle b = msg.getData();
                        String data = b.getString("data3");
                        Gson gson = new Gson();
                        adminMessageList.clear();
                        adminMessageList = gson.fromJson(data,new TypeToken<List<AdminMessage>>(){}.getType());;

                        adapter = new AdminMessageAdapter(getContext(),adminMessageList,R.layout.item_admin_message);
                        lvAdminMessage.setAdapter(adapter);

                        lvAdminMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.findViewById(R.id.unread_msg_number).setVisibility(View.INVISIBLE);
                                changeReadable(adminMessageList.get(i).getId());
                                Intent simple = new Intent();

                                if (adminMessageList.get(i).getPostId()>0){
                                    for(int t=0;t<list.size();t++){
                                        Log.e("listlist",list.get(t).getId()+"");
                                        if((""+list.get(t).getId()).equals(""+adminMessageList.get(i).getPostId())){
                                            simple.putExtra("post",list.get(t));
                                            simple.putExtra("personId",adminMessageList.get(i).getPersonId());
                                            simple.setClass(getContext(), CommentActivity.class);
                                            startActivity(simple);
                                        }
                                    }
                                }else if(adminMessageList.get(i).getPostId()<0){
                                    String content = adminMessageList.get(i).getContent_answer();
                                    Log.e("contentss",content+"**");
                                    simple.putExtra("content",content);
                                    simple.setClass(getContext(), ShowMessageDetailActivity.class);
                                    startActivity(simple);
                                }
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                }
                lvAdminMessage.setEmptyView(tvEmpty);

            }
        };
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

        return view;
    }
    public final void getpostdata() {
        SharedPreferences sp = getActivity().getSharedPreferences((new MyApp()).getPathInfo(), MODE_PRIVATE);
        final String personId = sp.getString("id","");
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetMyPostServlet?personId="+personId);
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    Log.e("datahhh",data);
                    util.sendMsg(data,1,handler);
                    Bundle bundle = new Bundle();
                    bundle.putString("data1", data);
                    Message msg = new Message();
                    msg.setData(bundle);
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public final void changeReadable(int id) {
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/ChangeAdminMessageReadable");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url, id+"");
                    util.getData(connection);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public final void getdata() {
        adminMessageList.clear();
        SharedPreferences sp = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        String phone = sp.getString("phone","");
        String data = phone;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/SearchAdminMessage");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url, data);
                    //获取数据
                    String data = util.getData(connection);
                    Bundle bundle = new Bundle();
                    bundle.putString("data3", data);
                    Message msg = new Message();
                    msg.setData(bundle);
                    msg.what = 3;
                    handler.sendMessage(msg);
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
        srl = view.findViewById(R.id.srl);
        tvEmpty = view.findViewById(R.id.tv_empty);
        lvAdminMessage = view.findViewById(R.id.lv_hot_spot);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("hotspot","调用了onResume方法");
        getpostdata();
        getdata();
    }
    @Override
    public void onStart() {
        super.onStart();
        getdata();
    }

    //刷新数据
    public void refreshData(){
        getdata();
    }
    //加载数据
    public void loadMoreData(){
        //加载五条数据，不够五条时剩余数据全部加入
        if(loadNum+5>=list.size()){
            for (int i =loadNum;i<adminMessageList.size();i++){
                loadList.add(adminMessageList.get(i));
            }
        }else {
            for (int i =loadNum;i<loadNum+5;i++){
                loadList.add(adminMessageList.get(i));
            }
        }
        loadNum = loadNum+5;
        Log.e("更新数","loadNum"+loadNum);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            firstPosition=lvAdminMessage.getFirstVisiblePosition();
        }
        View v=lvAdminMessage.getChildAt(0);
        top=v.getTop();

        editor.putInt("firstPosition", firstPosition);
        editor.putInt("top", top);
        editor.commit();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
