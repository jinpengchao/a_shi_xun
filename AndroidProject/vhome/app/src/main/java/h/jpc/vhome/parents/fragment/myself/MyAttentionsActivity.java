package h.jpc.vhome.parents.fragment.myself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.adapter.HotSpotAdapter;
import h.jpc.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.PostBean;
import h.jpc.vhome.user.entity.ParentUserInfo;
import h.jpc.vhome.util.ConnectionUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAttentionsActivity extends AppCompatActivity {
    private static final String TAG = "MyAttentionActivity";
    private ListView lvHotSpot;
    private Handler handler;
    private SmartRefreshLayout srl;
    private int loadNum = 0;
    private TextView tvEmpty;
    private List<ParentUserInfo> list = new ArrayList<>();
    private List<ParentUserInfo> loadList = new ArrayList<>();
    private  MyAttentionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attentions);

        getViews();
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
                lvHotSpot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent simple = new Intent();
//                        simple.putExtra("post",list.get(i));
//                        simple.setClass(getContext(), CommentActivity.class);
//                        startActivity(simple);
                        Toast.makeText(MyAttentionsActivity.this, "目前没有跳转", Toast.LENGTH_SHORT).show();
                    }
                });
                adapter.notifyDataSetChanged();

            }
        };
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
        lvHotSpot = findViewById(R.id.lv_hot_spot);
        srl = findViewById(R.id.srl);
        tvEmpty = findViewById(R.id.tv_empty);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"调用了onresume方法");
        getdata();
    }
}
