package h.jpc.vhome.parents.fragment.alarm;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.children.fragment.historyAdapter.AlarmBean;
import h.jpc.vhome.util.ConnectionUtil;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.timeAdapter;

public class ClockDetail extends AppCompatActivity implements View.OnClickListener {
    private Calendar calendar;
    private TextView show_hour;
    private TextView show_minute;
    private EditText content;
    private Button set;
    private Button save;
    private Button delete;
    Clock clock;
    static int position;
    String hourformat;
    String minuteformat;
    Context context = ClockDetail.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_detial);
        show_hour = findViewById(R.id.hour);
        show_minute = findViewById(R.id.minute);
        content = findViewById(R.id.content);
        set = findViewById(R.id.set_time);
        set.setOnClickListener(this);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);
        calendar = Calendar.getInstance();
        initView();
    }
    @SuppressLint("SetTextI18n")
    private void initView() {
        position = getIntent().getIntExtra("position", -1);
        clock = list.get(position);
        Log.e("position---ClockDetial", position + "");
        if (clock.getHour() != null && clock.getMinute() != null) {
            hourformat = formatString(clock.getHour());
            minuteformat = formatString(clock.getMinute());
        }
        content.setText(clock.getContent());
        show_hour.setText(clock.getHour() + "");
        show_minute.setText(clock.getMinute() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_time:
                calendar.setTimeInMillis(System.currentTimeMillis());
                int mhour = calendar.get(Calendar.HOUR_OF_DAY);
                int mminute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(ClockDetail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        hourformat = format(hourOfDay);
                        minuteformat = format(minute);
                        Toast.makeText(ClockDetail.this, "" + hourformat + ":" + minuteformat, Toast.LENGTH_SHORT).show();
                        show_hour.setText(hourformat);
                        show_minute.setText(minuteformat);
                    }
                }, mhour, mminute, true).show();
                break;
            case R.id.save:
                clock.setHour(hourformat);
                clock.setMinute(minuteformat);
                clock.setContent(content.getText().toString());
                clock.setClockType(Clock.clock_open);//默认保存开启状态

                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getHour()));
                calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getMinute()));
                SharedPreferences sp = getSharedPreferences("alarm",MODE_PRIVATE);
                int clocktype = sp.getInt("clocktype"+position,0);
                changeAlarm(context,hourformat,minuteformat,content.getText().toString(),clocktype);
                timeAdapter.notifyDataSetChanged();
                finish();
                break;
            case R.id.delete:
                String content = clock.getContent();
                deleteSendedAlarm(content);
                finish();
                break;

        }
    }
    public void deleteSendedAlarm(String content){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/delAlarm");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    //发送数据
                    util.sendData(url,data);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void changeAlarm(Context context,String hour,String minute,String content,int clocktype){
        SharedPreferences sp = context.getSharedPreferences("alarm",MODE_PRIVATE);
        int alarmId = sp.getInt("alarmId"+position,0);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("alarmId",alarmId);
            jsonObject.put("hour",hour);
            jsonObject.put("minute",minute);
            jsonObject.put("content",content);
            jsonObject.put("clocktype",clocktype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/changeAlarm");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor editor = sp.edit();
                                if(null!=hour && !"".equals(hour)) {
                                    editor.putString("hour" + position, hour);
                                    editor.putString("minute" + position, minute);
                                    editor.putString("content" + position, content);
                                    editor.commit();
                                }
                                Log.e("闹钟修改完毕", "!");
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

    private String format(int x) {
        String s = "" + x;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

    private String formatString(String x) {
        String s = x;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }


}
