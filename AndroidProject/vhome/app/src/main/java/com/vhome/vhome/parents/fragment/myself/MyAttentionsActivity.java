package com.vhome.vhome.parents.fragment.myself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.AttentionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.user.entity.ParentUserInfo;
import com.vhome.vhome.user.personal.OthersSerlfActivity;
import com.vhome.vhome.util.ConnectionUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.List;

public class MyAttentionsActivity extends AppCompatActivity implements AbsListView.OnScrollListener{
    private static final String TAG = "MyAttentionActivity";
    private ListView lvHotSpot;
    private Handler handler;
    private SmartRefreshLayout srl;
    private int loadNum = 0;
    private TextView tvEmpty;
    private List<ParentUserInfo> list = new ArrayList<>();
    private List<ParentUserInfo> loadList = new ArrayList<>();
    private  MyAttentionAdapter adapter;
    private int firstPosition; //滑动以后的可见的第一条数据
    private int top;//滑动以后的第一条item的可见部分距离top的像素值
    private SharedPreferences sp;//偏好设置
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attentions);

        getViews();
        lvHotSpot.setOnScrollListener(this);

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
                list = gson.fromJson(data,new TypeToken<List<ParentUserInfo>>(){}.getType());
                Log.i(TAG,"list数据个数"+list.size());
                //设置加载的数据list,默认首先加载5条数据

                if(list.size()>5){
                    for (int k=0;k<5;k++){
                        loadList.add(list.get(k));
                        loadNum++;
                    }
                    Log.i(TAG,"加载的关注人数量loadNum"+loadNum);
                }else{
                    for (int k=0;k<list.size();k++){
                        loadList.add(list.get(k));
                        loadNum++;
                    }
                }

                adapter = new MyAttentionAdapter(MyAttentionsActivity.this,loadList,R.layout.item_myself_attention);
                lvHotSpot.setAdapter(adapter);
                lvHotSpot.setEmptyView(tvEmpty);
                adapter.setOnItemListClick(new MyAttentionAdapter.onItemListClick() {
                    @Override
                    public void myItemClick(int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyAttentionsActivity.this);
                        builder.setTitle("温馨提示：");
                        builder.setMessage("确定要取消关注吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delAttention(position);
                                list.remove(i);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

                lvHotSpot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent person = new Intent();
                        person.putExtra("personId",list.get(i).getId());
                        person.setClass(MyAttentionsActivity.this, OthersSerlfActivity.class);
                        startActivity(person);
                    }
                });
                adapter.notifyDataSetChanged();

                //定位回到上一次的浏览位置
                firstPosition=sp.getInt("firstPosition", 0);
                top=sp.getInt("top", 0);
                if(firstPosition!=0&&top!=0){
                    lvHotSpot.setSelectionFromTop(firstPosition, top);
                }
            }
        };
    }

    /**
     * 删除关注人
     * @param i
     */
    public void delAttention(int i) {
        AttentionBean attentionBean = new AttentionBean();
        attentionBean.setAttentionPersonId(list.get(i).getId());
        SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),Context.MODE_PRIVATE);
        attentionBean.setPersonId(sp.getString("id",""));
        String data = (new Gson()).toJson(attentionBean);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveAttentionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url,data);
                    String receive = util.getData(connection);
                    if (null!=receive && !"".equals(receive)){
                        Log.i(TAG, "run: 取消关注成功！");
                    }else {
                        Log.e(TAG, "run: 取消关注失败！");
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

    private void getdata() {
        loadNum = 0;
        list.clear();
        loadList.clear();
        SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(), Context.MODE_PRIVATE);
        final String personId = sp.getString("id","");
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetMyAttentionsServlet?personId="+personId);
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    util.sendMsg(data,1,handler);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        sp=getPreferences(MODE_PRIVATE);
        editor=sp.edit();
        lvHotSpot = findViewById(R.id.lv_hot_spot);
        srl = findViewById(R.id.srl);
        tvEmpty = findViewById(R.id.tv_empty);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"调用了onStart方法");
        getdata();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            firstPosition=lvHotSpot.getFirstVisiblePosition();
        }
        View v=lvHotSpot.getChildAt(0);
        top=v.getTop();

        editor.putInt("firstPosition", firstPosition);
        editor.putInt("top", top);
        editor.commit();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
