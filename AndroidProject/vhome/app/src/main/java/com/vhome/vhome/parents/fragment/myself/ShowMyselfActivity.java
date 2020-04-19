package com.vhome.vhome.parents.fragment.myself;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.fragment.historyAdapter.AlarmBean;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.AttentionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.myself.view.MyScrollView;
import com.vhome.vhome.parents.fragment.myself.view.UnScrollListView;
import com.vhome.vhome.user.entity.ParentUserInfo;
import com.vhome.vhome.util.ConnectionUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.vhome.vhome.parents.TrackUtil.Utils.getContext;

public class ShowMyselfActivity extends AppCompatActivity {
    private String TAG = "ShowMyselfActivity";
    private MyScrollView myScrollView;
    private UnScrollListView lvHotSpot;
    private ImageView blurImageView;
    private ImageView header;
    private TextView nikeName;
    private SharedPreferences sp2;
    private TextView ids;
    private TextView areas;
    private ImageView sexs;
    private Handler handler;
    private List<PostBean> list = new ArrayList<>();
    private HotSpotAdapter adapter;
    private List<PostBean> loadList = new ArrayList<>();
    private int loadNum = 0;
    private TextView tvEmpty;
    private String personId = null;
    private String phone = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_myself);
        //获取传来的数据
        Intent idInten = getIntent();
        personId = idInten.getStringExtra("personId");
        getViews();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                list = gson.fromJson(data,new TypeToken<List<PostBean>>(){}.getType());
                Log.i("hotspotFragment","list数据个数"+list.size());
                //设置加载的数据list,默认首先加载5条数据
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

                adapter = new HotSpotAdapter(ShowMyselfActivity.this,loadList,R.layout.item_hotspot);
                lvHotSpot.setAdapter(adapter);
                lvHotSpot.setEmptyView(tvEmpty);
//                当点击收藏点赞的时候
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
                lvHotSpot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent simple = new Intent();
                        simple.putExtra("post",list.get(i));
                        simple.setClass(ShowMyselfActivity.this, CommentActivity.class);
                        startActivity(simple);
                    }
                });
                adapter.notifyDataSetChanged();
                String imgName = "header"+phone+".jpg";
                String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+imgName;
                Log.e("img====",imgName);
                Glide.with(ShowMyselfActivity.this).load(url)
                        .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                        .bitmapTransform(new BlurTransformation(ShowMyselfActivity.this, 25), new CenterCrop(ShowMyselfActivity.this))
                        .into(blurImageView);
                Glide.with(ShowMyselfActivity.this).load(url)
                        .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                        .placeholder(R.drawable.rc_default_portrait)
                        .bitmapTransform(new CropCircleTransformation(ShowMyselfActivity.this))
                        .into(header);
            }
        };
    }

    public void initMyselfInfo(String personId){
        //数据库
        final String data = personId;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/ShowOthersInfoServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                ParentUserInfo userInfo = gson.fromJson(data,ParentUserInfo.class);
                                phone = userInfo.getPhone();
                                String id = userInfo.getId();
                                String nickName = userInfo.getNikeName();
                                String sex = userInfo.getSex();
                                String area = userInfo.getArea();
                                String achieve = userInfo.getAcieve();
                                nikeName.setText(nickName);
                                ids.setText(phone);
                                areas.setText(area);
                                if (sex.equals("female")){
                                    sexs.setImageResource(R.mipmap.female);
                                }else if (sex.equals("male")){
                                    sexs.setImageResource(R.mipmap.male);
                                }else
                                    sexs.setImageResource(R.mipmap.unknown);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void addPostCollection(int i) {
        CollectionBean collection = new CollectionBean();
        PostBean post = list.get(i);
        SharedPreferences sp = getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
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
        String personId = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE).getString("id","");
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
        SharedPreferences sp = getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
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
        String personId = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE).getString("id","");
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

    private void getdata() {
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
        tvEmpty = findViewById(R.id.tv_empty);
        myScrollView = findViewById(R.id.show_scroll_view);
        blurImageView = (ImageView) findViewById(R.id.iv_blur);
        header = (ImageView) findViewById(R.id.parent_head);
        nikeName = (TextView) findViewById(R.id.parent_name);
        ids = (TextView) findViewById(R.id.parent_id);
        areas = (TextView) findViewById(R.id.parent_area);
        sexs = (ImageView) findViewById(R.id.parent_sex);
    }


    @Override
    public void onStart() {

        super.onStart();
        Log.e(TAG,"调用了onStart方法");
        getdata();
        initMyselfInfo(personId);
    }
}
