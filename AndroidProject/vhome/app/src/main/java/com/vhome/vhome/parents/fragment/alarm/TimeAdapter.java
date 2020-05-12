package com.vhome.vhome.parents.fragment.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.easeui.widget.EaseSwitchButton;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.util.ConnectionUtil;

import static com.vhome.vhome.parents.fragment.alarm.Clock.clock_close;
import static com.vhome.vhome.parents.fragment.alarm.Clock.clock_open;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private ClockStatus clockStatus;
    public interface ClockStatus{
        public void clockType(String content,int now, int wangTo);
    }
    public void setClockStatus(ClockStatus clockStatus){
        this.clockStatus = clockStatus;
    }
    List<Clock> list;
    LayoutInflater layoutInflater;
    Context context;
    public TimeAdapter(List<Clock> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_parent_alarm, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final TimeAdapter.ViewHolder viewHolder, final int i) {
        final Clock clock = list.get(i);

        Log.e("i=======",i+"  "+clock.getClockType());
        if (clock.getClockType() == clock_open){
            viewHolder.aSwitch.openSwitch();
            viewHolder.hour.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.minute.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.net.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.from.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.content.setTextColor(context.getResources().getColor(R.color.notChoseColor));
        }else if (clock.getClockType() == clock_close){
            viewHolder.aSwitch.closeSwitch();;
            viewHolder.hour.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.minute.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.from.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.net.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.content.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        viewHolder.hour.setText(clock.getHour()+"");
        viewHolder.minute.setText(clock.getMinute()+"");
        viewHolder.sendPersonId.setText(clock.getSendPersonId()+"");
        viewHolder.minute.setText(clock.getMinute()+"");
        viewHolder.content.setText(clock.getContent());
        viewHolder.todetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("toDetail",clock.getClockType()+"");
                Intent intent = new Intent(context, ClockDetail.class);
                intent.putExtra("position", i);
                context.startActivity(intent);
            }
        });
        //点击收藏的时候,收藏成功改变图标颜色
        final ViewHolder finalHolder = viewHolder;
        viewHolder.aSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (clock.getClockType() == clock_close) {
                    viewHolder.aSwitch.openSwitch();
                    String content = list.get(i).getContent();
//                    clockStatus.clockType(content,clock_close,clock_open);
                    changeAlarm(content,clock_open);
                    clock.setClockType(clock_open);
                    Toast.makeText(context, "开启闹钟", Toast.LENGTH_SHORT).show();
                    viewHolder.hour.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.minute.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.net.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.from.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.content.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                } else if (clock.getClockType() == clock_open){
                    viewHolder.aSwitch.closeSwitch();
                    String content = list.get(i).getContent();
//                    clockStatus.clockType(content,clock_open,clock_close);
                    changeAlarm(content,clock_close);
                    clock.setClockType(clock_close);
                    Toast.makeText(context, "关闭闹钟", Toast.LENGTH_SHORT).show();
                    viewHolder.hour.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.minute.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.net.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.from.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.content.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hour;
        TextView minute;
        TextView content;
        TextView sendPersonId;
        TextView from;
        TextView net;
        EaseSwitchButton aSwitch;
        LinearLayout todetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            minute = itemView.findViewById(R.id.minute);
            net = itemView.findViewById(R.id.net);
            sendPersonId = itemView.findViewById(R.id.send_person);
            from = itemView.findViewById(R.id.from);
            content = itemView.findViewById(R.id.content_item);
            aSwitch = itemView.findViewById(R.id.switch_control);
            todetail = itemView.findViewById(R.id.todetail);
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

}
