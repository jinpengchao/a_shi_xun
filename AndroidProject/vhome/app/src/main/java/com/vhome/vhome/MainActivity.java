package com.vhome.vhome;


import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.vhome.chat.DemoHelper;
import com.vhome.chat.R;
import com.vhome.chat.db.DemoDBManager;
import com.vhome.vhome.children.ChildrenMain;
import com.vhome.vhome.children.fragment.ChildEdit;
import com.vhome.vhome.parents.ParentMain;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseActivity;
import com.vhome.vhome.user.FindBackPwdActivity;
import com.vhome.vhome.user.LoginByCodeActivity;
import com.vhome.vhome.user.RegisterActivity;
import com.vhome.vhome.user.entity.User;
import com.vhome.vhome.util.ClearWriteEditText;
import com.vhome.vhome.util.ConnectionUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vhome.chat.ui.ServiceCheckActivity;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.core.app.NotificationCompat;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    //环信登录相关↓
    private static final String TAG = "LoginActivity";
    public static final int REQUEST_CODE_SETNICK = 1;
    private boolean progressShow;
    //环信登录相关↑
    private long exitTime = 0;
    private TextView register;
    private TextView use_code;
    private ToggleButton togglePwd;
    private Button pwdLogin;
    public EditText etPhone;
    public EditText etPwd;
    private TextView findBackPwd;
    private ImageView mainBackground;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Toast mToast;
    private NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        getView();
        initListener();
        Glide.with(this).load(R.mipmap.mainbk1).centerCrop().into(mainBackground);
        Log.e("MainActivity","oncreate");
        Bundle bundle = this.getIntent().getExtras();
        if (null!=bundle) {
            String registerPhone = bundle.getString("phone");
            String registerPwd = bundle.getString("pwd");
            etPhone.setText(registerPhone);
            etPwd.setText(registerPwd);
        }
        sp = getSharedPreferences("user", MODE_PRIVATE);
        if (sp.getString("phone","")!=null) {
            String p = sp.getString("phone", "");
            String pwd = sp.getString("pwd", "");
            int type = sp.getInt("type", 0);
            String registerTime = sp.getString("registerTime", "");
            isLogin(p, pwd, type,registerTime);
        }
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    Intent jump0 = new Intent();
//                    jump0.setClass(MainActivity.this,ChildrenMain.class);
//                    startActivity(jump0);
                } else {
                    //否则隐藏密码
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    public void getView(){
        register = (TextView) findViewById(R.id.register);
        pwdLogin = (Button) findViewById(R.id.pwdLogin);
        etPwd = (ClearWriteEditText) findViewById(R.id.etPwd);
        etPhone  = (ClearWriteEditText) findViewById(R.id.etPhone);
        togglePwd = (ToggleButton) findViewById(R.id.togglePwd);
        use_code = (TextView) findViewById(R.id.use_code);
        findBackPwd = (TextView) findViewById(R.id.findBackPwd);
        mainBackground = (ImageView) findViewById(R.id.main_background);
    }
    public void initListener(){
        register.setOnClickListener(this);
        pwdLogin.setOnClickListener(this);
        use_code.setOnClickListener(this);
        findBackPwd.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwdLogin:
                Log.e("MainActivity","Onclick");
                Tongzhi();
                loginByPsw();
                break;
            case R.id.register:
                Intent intent = new Intent();
                intent.setClass(
                        MainActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.huadong_in,//进入动画
                        R.anim.huadong_out//出去动画
                );
                finish();
                break;
            case R.id.use_code:
                Intent intent1 = new Intent();
                intent1.setClass(
                        MainActivity.this,
                        LoginByCodeActivity.class);
                startActivity(intent1);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
                finish();
                break;
            case R.id.findBackPwd:
                Intent intent2 = new Intent();
                intent2.setClass(
                        MainActivity.this,
                        FindBackPwdActivity.class);
                startActivity(intent2);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
                break;
        }
    }
    public void isLogin(String phone,String pwd,int type,String registerTime){
        //存储登录状态
        editor = sp.edit();
        if(sp.getString("phone","")==""){
            editor.putString("phone",phone);
            editor.putString("pwd",pwd);
            editor.putInt("type",type);
            editor.putString("registerTime",registerTime);
            editor.commit();
        }if(sp.getString("phone","")!=""){
            if(sp.getString("phone",null)!=phone){
                editor.putString("phone",phone);
                editor.putString("pwd",pwd);
                editor.putInt("type",type);
                editor.putString("registerTime",registerTime);
                editor.commit();
            }else if(sp.getInt("type",0)==0){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ParentMain.class);
                startActivity(intent);
                overridePendingTransition(
                        R.anim.huadong_in,//进入动画
                        R.anim.huadong_out//出去动画
                );
                finish();
            }else if(sp.getInt("type",0)==1){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChildrenMain.class);
                startActivity(intent);
                overridePendingTransition(
                        R.anim.huadong_in,//进入动画
                        R.anim.huadong_out//出去动画
                );
                finish();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
    private void showToast(String msg) {
        if (mToast == null){
            mToast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }
    public void loginByPsw() {
        //准备数据
        Log.e("MainActivity", "logBypsw");
        String phoneNums;phoneNums = etPhone.getText().toString();
        final String passWords = etPwd.getText().toString();
        final User user = new User();
        user.setPhone(phoneNums);
        user.setPassword(passWords);
        Gson gson = new Gson();
        final String data = gson.toJson(user);

        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/pwdlogin");
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
                                    String p1 = json.getString("p");
                                    String pwd1 = json.getString("pwd");
                                    String register1 = json.getString("registerTime");
                                    int type1 = json.getInt("type");
                                    isLogin(p1,pwd1,type1,register1);
                                    pwdLogin.setText("请稍候...");
                                    //环信注册登录
                                    login(null);
                                    // 获取通知服务对象NotificationManager


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etPwd.setText("");
                                showToast("用户名或密码错误");
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
    public void login(View view) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, "检查网络状况！", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = etPhone.getText().toString().trim();
        String currentPassword = etPwd.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, "电话不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        MyApp.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                if (!MainActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }

                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * SDK service check
     *
     * @param v
     */
    public void serviceCheck(View v) {
        startActivity(new Intent(this, ServiceCheckActivity.class));
    }
    private void Tongzhi() {
        //获取系统提供的通知管理服务
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //判断是否为8.0以上系统，是的话新建一个通道
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //创建一个通道 一参：id  二参：name 三参：统通知的优先级
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("chId", "聊天信息", NotificationManager.IMPORTANCE_MAX);
            manager.createNotificationChannel(channel);//创建
            channel.setVibrationPattern(new long[]{0});//通道来控制震动
            tong();

        }else {
            tong();
        }
    }
    private void tong() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//获取管理类的实例
        Intent intent=new Intent(this, WelcomeNewUser.class);
        //PendingIntent点击通知后跳转，一参：context 二参：一般为0 三参：intent对象 四参：一般为0
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        Notification builder = new NotificationCompat.Builder(this, "chId")
                .setContentTitle("欢迎加入微家大家庭！")//标题
                .setContentText("您有一份微家使用说明书，请点击查看！")//内容
                .setSmallIcon(R.drawable.em_logo_uidemo)//图片
                .setContentIntent(pendingIntent)//点击通知跳转
                .setAutoCancel(true)//完成跳转自动取消通知
                .build();
        manager.notify(1, builder);//让通知显示出来
    }

}
