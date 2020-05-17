package com.vhome.vhome.user.personal;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.vhome.vhome.user.personal.widget.AlertDialogFragment;

import java.util.prefs.Preferences;

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
    private EditText itemPhone;
    private EditText itemEmail;
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
        itemEmail = findViewById(R.id.et_email);
        rlEmail = findViewById(R.id.rl_email);
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
                    itemPhone.requestFocus();
                        return true;
                    }
                        return false;
                }
            });
        itemPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    itemEmail.requestFocus();
                    return true;
                }
                return false;
            }
        });
        itemEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    itemTheme.requestFocus();
                    return true;
                }
                return false;
            }
        });
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
        rlPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPhone.requestFocus();
            }
        });
        rlEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEmail.requestFocus();
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
        if (itemEmail.getText().toString().length() == 0) {
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


//        NewTicketBody ticketBody = new NewTicketBody();
//        ticketBody.setContent(contentText.getText().toString());
//        ticketBody.setSubject(itemTheme.getText().toString());
//        NewTicketBody.CreatorBean creatorBean = new NewTicketBody.CreatorBean();
//        creatorBean.setEmail(itemEmail.getText().toString());
//        creatorBean.setName(itemName.getText().toString());
//        creatorBean.setPhone(itemPhone.getText().toString());
//        ticketBody.setCreator(creatorBean);
//
//        String target = Preferences.getInstance().getCustomerAccount();
//        String tenantId = Preferences.getInstance().getTenantId();
//        String projectId = Preferences.getInstance().getProjectId();
//        Gson gson = new Gson();
//        ChatClient.getInstance().leaveMsgManager().createLeaveMsg(gson.toJson(ticketBody).toString(), projectId, target, new ValueCallBack<String>() {
//
//            @Override
//            public void onSuccess(final String value) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeDialog();
//                        //发送成功后的操作
//                        sendLayout.setVisibility(View.GONE);
//                        contentText.setVisibility(View.GONE);
//                        successLayout.setVisibility(View.VISIBLE);
//                        itemName.setKeyListener(null);
//                        itemPhone.setKeyListener(null);
//                        itemEmail.setKeyListener(null);
//                        itemTheme.setKeyListener(null);
//                        detailLayout.setVisibility(View.VISIBLE);
//                        detailText.setText(contentText.getText().toString());
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int code, final String error) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeDialog();
//                        Log.e(TAG, "error:" + error);
//                        if (!NewLeaveMessageActivity.this.isFinishing()) {
//                            showAlertDialog();
//                        }
//                    }
//                });
//            }
//
//        });
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
