package h.jpc.vhome;

import androidx.appcompat.app.AppCompatActivity;
import cn.smssdk.SMSSDK;
import h.jpc.vhome.children.ChildrenMain;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.user.FindBackPwdActivity;
import h.jpc.vhome.user.LoginByCodeActivity;
import h.jpc.vhome.user.RegisterActivity;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long exitTime = 0;
    private TextView register;
    private TextView use_code;
    private ToggleButton togglePwd;
    private Button pwdLogin;
    private EditText etPhone;
    private EditText etPwd;
    private TextView findBackPwd;
    private ImageView mainBackground;
    private ActionBar actionBar;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = this.getActionBar();
        getView();
        initListener();
        sp = getSharedPreferences("user", MODE_PRIVATE);
        if (sp.getString("phone","")!=null) {
            String p = sp.getString("phone", "");
            String pwd = sp.getString("pwd", "");
            int type = sp.getInt("type", 0);
            isLogin(p, pwd, type);
        }
        Glide.with(this).load(R.mipmap.mainbk1).centerCrop().into(mainBackground);
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ParentMain.class);
                startActivity(intent);
//                if (isChecked) {
//                    //如果选中，显示密码
//                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
            }
        });
    }
    public void getView(){
        register = findViewById(R.id.register);
        pwdLogin = findViewById(R.id.pwdLogin);
        etPwd = findViewById(R.id.etPwd);
        etPhone  = findViewById(R.id.etPhone);
        togglePwd = findViewById(R.id.togglePwd);
        use_code = findViewById(R.id.use_code);
        findBackPwd = findViewById(R.id.findBackPwd);
        mainBackground = findViewById(R.id.main_background);
    }
    public void initListener(){
        register.setOnClickListener(this);
        pwdLogin.setOnClickListener(this);
        use_code.setOnClickListener(this);
        findBackPwd.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwdLogin:
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
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
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
    public void isLogin(String phone,String pwd,int type){
        //存储登录状态
        editor = sp.edit();
        if(sp.getString("phone","")==""){
            editor.putString("phone",phone);
            editor.putString("pwd",pwd);
            editor.putInt("type",type);
            editor.commit();
        }if(sp.getString("phone","")!=""){
            if(sp.getString("phone",null)!=phone){
                editor.putString("phone",phone);
                editor.putString("pwd",pwd);
                editor.putInt("type",type);
                editor.commit();
            }else if(sp.getInt("type",0)==0){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ParentMain.class);
                startActivity(intent);
            }else if(sp.getInt("type",0)==1){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChildrenMain.class);
                startActivity(intent);
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
    private void loginByPsw() {
        //准备数据
        final String phoneNums = etPhone.getText().toString();
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
                                    int type1 = json.getInt("type");
                                    isLogin(p1,pwd1,type1);
                                    pwdLogin.setText("请稍候...");
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
                                etPwd.setHint("用户名或密码错误");
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
