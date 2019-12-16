package h.jpc.vhome.parents.fragment.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.HttpLogin;

public class NewsActivity extends AppCompatActivity {
    private List<NewsBean> news=new ArrayList<>();
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
                            newsBean.setCategory(jsonObject1.getString("category"));
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }





        srl = (SmartRefreshLayout) findViewById(R.id.srl);
        srl.setReboundDuration(1000);
        load();
        lvStus= (ListView) findViewById(R.id.lv_data);
        newsAdapter=new NewsAdapter(getApplicationContext(),news);
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
                Toast.makeText(getApplicationContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(news.size()>40){
                    srl.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getApplicationContext(),
                            "页面最多显示40条新闻",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loadMoreData();
                    srl.finishLoadMore();
                    Toast.makeText(getApplicationContext(),
                            "加载20条数据",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=httpLogin.JasonAccpt1();
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler.sendMessage(message);
            }
        }).start();
    }
    private void loadnew(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=null;
                switch (getNum()){
                    case 0:
                        result=httpLogin.JasonAccpt1();
                        break;
                    case 1:
                        result=httpLogin.JasonAccpt3();
                        break;
                    case 2:
                        result=httpLogin.JasonAccpt4();
                        break;
                    case 3:
                        result=httpLogin.JasonAccpt5();
                        break;
                    case 4:
                        result=httpLogin.JasonAccpt6();
                        break;
                    case 5:
                        result=httpLogin.JasonAccpt7();
                        break;
                    case 6:
                        result=httpLogin.JasonAccpt8();
                        break;
                    case 7:
                        result=httpLogin.JasonAccpt9();
                        break;
                    case 8:
                        result=httpLogin.JasonAccpt10();
                        break;
                    case 9:
                        result=httpLogin.JasonAccpt11();
                        break;
                }
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler.sendMessage(message);
            }
        }).start();
    }
    private int getNum(){
        //新闻类型 top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
        int min=0;
        int max=9;
        Random random = new Random();
        int num = random.nextInt(max)%(max-min+1) + min;
        return num;
    }

    private void loadMore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=null;
                switch (getNum()){
                    case 0:
                        result=httpLogin.JasonAccpt1();
                        break;
                    case 1:
                        result=httpLogin.JasonAccpt3();
                        break;
                    case 2:
                        result=httpLogin.JasonAccpt4();
                        break;
                    case 3:
                        result=httpLogin.JasonAccpt5();
                        break;
                    case 4:
                        result=httpLogin.JasonAccpt6();
                        break;
                    case 5:
                        result=httpLogin.JasonAccpt7();
                        break;
                    case 6:
                        result=httpLogin.JasonAccpt8();
                        break;
                    case 7:
                        result=httpLogin.JasonAccpt9();
                        break;
                    case 8:
                        result=httpLogin.JasonAccpt10();
                        break;
                    case 9:
                        result=httpLogin.JasonAccpt11();
                        break;
                }
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
        loadnew();
        newsAdapter.notifyDataSetChanged();
    }
    public void loadMoreData(){
        loadMore();
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
