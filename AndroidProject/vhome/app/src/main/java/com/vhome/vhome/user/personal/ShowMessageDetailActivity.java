package com.vhome.vhome.user.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.user.entity.NewTicketBody;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ShowMessageDetailActivity extends Activity {
    private ImageView back;
    private TextView question;
    private TextView phone;
    private TextView name;
    private TextView theme;
    private TextView answer;
    private String answers;
    private Handler handler;
    private NewTicketBody body;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_message_detial);
        getView();
        Intent intent = getIntent();
        answers = intent.getStringExtra("content");
        getdata(answers);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        Bundle c = msg.getData();
                        String datac = c.getString("data3");
                        Gson gsonc = new Gson();
                        body = gsonc.fromJson(datac, new TypeToken<NewTicketBody>() {
                        }.getType());
                        theme.setText(body.getSubject());
                        question.setText(body.getContent());
                        phone.setText(body.getCreatorPhone());
                        name.setText(body.getCreatorName());
                        answer.setText(answers);
                        break;
                }
            }
        };

    }
    public void getView(){
        back = findViewById(R.id.back);
        question = findViewById(R.id.content);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        theme = findViewById(R.id.theme);
        answer = findViewById(R.id.answer);
    }
    public final void getdata(String content) {
        SharedPreferences sp = getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        String phone = sp.getString("phone","");
        JSONObject json = new JSONObject();
        try {
            json.put("content", content);
            json.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = json.toString();

        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/ShowQuestionsDetails");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url, data);
                    //获取数据
                    String data = util.getData(connection);
                    Bundle bundle = new Bundle();
                    bundle.putString("data3", data);
                    Message msg = new Message();
                    msg.setData(bundle);
                    msg.what = 3;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
