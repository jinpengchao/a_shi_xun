package com.vhome.vhome.user;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.vhome.chat.DemoHelper;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseActivity;
import com.vhome.vhome.user.entity.User;
import com.vhome.vhome.util.ConnectionUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ProgressDialog mDialog;
    private EditText userName;
    private EditText inputPhoneEt;
    private EditText etPwd;
    private ToggleButton togglePwd;
    private EditText inputCodeEt;
    private Button requestCodeBtn;
    private Button registerBtn;
    private RadioGroup radioGroup;
    private RadioButton rb;
    private RadioButton rb1;
    private RadioButton rb2;
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
                        registerUser();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "验证码错误！",
                                Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        getView();
        init();
        initRadioGroup();
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void getView() {
        togglePwd = (ToggleButton) findViewById(R.id.togglePwd);
        etPwd = (EditText) findViewById(R.id.etPwd);
        back = (ImageView) findViewById(R.id.backToLogin);
        userName = (EditText) findViewById(R.id.userName);
        inputPhoneEt = (EditText) findViewById(R.id.input_phone_et);
        inputCodeEt = (EditText) findViewById(R.id.input_code_et);
        requestCodeBtn = (Button) findViewById(R.id.request_code_btn);
        registerBtn = (Button) findViewById(R.id.registerOK);
        radioGroup = (RadioGroup) findViewById(R.id.rgType);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
    }

    private void init() {
        requestCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        back.setOnClickListener(this);
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
            case R.id.backToLogin:
                Intent in = new Intent();
                in.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(in);
                finish();
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
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
            case R.id.registerOK:
                //将收到的验证码和手机号提交再次核对
                SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt
                        .getText().toString());
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
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    @Override
    public void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    /**
     * 初始化监听
     */
    private void initRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectRadioBtn();
            }
        });
    }

    /**
     * 获取RadioButton选中值
     */
    private void selectRadioBtn() {
        rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
    }

    /**
     * 保存User信息
     */
    public static String getRandomId(int len) {
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }
    private void registerUser() {
        //准备数据
        String phoneNums = inputPhoneEt.getText().toString();
        String nikeNames = userName.getText().toString();
        String passWords = etPwd.getText().toString();
        String id = getRandomId(6);
        int type = -1;
        if(nikeNames.length()==0 || nikeNames.length()>=16){
            Toast.makeText(this,"请输入正确的昵称！", Toast.LENGTH_SHORT).show();
        } else if(phoneNums.length()!=11){
            Toast.makeText(this,"请检查手机号是否正确！", Toast.LENGTH_SHORT).show();
        } else if (passWords.length()<4){
            Toast.makeText(this,"密码至少4位！", Toast.LENGTH_SHORT).show();
        } else if (!rb1.isChecked() && !rb2.isChecked()){
            Toast.makeText(this,"请先选择一款需要的系统！", Toast.LENGTH_SHORT).show();
        } else {
            String rbText = rb.getText().toString();
            if(rbText.equals("父母注册")){
                type = 0;
            }else{
                type = 1;
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            final User user = new User(phoneNums, passWords, time, nikeNames, id, "", "", type);
            Gson gson = new Gson();
            final String data = gson.toJson(user);
            new Thread() {
                @Override
                public void run() {
                    String ip = (new MyApp()).getIp();
                    try {
                        URL url = new URL("http://" + ip + ":8080/vhome/register");
                        ConnectionUtil util = new ConnectionUtil();
                        //发送数据
                        HttpURLConnection connection = util.sendData(url, data);
                        //获取数据
                        final String data = util.getData(connection);
                        if (null != data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (data.equals("yes")){
                                        //易信注册
                                        register(null);
                                    }else
                                        Toast.makeText(getApplication(), "该手机号已经被注册", Toast.LENGTH_SHORT).show();
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
    public void register(View view) {
        final String username = inputPhoneEt.getText().toString().trim();
        final String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            inputPhoneEt.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            etPwd.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_registered));
            pd.show();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        // call method in SDK
                        EMClient.getInstance().createAccount(username, pwd);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!RegisterActivity.this.isFinishing())
                                    pd.dismiss();
                                // save current user
                                DemoHelper.getInstance().setCurrentUserName(username);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent();
                                i.setClass(RegisterActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", username);
                                bundle.putString("pwd", pwd);
                                i.putExtras(bundle);
                                startActivity(i);
                                finish();
                                overridePendingTransition(
                                        R.anim.in,//进入动画
                                        R.anim.out//出去动画
                                );
                                finish();
                            }
                        });
                    } catch (final HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!RegisterActivity.this.isFinishing())
                                    pd.dismiss();
                                int errorCode=e.getErrorCode();
                                if(errorCode==EMError.NETWORK_ERROR){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.EXCEED_SERVICE_LIMIT){
                                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_exceed_service_limit), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }).start();

        }
    }

}
