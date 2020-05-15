package com.vhome.vhome.user.personal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.vhome.chat.R;
import com.vhome.chat.ui.SettingActivity;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.ParentMain;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.user.LoginByCodeActivity;
import com.vhome.vhome.user.entity.ParentUserInfo;
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
    private String personId;
    private TextView locs;
    private TextView regist;
    private TextView lv;


    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private int lastState = 1;
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
        personId = idInten.getStringExtra("personId");
        initListener();
        initTab();
        initStatus();

        String[] phoneArray=str.split("r");
        phone = phoneArray[1];
        Log.e("myphonee",phone);
        path = "/sdcard/header"+phone+"/";// sd路径
        setImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImages();
        initListener();
    }

    /**
     * 初始化id
     */
    private void setImages(){
        Bitmap bt = BitmapFactory.decodeFile(path + "header" + phone + ".jpg");// 从SD卡中找头像，转换成Bitmap
        Bitmap bt2 = BitmapFactory.decodeFile(path + "bg" + phone + ".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null || bt2 != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            Drawable drawable2 = new BitmapDrawable(bt2);// 转换成drawable
            mAvater.setImageDrawable(drawable);
            topAvater.setImageDrawable(drawable);
            zoom.setImageDrawable(drawable2);
        } else {
            String url = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" + "header" + phone + ".jpg";
            String url2 = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" + "bg" + phone + ".jpg";
            Glide.with(this)
                    .load(url)
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.rc_default_portrait)
                    .into(mAvater);
            Glide.with(this)
                    .load(url)
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.rc_default_portrait)
                    .into(topAvater);
            Glide.with(this)
                    .load(url2)
                    .placeholder(R.mipmap.sss)
                    .priority(Priority.HIGH)
                    .into(zoom);
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
        regist = (TextView) findViewById(R.id.date);
        lv = (TextView) findViewById(R.id.lv_fatie);
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
        initMyselfInfo(personId);
        mSettingIv.setVisibility(View.GONE);
        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialogchoosephoto(OthersSerlfActivity.this){
                    @Override
                    public void useCamera() {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "bg"+phone+".jpg")));
                        startActivityForResult(intent2, 2);// 采用ForResult打开
                    }
                    @Override
                    public void useGalley() {
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent1, 1);
                    }

                    @Override
                    public void useCancel() {
                    }
                }.show();
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
                SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                String phone = sharedPreferences.getString("phone","");
                Intent intent = new Intent(OthersSerlfActivity.this, ImageShowerActivity.class);
                intent.putExtra("phone",phone);
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
                                //添加注册年月
                                String signal = userInfo.getPersonalWord();

                                nickName.setText(nickNames);
                                titleNickname.setText(nickNames);
                                flag.setText(signal);
                                locs.setText(area);

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
}

