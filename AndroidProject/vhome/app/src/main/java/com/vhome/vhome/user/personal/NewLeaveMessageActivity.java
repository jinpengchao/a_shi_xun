package com.vhome.vhome.user.personal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vhome.chat.R;
import com.vhome.chat.ui.BaseActivity;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.user.entity.NewTicketBody;
import com.vhome.vhome.user.personal.widget.AlertDialogFragment;
import com.vhome.vhome.util.ConnectionUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * 发起留言界面
 */
public class NewLeaveMessageActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = NewLeaveMessageActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private RelativeLayout sendLayout;
    private RelativeLayout successLayout;
    private EditText contentText;
    private RelativeLayout rlName;
    private RelativeLayout rlPhone;
    private RelativeLayout rlEmail;
    private RelativeLayout rlTheme;
    private EditText itemName;
    private TextView itemPhone;
    private EditText itemTheme;
    private RelativeLayout detailLayout;
    private TextView detailText;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.em_activity_newleave);
        initView();
        initListener();
    }

    private void initView() {
        sendLayout = findViewById(R.id.rl_new_leave_send);
        successLayout = findViewById(R.id.rl_new_leave_success);
        contentText = findViewById(R.id.et_new_leave_content);
        itemName = findViewById(R.id.et_name);
        rlName = findViewById(R.id.rl_name);
        itemPhone = findViewById(R.id.et_phone);
        rlPhone = findViewById(R.id.rl_phone);
        itemTheme = findViewById(R.id.et_theme);
        rlTheme = findViewById(R.id.rl_theme);
        detailLayout = findViewById(R.id.rl_detail_content);
        detailText = findViewById(R.id.tv_detail);
        contentText.requestFocus();
    }

    private void initListener() {
        findViewById(R.id.rl_back).setOnClickListener(this);
        findViewById(R.id.rl_send).setOnClickListener(this);
        //输入法回车焦点切换
        itemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    itemTheme.requestFocus();
                        return true;
                    }
                        return false;
                }
            });
        SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone","");
        itemPhone.setText(phone);
        itemTheme.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    itemTheme.clearFocus();
                    return true;
                }
                return false;
            }
        });

        /* 4个内容的点击区域过小 点击layout可以request focus */
        rlName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName.requestFocus();
            }
        });
        rlTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemTheme.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_send:
                if (hasItemsNoValue()) {
                    Toast.makeText(NewLeaveMessageActivity.this, "请输入必填项",Toast.LENGTH_LONG);
                    return;
                }
                contentText.requestFocus();
                commitLeaveMessage();
                break;
            default:
                break;
        }
    }

    private boolean hasItemsNoValue () {
        if (itemName.getText().toString().length() == 0){
            return true;
        }
        if (itemPhone.getText().toString().length() == 0) {
            return true;
        }
        return itemTheme.getText().toString().length() == 0;
    }

    private void commitLeaveMessage(){

        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("发送中......");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

        sendLeave();
    }
    public void sendLeave() {
        //准备数据
        SharedPreferences sharedPreferences = getSharedPreferences("registrationID",MODE_PRIVATE);
        String registrationID = sharedPreferences.getString("id","");
        NewTicketBody ticketBody = new NewTicketBody();
        ticketBody.setContent(contentText.getText().toString());
        ticketBody.setSubject(itemTheme.getText().toString());
        ticketBody.setCreatorName(itemName.getText().toString());
        ticketBody.setCreatorPhone(itemPhone.getText().toString());
        ticketBody.setRegistrationId(registrationID);
        Gson gson = new Gson();
        final String data = gson.toJson(ticketBody);
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/SaveQuestions");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    final String data = util.getData(connection);

                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (data!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeDialog();
                                sendLayout.setVisibility(View.GONE);
                                contentText.setVisibility(View.GONE);
                                successLayout.setVisibility(View.VISIBLE);
                                itemName.setKeyListener(null);
                                itemPhone.setKeyListener(null);
                                itemTheme.setKeyListener(null);
                                detailLayout.setVisibility(View.VISIBLE);
                                detailText.setText(contentText.getText().toString());
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeDialog();
                                if (!NewLeaveMessageActivity.this.isFinishing()) {
                                    showAlertDialog();
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
    private void showAlertDialog() {
        FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
        String fragmentTag = "dialogFragment";
        Fragment fragment =  getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if(fragment!=null){
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mFragTransaction.remove(fragment);
        }
        final AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setTitleText("提交失败");
        dialogFragment.setContentText("请检查网络或重新提交");
        dialogFragment.setRightBtnText("好的");
        dialogFragment.show(mFragTransaction, fragmentTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDialog();
    }

    private void closeDialog(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
