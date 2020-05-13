package com.vhome.vhome.parents.fragment.news.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vhome.chat.R;
import com.vhome.vhome.parents.HttpLogin;
import com.vhome.vhome.parents.fragment.news.NewsAdapter;
import com.vhome.vhome.parents.fragment.news.NewsBean;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShehuiFragment extends BaseFragment {
    private List<NewsBean> news=new ArrayList<>();
    private List<NewsBean> news1=new ArrayList<>();
    private ListView lvStus;
    private SmartRefreshLayout srl;
    private NewsAdapter newsAdapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 3:
                    Bundle bundle1=new Bundle();
                    bundle1=msg.getData();
                    String result1=bundle1.getString("result1");
                    //显示jason数据
//                    Toast.makeText(getApplication(),result1,Toast.LENGTH_LONG).show();
                    try{
                        JSONObject jsonObject=new JSONObject(result1);
                        JSONObject jsonObject2=jsonObject.getJSONObject("result");
                        JSONArray jsonArray=jsonObject2.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            NewsBean newsBean=new NewsBean();
                            newsBean.setUniquekey(jsonObject1.getString("uniquekey"));
                            newsBean.setTitle(jsonObject1.getString("title"));
                            newsBean.setDate(jsonObject1.getString("date"));
                            newsBean.setAuthor_name(jsonObject1.getString("author_name"));
                            newsBean.setCategory("社会");
                            newsBean.setUrl(jsonObject1.getString("url"));
                            newsBean.setThumbnail_pic_s(jsonObject1.getString("thumbnail_pic_s"));
                            news.add(newsBean);
                        }
                        newsAdapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_activity, null);

        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }





        srl = (SmartRefreshLayout)view.findViewById(R.id.srl);
        srl.setReboundDuration(1000);
        load();
        lvStus= (ListView)view.findViewById(R.id.lv_data);
        newsAdapter=new NewsAdapter(this.getActivity(),news);
        lvStus.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
        lvStus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.get(i).getUrl()));//用于
                //intent正在操作的数据，数据的形式通常是URi.parse()解析产生的
                startActivity(intent);
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
                Toast.makeText(getActivity().getApplicationContext(),
                        "每5-30分钟进行一次刷新",
                        Toast.LENGTH_SHORT).show();
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(news.size()>40){
                    srl.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "页面最多显示40条新闻",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loadMoreData();
                    srl.finishLoadMore();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "加载20条数据",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        return null;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button button= (Button) getActivity().findViewById(R.id.find_news);
        final EditText editText=getActivity().findViewById(R.id.news_name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(NewsBean newsBean:news){
                    if(newsBean.getTitle().contains(editText.getText().toString())){
                        news1.add(newsBean);
                    }
                }
                if (news1==null){
                    Toast.makeText(getActivity(),"查询失败，无该新闻", Toast.LENGTH_SHORT).show();
                }else{
                    lvStus= (ListView)getActivity().findViewById(R.id.lv_data);
                    newsAdapter=new NewsAdapter(getActivity(),news1);
                    lvStus.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                }

            }
        });

    }
    private void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=httpLogin.JasonAccpt3();
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler.sendMessage(message);
            }
        }).start();
    }
    private void load1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=httpLogin.JasonAccpt5();
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler.sendMessage(message);
            }
        }).start();
    }
    public void refreshData(){
        news.clear();
        load();
        newsAdapter.notifyDataSetChanged();
    }
    public void loadMoreData(){
        load();
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.getActivity().finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
