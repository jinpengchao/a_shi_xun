package com.vhome.vhome.parents.fragment.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.fragment.historyAdapter.AlarmBean;
import com.vhome.vhome.util.ConnectionUtil;

public class AlarmActivity extends Activity {
    public static List<Clock> list = new ArrayList<>();
    public static TimeAdapter timeAdapter;
    RecyclerView recyclerView;
    RelativeLayout drawerLayout;
    Context context = AlarmActivity.this;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm);
        textView = findViewById(R.id.warnInfo1);
        recyclerView = findViewById(R.id.clock_list);
        drawerLayout = findViewById(R.id.layout1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        timeAdapter = new TimeAdapter(list, context);
        recyclerView.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initRecyclerView();
    }
    private void initRecyclerView() {
        textView.setText("");
        if (list.size()==0){
            textView.setText("您还没有收到小提示哦~");
        }
    }
    @Override
    public void onBackPressed() {
            AlarmActivity.this.finish();
    }
    public void getAlarm(){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        final String data = phone;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/showAllAlarm");
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
                                SharedPreferences sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String json = data;
                                //得到集合对应的具体类型
                                Type type = new TypeToken<List<AlarmBean>>(){}.getType();
                                List<AlarmBean> alarm = gson.fromJson(json,type);
                                for (int i=0;i<alarm.size();i++){
                                    int alarmId = alarm.get(i).getAlarmId();
                                    String time = alarm.get(i).getAlarmTime();
                                    String content = alarm.get(i).getContent();
                                    String sendperson = alarm.get(i).getSendPersonId();
                                    String[] timer = time.split(":");
                                    int clocktype = alarm.get(i).getClocktype();
                                    Clock clock = new Clock();
                                    clock.setHour(timer[0]);
                                    clock.setMinute(timer[1]);
                                    clock.setContent(content);
                                    clock.setSendPersonId(sendperson);
                                    clock.setClockType(clocktype);
                                    list.add(clock);
                                }
                                timeAdapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerView();
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
    protected void onStart() {
        super.onStart();
        list.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        getAlarm();
    }
}
