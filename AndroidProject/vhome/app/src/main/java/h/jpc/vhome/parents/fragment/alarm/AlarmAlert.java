package h.jpc.vhome.parents.fragment.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.HomeFragment;
import h.jpc.vhome.util.ConnectionUtil;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.Clock.clock_close;
import static h.jpc.vhome.parents.fragment.alarm.ClockDetail.position;
public class AlarmAlert extends Activity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this, R.raw.clockmusic2);
        mediaPlayer.start();
        SharedPreferences sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
        Intent ii = getIntent();
        int position1 = ii.getIntExtra("position",-1);
        String sendPerson = sharedPreferences.getString("sendperson"+position1,"");
        String content = sharedPreferences.getString("content"+position1,"");
        if(position1 != -1) {
            new AlertDialog.Builder(AlarmAlert.this)
                    .setIcon(R.mipmap.clock)
                    .setTitle("小提醒来自：" + sendPerson)
                    .setCancelable(false)
                    .setMessage(content)
                    .setPositiveButton("知道了！"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlarmAlert.this.finish();
                                    try {
                                        changeAlarm(content,clock_close);
                                    } catch (IndexOutOfBoundsException e) {
                                        Log.e("被老子捕获了吧", "你个渣渣" + HomeFragment.size);
                                    } finally {
                                        mediaPlayer.stop();
                                    }
                                }
                            }).show();
        }
    }
    public void changeAlarm(String content,int clocktype){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("alarmId",0);
            jsonObject.put("hour","");
            jsonObject.put("minute","");
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
                                Log.e("闹钟状态修改完毕", "!");
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
