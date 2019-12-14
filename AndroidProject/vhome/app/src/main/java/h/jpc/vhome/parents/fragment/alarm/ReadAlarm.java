package h.jpc.vhome.parents.fragment.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

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
import h.jpc.vhome.parents.fragment.HomeFragment;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.timeAdapter;


public class ReadAlarm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_alarm);
        SharedPreferences sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
        list.clear();
        for(int i=0;i< HomeFragment.size;i++){
            Clock clock = new Clock();
            clock.setHour(sharedPreferences.getString("hour"+i,""));
            clock.setMinute(sharedPreferences.getString("minute"+i,""));
            clock.setContent(sharedPreferences.getString("content"+i,""));
            clock.setSendPersonId(sharedPreferences.getString("sendperson"+i,""));
            clock.setClockType(sharedPreferences.getInt("clocktype"+i,0));
            list.add(clock);
        }
        timeAdapter.notifyDataSetChanged();
        finish();
    }
}
