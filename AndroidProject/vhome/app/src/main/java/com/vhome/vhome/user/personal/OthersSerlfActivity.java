package com.vhome.vhome.user.personal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.signature.StringSignature;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.vhome.chat.Constant;
import com.vhome.chat.R;
import com.vhome.chat.ui.ChatActivity;
import com.vhome.chat.ui.SettingActivity;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.ParentMain;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.AttentionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.user.LoginByCodeActivity;
import com.vhome.vhome.user.entity.ParentUserInfo;
import com.vhome.vhome.user.entity.User;
import com.vhome.vhome.user.personal.util.Dialogchooseattention;
import com.vhome.vhome.user.personal.util.Dialogchoosephoto;
import com.vhome.vhome.user.personal.util.widget.NoScrollViewPager;
import com.vhome.vhome.user.personal.fragment.MyPostFragment;
import com.vhome.vhome.user.personal.fragment.MyFragmentPagerAdapter;
import com.vhome.vhome.user.personal.fragment.dummy.TabEntity;
import com.vhome.vhome.user.personal.widget.CircleImageView;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class OthersSerlfActivity extends AppCompatActivity {
    public static String phone;
    public static String path;
    //========================
    private Toolbar mToolBar;
    private ViewGroup titleContainer;
    private AppBarLayout mAppBarLayout;
    private ViewGroup titleCenterLayout;
    private ImageView mSettingIv,mMsgIv;
    private CircleImageView mAvater;
    private CircleImageView topAvater;
    private CommonTabLayout mTablayout;
    private NoScrollViewPager mViewPager;
    private TextView nickName;
    private TextView flag;
    private ImageView genders;
    private ImageView zoom;
    private TextView titleNickname;
    public static String personIds;
    private TextView locs;
    private TextView siChat;
    private TextView addAttention;
    private TextView ages;
    private TextView joinDate;
    private String status="";
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private int lastState = 1;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 5:
                    Bundle bundle = msg.getData();
                    String receive = bundle.getString("data");
                    status = receive;
                    if(status.equals("1")){
                        addAttention.setText("已关注√");
                        GradientDrawable myGrad = (GradientDrawable)addAttention.getBackground();
                        myGrad.setColor(ContextCompat.getColor(OthersSerlfActivity.this,R.color.attentionedColor));
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.detectFileUriExposure();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othersself);
        findId();
        Intent idInten = getIntent();
        String str = idInten.getStringExtra("headImg");
        personIds = idInten.getStringExtra("personId");
        initListener();
        initTab();
        initStatus();
        getRegister();
        getAttentionsStatus();
        String[] phoneArray=str.split("r");
        phone = phoneArray[1];
        path = "/sdcard/header"+phone+"/";// sd路径
        setImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImages();
        initListener();
        getRegister();
        getAttentionsStatus();
    }

    /**
     * 初始化id
     */
    private void setImages(){
        Bitmap bt = BitmapFactory.decodeFile(path + "header" + phone + ".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            String url2 = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" + "bg" + phone + ".jpg";
            Glide.with(this)
                    .load(url2)
                    .priority(Priority.HIGH)
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(zoom);
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            mAvater.setImageDrawable(drawable);
            topAvater.setImageDrawable(drawable);
        } else {
            String url = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" + "header" + phone + ".jpg";

            Glide.with(this)
                    .load(url)
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.rc_default_portrait)
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(mAvater);
            Glide.with(this)
                    .load(url)
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.rc_default_portrait)
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(topAvater);

        }
    }
    private void findId() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        titleContainer = (ViewGroup) findViewById(R.id.title_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        titleCenterLayout = (ViewGroup) findViewById(R.id.title_center_layout);
        mMsgIv = (ImageView) findViewById(R.id.uc_msg_iv);
        mSettingIv = (ImageView) findViewById(R.id.uc_setting_iv);
        mAvater = (CircleImageView) findViewById(R.id.uc_avater);
        topAvater = (CircleImageView) findViewById(R.id.title_uc_avater);
        mTablayout = (CommonTabLayout) findViewById(R.id.uc_tablayout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.uc_viewpager);
        nickName = (TextView) findViewById(R.id.frag_uc_nickname_tv);
        flag = (TextView) findViewById(R.id.frag_uc_interest_tv);
        genders = (ImageView) findViewById(R.id.gender);
        zoom = (ImageView) findViewById(R.id.uc_zoomiv);
        titleNickname = (TextView) findViewById(R.id.title_uc_title);
        locs = (TextView) findViewById(R.id.location);
        siChat = (TextView) findViewById(R.id.sixin);
        addAttention = (TextView) findViewById(R.id.frag_uc_follow_tv);
        ages = (TextView) findViewById(R.id.age);
        joinDate = (TextView) findViewById(R.id.date);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        fragments = getFragments();
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, getNames());

        mTablayout.setTabData(mTabEntities);
        mViewPager.setAdapter(myFragmentPagerAdapter);
    }

    /**
     * 绑定事件
     */
    private void initListener() {
        initMyselfInfo(personIds);
        mSettingIv.setVisibility(View.GONE);


        addAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("1")){
                    new Dialogchooseattention(OthersSerlfActivity.this){
                        @Override
                        public void useCamera() {
                        }
                        @Override
                        public void useGalley() {
                            addAttention.setText("+关注");
                            GradientDrawable myGrad = (GradientDrawable)addAttention.getBackground();
                            myGrad.setColor(ContextCompat.getColor(OthersSerlfActivity.this,R.color.attentionColor));
                            delAttention();
                            status = 0+"";
                            Log.i("", "onClick: 已经取消关注");
                        }
                        @Override
                        public void useCancel() {
                        }
                    }.show();
                }else {
                    addAttention();
                }
            }
        });
        siChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = phone;
                // start chat acitivity
                Intent intent = new Intent(OthersSerlfActivity.this, ChatActivity.class);
                // it's single chat
                intent.putExtra(Constant.EXTRA_USER_ID, username);
                startActivity(intent);
            }
        });
        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersSerlfActivity.this, ImageShowerActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });
        mMsgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAvater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersSerlfActivity.this, ImageShowerActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                if (titleCenterLayout != null && mAvater != null && mMsgIv != null) {
                    titleCenterLayout.setAlpha(percent);
                    StatusBarUtil.setTranslucentForImageView(OthersSerlfActivity.this, (int) (255f * percent), null);
                    if (percent == 0) {
                        groupChange(1f, 1);
                    } else if (percent == 1) {
                        if (mAvater.getVisibility() != View.GONE) {
                            mAvater.setVisibility(View.GONE);
                        }
                        groupChange(1f, 2);
                    } else {
                        if (mAvater.getVisibility() != View.VISIBLE) {
                            mAvater.setVisibility(View.VISIBLE);
                        }
                        groupChange(percent, 0);
                    }

                }
            }
        });
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化状态栏位置
     */
    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以下不支持状态栏变色
            //注意了，这里使用了第三方库 StatusBarUtil，目的是改变状态栏的alpha
            StatusBarUtil.setTransparentForImageView(OthersSerlfActivity.this, null);
            //这里是重设我们的title布局的topMargin，StatusBarUtil提供了重设的方法，但是我们这里有两个布局
            int statusBarHeight = getStatusBarHeight(OthersSerlfActivity.this);
            CollapsingToolbarLayout.LayoutParams lp1 = (CollapsingToolbarLayout.LayoutParams) titleContainer.getLayoutParams();
            lp1.topMargin = statusBarHeight;
            titleContainer.setLayoutParams(lp1);
            CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) mToolBar.getLayoutParams();
            lp2.topMargin = statusBarHeight;
            mToolBar.setLayoutParams(lp2);
        }
    }

    /**
     * @param alpha
     * @param state 0-正在变化 1展开 2 关闭
     */
    public void groupChange(float alpha, int state) {
        lastState = state;
        mMsgIv.setAlpha(alpha);

        switch (state) {
            case 1://完全展开 显示白色
                mMsgIv.setImageResource(R.mipmap.left);
                mViewPager.setNoScroll(false);
                break;
            case 2://完全关闭 显示黑色
                mMsgIv.setImageResource(R.mipmap.left_black);
                mViewPager.setNoScroll(false);
                break;
            case 0://介于两种临界值之间 显示黑色
                if (lastState != 0) {
                    mMsgIv.setImageResource(R.mipmap.left_black);
                }
                //为什么禁止滑动？在介于开关状态之间，不允许滑动，开启可能会导致不好的体验
                mViewPager.setNoScroll(true);
                break;
        }
    }


    /**
     * 获取状态栏高度
     * ！！这个方法来自StatusBarUtil,因为作者将之设为private，所以直接copy出来
     *
     * @param context context
     * @return 状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 假数据
     *
     * @return
     */
    public String[] getNames() {
        String[] mNames = new String[]{"帖子", "喜欢", "收藏"};
        for (String str : mNames) {
            mTabEntities.add(new TabEntity(String.valueOf(new Random().nextInt(200)), str));
        }
        return mNames;
    }
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyPostFragment());
        fragments.add(new MyPostFragment());
        fragments.add(new MyPostFragment());
        return fragments;
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
                                String nickNames = userInfo.getNikeName();
                                String sex = userInfo.getSex();
                                String area = userInfo.getArea();
                                String signal = userInfo.getPersonalWord();
                                String birth = userInfo.getBirthday();

                                nickName.setText(nickNames);
                                titleNickname.setText(nickNames);
                                flag.setText(signal);
                                locs.setText(area);

                                //计算年龄
                                String y[] = birth.split("-");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                                int year = Integer.parseInt(y[0]);
                                Date date = new Date();
                                int now = Integer.parseInt(sdf.format(date));
                                int age = now - year;
                                ages.setText(age+" 周岁");


                                if (sex.equals("male")){
                                    Glide.with(OthersSerlfActivity.this).load(R.drawable.man).into(genders);
                                }else if(sex.equals("female")){
                                    Glide.with(OthersSerlfActivity.this).load(R.drawable.woman).into(genders);
                                }else
                                    Glide.with(OthersSerlfActivity.this).load(R.drawable.unknowsex).into(genders);


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
    public void getRegister() {
        final User user = new User();
        user.setPhone(phone);
        Gson gson = new Gson();
        final String data = gson.toJson(user);

        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/registerTime");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject(data);
                                    String register1 = json.getString("registerTime");
                                    String t[] = register1.split("-");
                                    String joinY = t[0];
                                    String joinM = t[1];;
                                    for (int i=1;i<10;i++){
                                        if(joinM.equals("0"+i)){
                                            joinM = i+"";
                                        }
                                    }
                                    joinDate.setText(joinY+"年"+joinM+"月 加入");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
    public void getAttentionsStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        String myId = sharedPreferences.getString("id","");
        String oppositeId = personIds;
        String all = myId+"-"+oppositeId;
        final String data = all;

        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/IfAttention");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                util.sendMsg(data,5,handler);
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
    /**
     * 删除关注
     */
    public void delAttention() {
        AttentionBean attentionBean = new AttentionBean();
        attentionBean.setAttentionPersonId(personIds);
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
                        Log.i("", "run: 取消关注成功！");
                    }else {
                        Log.e("", "run: 取消关注失败！");
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
     * 添加关注
     */
    private void addAttention() {
        //准备数据
        AttentionBean attention = new AttentionBean();
        attention.setAttentionPersonId(personIds);
        SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
        attention.setPersonId(sp.getString("id",""));
//        修改UI
        addAttention.setText("已关注√");
        GradientDrawable myGrad = (GradientDrawable)addAttention.getBackground();
        myGrad.setColor(ContextCompat.getColor(OthersSerlfActivity.this,R.color.attentionedColor));
        status = 1+"";
        new Thread(){
            @Override
            public void run() {
                Gson gson = new Gson();
                String attentionData = gson.toJson(attention);
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/SaveAttentionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url,attentionData);
                    String receive = util.getData(connection);
                    if (null!=receive&&!"".equals(receive)){
                        Log.i("","添加关注成功"+attentionData);
                    }else {
                        Log.e("","添加关注出错");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}

