package com.vhome.vhome.children.fragment.historyAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;

public class HistoryWarnAdapter extends BaseAdapter {
    private List<AlarmBean> alarmBeansList;
    private Context context;
    private int itemLayout;

    public HistoryWarnAdapter(Context context, List<AlarmBean> alarmBeans, int itemLayout) {
        this.alarmBeansList = alarmBeans;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return alarmBeansList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmBeansList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout, null);
            holder.history_content = convertView.findViewById(R.id.history_content);
            holder.history_time = convertView.findViewById(R.id.history_time);
            holder.history_receiver = convertView.findViewById(R.id.history_receiver);
            holder.history_sender = convertView.findViewById(R.id.history_sender);
            holder.todetail = convertView.findViewById(R.id.todetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AlarmBean alarmBean = alarmBeansList.get(position);
        holder.history_content.setText(alarmBean.getContent());
        holder.history_time.setText(alarmBean.getAlarmTime());
        holder.history_receiver.setText(alarmBean.getReceivePersonId());
        holder.history_sender.setText(alarmBean.getSendPersonId());
        String content = alarmBean.getContent();
        holder.todetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("是否取消发送？")
                        .setItems(R.array.choose,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        String[] PK = context.getResources()
                                                .getStringArray(
                                                        R.array.choose);
                                        if (PK[which].equals("取消发送")) {
                                            if (null!=alarmBeansList) {
                                                alarmBeansList.remove(position);
                                                Log.e("content",content+"");
                                                deleteSendedAlarm(content);
                                                notifyDataSetChanged();
                                            }
                                            Toast.makeText(context,"取消成功啦~", Toast.LENGTH_LONG).show();
                                        }
                                        if (PK[which].equals("关闭")) {
                                        }
                                    }
                                }).show();
                return true;
            }
        });
        return convertView;
    }

    final static class ViewHolder{
        private RelativeLayout todetail;
        private TextView history_content;
        private TextView history_time;
        private TextView history_receiver;
        private TextView history_sender;
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
}
