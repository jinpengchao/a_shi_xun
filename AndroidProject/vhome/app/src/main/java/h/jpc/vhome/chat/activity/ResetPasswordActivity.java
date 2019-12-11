package h.jpc.vhome.chat.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.utils.CommonUtils;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.util.ConnectionUtil;

public class ResetPasswordActivity extends BaseActivity {

    private EditText mOld_password;
    private EditText mNew_password;
    private EditText mRe_newPassword;
    private Button mBtn_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
        initData();

    }

    private void initView() {
        initTitle(true, true, "修改密码", "", false, "保存");
        mOld_password = (EditText) findViewById(R.id.old_password);
        mNew_password = (EditText) findViewById(R.id.new_password);
        mRe_newPassword = (EditText) findViewById(R.id.re_newPassword);
        mBtn_sure = (Button) findViewById(R.id.btn_sure);
    }

    private void initData() {
        mBtn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPsw = mOld_password.getText().toString().trim();
                String newPsw = mNew_password.getText().toString().trim();
                String reNewPsw = mRe_newPassword.getText().toString().trim();

                boolean passwordValid = JMessageClient.isCurrentUserPasswordValid(oldPsw);
                if (passwordValid) {
                    if (newPsw.equals(reNewPsw)) {
                        changePwd(reNewPsw);
                        final ProgressDialog dialog = new ProgressDialog(ResetPasswordActivity.this);
                        dialog.setMessage(getString(R.string.modifying_hint));
                        dialog.show();
                        JMessageClient.updateUserPassword(oldPsw, newPsw, new BasicCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage) {
                                dialog.dismiss();
                                if (responseCode == 0) {
                                    ToastUtil.shortToast(ResetPasswordActivity.this, "修改成功");
                                    finish();
                                }else {
                                    ToastUtil.shortToast(ResetPasswordActivity.this, "修改失败, 新密码要在4-128字节之间");
                                }
                            }
                        });
                    } else {
                        ToastUtil.shortToast(ResetPasswordActivity.this, "两次输入不相同");
                    }
                } else {
                    ToastUtil.shortToast(ResetPasswordActivity.this, "原密码不正确");
                }
            }
        });
    }
    public void changePwd(String newPwd){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pwd",newPwd);
        editor.commit();
        String phone = sp.getString("phone","");
        JSONObject json = new JSONObject();
        json.put("phone",phone);
        json.put("newPwd",newPwd);
        final String data = json.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/resetpwd");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("修改密码成功","ok");
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("pwd",data);
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

    @Override
    protected void onPause() {
        super.onPause();
        CommonUtils.hideKeyboard(this);
    }
}
