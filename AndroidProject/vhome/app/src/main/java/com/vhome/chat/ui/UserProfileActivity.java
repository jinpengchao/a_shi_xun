package com.vhome.chat.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.vhome.chat.DemoHelper;
import com.vhome.chat.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.vhome.chat.domain.Relations;
import com.vhome.chat.domain.SendPerson;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.user.entity.ParentUserInfo;
import com.vhome.vhome.util.ConnectionUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends BaseActivity implements OnClickListener{

    private ImageView headAvatar;
    private TextView tvUsername;
    private String nickName;
    private String username;
    private ImageView gender;
    private TextView tvFriendName;
    private Button addRelation;



    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_user_profile);
        initView();
        try {
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            initUserInfo(username);
            initListener(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
        tvUsername = (TextView) findViewById(R.id.user_username);
        gender = (ImageView) findViewById(R.id.user_gender);
        tvFriendName = (TextView) findViewById(R.id.friends_name);
        addRelation = (Button) findViewById(R.id.relation);
    }

    private void initListener(String name) throws IOException {

        headAvatar.setOnClickListener(this);
        addRelation.setOnClickListener(this);
        nickName = name;
        if(username != null){
            if (username.equals(EMClient.getInstance().getCurrentUser())) {
                tvUsername.setText(EMClient.getInstance().getCurrentUser());
                EaseUserUtils.setUserNick(nickName, tvUsername);
                EaseUserUtils.setUserAvatar(this, username, headAvatar);
            } else {
                tvUsername.setText(username);
                EaseUserUtils.setUserNick(nickName, tvUsername);
                EaseUserUtils.setUserAvatar(this, username, headAvatar);
                asyncFetchUserInfo(username);
            }
        }

    }
    public void initUserInfo(String username){
            JSONObject json = new JSONObject();
            int type = 0;
            try {
                json.put("phone",username);
                json.put("type",type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String data = json.toString();
            new Thread(){
                @Override
                public void run() {
                    String ip = (new MyApp()).getIp();
                    try {
                        URL url = new URL("http://"+ip+":8080/vhome/searchUserInfo");
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
                                    String name = userInfo.getNikeName();
                                    try {
                                        initListener(name);
                                    } catch (IOException e) {
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_head_avatar:
                Intent i = new Intent();
                break;
            case R.id.set_name:
                //跳转添加备注页
                break;
            case R.id.relation:
                sendRequest();
                Log.e("发送成功","ok");
                break;
            case R.id.conversation:
                //跳转聊天页
                break;
            default:
                break;
        }

    }
    public void sendRequest() {
        //准备数据
        SharedPreferences sp1 = getSharedPreferences("parentUserInfo",Context.MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("childUserInfo",Context.MODE_PRIVATE);
        String sendPhone = sp1.getString("phone","indefindParent");
        String sendName = "";
        if(sendPhone.equals("indefindParent")){
            sendPhone = sp2.getString("phone","indefindChild");
            sendName = sp2.getString("nickName","");
        }else{
            sendPhone = sp1.getString("phone","indefindParent");
            sendName = sp1.getString("nickName","");
        }
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        SendPerson sendPerson = new SendPerson();
        sendPerson.setSendPhone(sendPhone);
        sendPerson.setSendName(sendName);
        sendPerson.setReceivePhone(username);
        Log.e("我的电话2","2"+username);
        Gson gson = new Gson();
        final String data = gson.toJson(sendPerson);

        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/SendRequsetOfRelation");
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
                                    String ok = json.getString("ojbk");
                                    Log.e("okk",ok);
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
    public void asyncFetchUserInfo(String username){
        DemoHelper.getInstance().getUserProfileManager().asyncGetUserInfo(username, new EMValueCallBack<EaseUser>() {
            @Override
            public void onSuccess(EaseUser user) {
                if (user != null) {
                    DemoHelper.getInstance().saveContact(user);
                    if(isFinishing()){
                        return;
                    }
                    tvUsername.setText(user.getNickname());
                    if(!TextUtils.isEmpty(user.getAvatar())){
                        Glide.with(UserProfileActivity.this).load(user.getAvatar())
                                .placeholder(R.mipmap.default_image)
                                .into(headAvatar);
                    }else{
                        Glide.with(UserProfileActivity.this).load(R.mipmap.default_image).into(headAvatar);
                    }
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

}
