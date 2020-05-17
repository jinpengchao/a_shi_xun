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
import com.bumptech.glide.signature.StringSignature;
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

import com.vhome.chat.R;
import com.vhome.chat.ui.SettingActivity;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.ParentMain;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.myself.MyAttentionsActivity;
import com.vhome.vhome.parents.fragment.myself.MyFunsActivity;
import com.vhome.vhome.user.LoginByCodeActivity;
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

public class MySelfActivity extends AppCompatActivity {
    public static String phone;
    public static String path;
    public static String fileName;
    private Bitmap head;// 头像Bitmap
    //========================
    private Toolbar mToolBar;
    private ViewGroup titleContainer;
    private AppBarLayout mAppBarLayout;
    private ViewGroup titleCenterLayout;
    private ImageView mSettingIv, mMsgIv;
    private CircleImageView mAvater;
    private CircleImageView topAvater;
    private CommonTabLayout mTablayout;
    private NoScrollViewPager mViewPager;
    private TextView nickName;
    private TextView editInfo;
    private TextView flag;
    private ImageView genders;
    private ImageView zoom;
    private TextView titleNickname;
    private TextView tvFuns;
    private TextView tvAttention;
    private TextView ages;
    private TextView joinDate;
    private TextView loc;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private int lastState = 1;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 5:
                    initMyselfInfo();
                    Bundle bundle = msg.getData();
                    String receive = bundle.getString("data");
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(receive);
                        int attentionNum = jsonObject.getInt("attentionNum");
                        int funsNum = jsonObject.getInt("funsNum");
                        tvAttention.setText(attentionNum+"");
                        tvFuns.setText(funsNum+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        setContentView(R.layout.activity_myself);
        findId();
        initListener();
        initTab();
        initStatus();
        getCount();
        initMyselfInfo();
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        phone = sharedPreferences.getString("phone","");
        path = "/sdcard/header"+phone+"/";// sd路径
        setImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImages();
        initListener();
        initMyselfInfo();
        getCount();
    }

