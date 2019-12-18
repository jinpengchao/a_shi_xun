package h.jpc.vhome.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import h.jpc.vhome.MainActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.application.JGApplication;
import h.jpc.vhome.chat.database.UserEntry;
import h.jpc.vhome.chat.utils.DialogCreator;
import h.jpc.vhome.chat.utils.HandleResponseCode;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.children.ChildrenMain;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginByCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private long exitTime = 0;
    private TextView use_pwd;
    private TextView findBackPwd;
    private TextView register;
    private ImageView loginByCodeBk;
    private EditText inputPhoneEt;
    private EditText inputCodeEt;
    private Button requestCodeBtn;
    private Button loginBtn;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    int i = 30;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                requestCodeBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                requestCodeBtn.setText("获取验证码");
                requestCodeBtn.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        loginByCode();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else
                        ((Throwable) data).printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_code);
        getView();
        Glide.with(this).load(R.mipmap.mainbk1).centerCrop().into(loginByCodeBk);
        init();
        sp = getSharedPreferences("user", MODE_PRIVATE);
        if (sp.getString("phone","")!=null) {
            String p = sp.getString("phone", "");
            String pwd = sp.getString("pwd", "");
            int type = sp.getInt("type", 0);
            isLogin(p, pwd, type);
        }
    }

    public void getView() {
        use_pwd = findViewById(R.id.use_pwd);
        inputPhoneEt = findViewById(R.id.input_phone_et);
        inputCodeEt = findViewById(R.id.input_code_et);
        requestCodeBtn = findViewById(R.id.request_code_btn);
        loginBtn = findViewById(R.id.login_btn);
        loginByCodeBk = findViewById(R.id.login_by_code_background);
        findBackPwd = findViewById(R.id.findBackPwd);
        register = findViewById(R.id.register);
    }
    /**
     * 初始化控件
     */
    private void init() {
        requestCodeBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        use_pwd.setOnClickListener(this);
        findBackPwd.setOnClickListener(this);
        register.setOnClickListener(this);
        // 启动短信验证sdk
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        String phoneNums = inputPhoneEt.getText().toString();
        switch (v.getId()) {
            case R.id.use_pwd:
                Intent intent = new Intent();
                intent.setClass(
                        LoginByCodeActivity.this,
                        MainActivity.class);
                startActivity(intent);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
                finish();
                break;
            case R.id.request_code_btn:
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                requestCodeBtn.setClickable(false);
                requestCodeBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.login_btn:
                //将收到的验证码和手机号提交再次核对
                SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt
                        .getText().toString());
                break;
            case R.id.register:
                Intent intent1 = new Intent();
                intent1.setClass(
                        LoginByCodeActivity.this,
                        RegisterActivity.class);
                startActivity(intent1);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.huadong_in,//进入动画
                        R.anim.huadong_out//出去动画
                );
                break;
        }
    }

    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
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
    public void loginByCode() {
        //准备数据
        Log.e("MainActivity", "logByCode");
        final String phoneNums = inputPhoneEt.getText().toString();
        if (TextUtils.isEmpty(phoneNums)){
            Toast.makeText(LoginByCodeActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        final User user = new User();
        user.setPhone(phoneNums);
        Gson gson = new Gson();
        final String data = gson.toJson(user);

        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/LoginByCodeServlet");
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
                                    int type1 = json.getInt("type");
                                    isLogin(p1,pwd1,type1);
                                    loginBtn.setText("请稍候...");
                                    if (JGApplication.registerOrLogin % 2 == 1) {
                                        final Dialog dialog = DialogCreator.createLoadingDialog(LoginByCodeActivity.this,
                                                getString(R.string.login_hint));
                                        dialog.show();
                                        JMessageClient.login(phoneNums, pwd1, new BasicCallback() {
                                            @Override
                                            public void gotResult(int responseCode, String responseMessage) {
                                                dialog.dismiss();
                                                if (responseCode == 0) {
                                                    SharePreferenceManager.setCachedPsw(pwd1);
                                                    UserInfo myInfo = JMessageClient.getMyInfo();
                                                    File avatarFile = myInfo.getAvatarFile();
                                                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                                                    if (avatarFile != null) {
                                                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                                                    } else {
                                                        SharePreferenceManager.setCachedAvatarPath(null);
                                                    }
                                                    String username = myInfo.getUserName();
                                                    String appKey = myInfo.getAppKey();
                                                    UserEntry user = UserEntry.getUser(username, appKey);
                                                    if (null == user) {
                                                        user = new UserEntry(username, appKey);
                                                        user.save();
                                                    }
                                                }
                                            }
                                        });
                                    } else {
                                        JMessageClient.register(phoneNums, pwd1, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 0) {
                                                    SharePreferenceManager.setRegisterName(phoneNums);
                                                    SharePreferenceManager.setRegistePass(pwd1);
                                                    startActivity(new Intent(LoginByCodeActivity.this, RegisterActivity.class));
                                                    ToastUtil.shortToast(LoginByCodeActivity.this, "注册成功");
                                                } else {
                                                    HandleResponseCode.onHandle(LoginByCodeActivity.this, i, false);
                                                }
                                            }
                                        });
                                    }
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
    public void isLogin(String phone,String pwd,int type) {
        //存储登录状态
        editor = sp.edit();
        if (sp.getString("phone", "") == "") {
            editor.putString("phone", phone);
            editor.putString("pwd", pwd);
            editor.putInt("type", type);
            editor.commit();
        }
        if (sp.getString("phone", "") != "") {
            if (sp.getString("phone", null) != phone) {
                editor.putString("phone", phone);
                editor.putString("pwd", pwd);
                editor.putInt("type", type);
                editor.commit();
            } else if (sp.getInt("type", 0) == 0) {
                Intent intent = new Intent();
                intent.setClass(LoginByCodeActivity.this, ParentMain.class);
                startActivity(intent);
                overridePendingTransition(
                        R.anim.huadong_in,//进入动画
                        R.anim.huadong_out//出去动画
                );
                finish();
            } else if (sp.getInt("type", 0) == 1) {
                Intent intent = new Intent();
                intent.setClass(LoginByCodeActivity.this, ChildrenMain.class);
                startActivity(intent);
                overridePendingTransition(
                        R.anim.huadong_in,//进入动画
                        R.anim.huadong_out//出去动画
                );
                finish();
            }
        }
    }
}