    /**
     * 初始化id
     */
    private void setImages(){
        Bitmap bt = BitmapFactory.decodeFile(path + "header" + phone + ".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            String url2 = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" + "bg" + phone + ".jpg";
            Glide.with(this)
                    .load(url2)
                    .priority(Priority.HIGH)
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(zoom);
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
        mSettingIv = (ImageView) findViewById(R.id.uc_setting_iv);
        mMsgIv = (ImageView) findViewById(R.id.uc_msg_iv);
        mAvater = (CircleImageView) findViewById(R.id.uc_avater);
        topAvater = (CircleImageView) findViewById(R.id.title_uc_avater);
        mTablayout = (CommonTabLayout) findViewById(R.id.uc_tablayout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.uc_viewpager);
        editInfo = (TextView) findViewById(R.id.frag_uc_follow_tv);
        nickName = (TextView) findViewById(R.id.frag_uc_nickname_tv);
        flag = (TextView) findViewById(R.id.frag_uc_interest_tv);
        genders = (ImageView) findViewById(R.id.gender);
        zoom = (ImageView) findViewById(R.id.uc_zoomiv);
        titleNickname = (TextView) findViewById(R.id.title_uc_title);
        tvFuns = (TextView) findViewById(R.id.fans);
        tvAttention = (TextView) findViewById(R.id.attentions);
        ages = (TextView) findViewById(R.id.age);
        joinDate = (TextView) findViewById(R.id.date);
        loc = (TextView) findViewById(R.id.location);
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
        SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("user",MODE_PRIVATE);
        String name =  sharedPreferences.getString("nickName","");
        String flags =  sharedPreferences.getString("personalWord","这个人很懒，没有留下个性签名");


        nickName.setText(name);
        titleNickname.setText(name);
        flag.setText(flags);
        String sex =  sharedPreferences.getString("sex","unknow");
        if (sex.equals("male")){
            Glide.with(this).load(R.drawable.man).into(genders);
        }else if(sex.equals("female")){
            Glide.with(this).load(R.drawable.woman).into(genders);
        }else
            Glide.with(this).load(R.drawable.unknowsex).into(genders);
        //计算年龄
        String birth =  sharedPreferences.getString("birthday","");
        String y[] = birth.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(y[0]);
        Date date = new Date();
        int now = Integer.parseInt(sdf.format(date));
        int age = now - year;
        ages.setText(age+" 周岁");
        //计算加入日期
        String registerTime = sharedPreferences1.getString("registerTime","");
        String t[] = registerTime.split("-");
        String joinY = t[0];
        String joinM = t[1];;
        for (int i=1;i<10;i++){
            if(joinM.equals("0"+i)){
                joinM = i+"";
            }
        }
        joinDate.setText(joinY+"年"+joinM+"月 加入");
        //设置地区
        String city = sharedPreferences.getString("area","");
        loc.setText(city);
        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialogchoosephoto(MySelfActivity.this){
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
                Intent intent1 = new Intent();
                intent1.setClass(
                        MySelfActivity.this,
                        ParentMain.class);
                startActivity(intent1);
                finish();
            }
        });
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MySelfActivity.this,PersonalEdit.class);
                startActivity(intent2);
            }
        });
        mAvater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                String phone = sharedPreferences.getString("phone","");
                Intent intent = new Intent(MySelfActivity.this, ImageShowerActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        mSettingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MySelfActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        //点击关注的人的时候,显示关注人的列表
        tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MySelfActivity.this, MyAttentionsActivity.class);
                startActivity(intent);
            }
        });
        //点击粉丝的时候，显示粉丝列表
        tvFuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MySelfActivity.this, MyFunsActivity.class);
                startActivity(intent);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                if (titleCenterLayout != null && mAvater != null && mSettingIv != null && mMsgIv != null) {
                    titleCenterLayout.setAlpha(percent);
                    StatusBarUtil.setTranslucentForImageView(MySelfActivity.this, (int) (255f * percent), null);
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
            StatusBarUtil.setTransparentForImageView(MySelfActivity.this, null);
            //这里是重设我们的title布局的topMargin，StatusBarUtil提供了重设的方法，但是我们这里有两个布局
            int statusBarHeight = getStatusBarHeight(MySelfActivity.this);
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

        mSettingIv.setAlpha(alpha);
        mMsgIv.setAlpha(alpha);

        switch (state) {
            case 1://完全展开 显示白色
                mMsgIv.setImageResource(R.mipmap.left);
                mSettingIv.setImageResource(R.mipmap.setting);
                mViewPager.setNoScroll(false);
                break;
            case 2://完全关闭 显示黑色
                mMsgIv.setImageResource(R.mipmap.left_black);
                mSettingIv.setImageResource(R.mipmap.setting_black);
                mViewPager.setNoScroll(false);
                break;
            case 0://介于两种临界值之间 显示黑色
                if (lastState != 0) {
                    mMsgIv.setImageResource(R.mipmap.left_black);
                    mSettingIv.setImageResource(R.mipmap.setting_black);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/bg"+phone+".jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        new Thread(){
                            @Override
                            public void run() {
                                uploadHeaderImg();
                            }
                        }.start();
                        saveHeaderImg();
                        setPicToView(head);// 保存在SD卡中
                        zoom.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }else
            file.mkdirs();// 创建文件夹
        String fileName = path + "bg"+phone+".jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void uploadHeaderImg(){
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/uploadHeader";
        String fileName = path + "bg"+phone+".jpg";// 图片名字
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("msg","上传图片");
        params.addBodyParameter("imgs",new File(fileName));
        params.setMultipart(true);
        x.Ext.init(getApplication());
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MySelfActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MySelfActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
    public void saveHeaderImg(){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        int type = sp.getInt("type",0);
        String[] file = path.split("/");
        fileName = file[file.length-1];
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("type",type);
            jsonObject.put("fileName",fileName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String dataTo = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/saveheaderImg");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,dataTo);
                    //获取数据
                    final String dataFrom = util.getData(connection);
                    if(null!=dataFrom){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("headImg",dataFrom);
                                editor.commit();
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
    public void initMyselfInfo(){
        Log.e("缓存的个人信息","old");
        SharedPreferences sp2 = getSharedPreferences("parentUserInfo", MODE_PRIVATE);

        String id = sp2.getString("phone","");
        String nickName = sp2.getString("nickName","");
        String sex = sp2.getString("sex","");
        String area = sp2.getString("area","");
        String achieve = sp2.getString("achieve","");
    }
    /**
     * 获取关注数和粉丝数
     */
    private void getCount() {
        new Thread(){
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
                String personId = sp.getString("id","");
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/GetCountServlet?personId="+personId);
                    ConnectionUtil util = new ConnectionUtil();
                    String receive = util.getData(url);
                    if (null!=receive&&!"".equals(receive)){
                        Log.i("数量", "run: 获得数量成功！");
                        util.sendMsg(receive,5,handler);
                    }else {
                        Log.e("数量", "run: 获取关注和粉丝数量失败" );
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

