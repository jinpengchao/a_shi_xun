package h.jpc.vhome.parents.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.BaseFragment;
import h.jpc.vhome.children.fragment.historyAdapter.AlarmBean;
import h.jpc.vhome.parents.HttpLogin;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.parents.fragment.alarm.AlarmActivity;
import h.jpc.vhome.parents.fragment.calendar.LunarCalendar;
import h.jpc.vhome.parents.fragment.news.NewsActivity;
import h.jpc.vhome.parents.fragment.weather.WeatherActivity;
import h.jpc.vhome.user.entity.ParentUserInfo;
import h.jpc.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends BaseFragment {
    private TextView yang_li_calendar;
    private TextView yin_li_calendar;
    private TextView today_weather;
    private ImageView alarm;
    private ImageView weather;
    private ImageView news;
    private ImageView top_bg;
    public static int size;
    private SharedPreferences sp2;
    private SharedPreferences.Editor editor2;
    private Handler handler,handler2=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 3:
                    Bundle bundle1=new Bundle();
                    bundle1=msg.getData();
                    String result1=bundle1.getString("result1");
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("today_weather",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    try{
                        JSONObject jsonObject=new JSONObject(result1);
                        JSONObject jsonObject2=jsonObject.getJSONObject("result");
                        JSONArray jsonArray=jsonObject2.getJSONArray("future");
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        editor.putString("temperature",jsonObject1.getString("week"));
                        editor.putString("weather",jsonObject1.getString("weather"));
                        editor.putString("week",jsonObject1.getString("week"));
                        editor.putString("date",jsonObject1.getString("date"));
                        String weather = jsonObject1.getString("weather");
                        String week = jsonObject1.getString("week");
                        String temp = jsonObject1.getString("temperature");
//                        if(weather.equals("晴")){
//                            Glide.with(getActivity()).load(R.drawable.sun).asGif().into(gif_weather);
//                        }else if(weather.equals("雨")){
//                            Glide.with(getActivity()).load(R.drawable.yu).into(gif_weather);
//                        }else {
//                            Glide.with(getActivity()).load(R.drawable.yin).into(gif_weather);
//                        }
                        today_weather.setText(week + "  " + weather + "  " + temp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_parent_home,null);
        initUserInfo();
        getAlarm();
        top_bg = view.findViewById(R.id.top_bg);
        Glide.with(getActivity()).load(R.drawable.top_bg).into(top_bg);
//        AssetManager mgr = getActivity().getAssets();
//        Typeface tf = Typeface.createFromAsset(mgr, "fonts/kaiti.ttf");
//        yang_li_calendar.setTypeface(tf);
//        yin_li_calendar.setTypeface(tf);
        /*
         * 功能：设置时间
         * 作者：靳朋朝
         * 完成时间：2019年11月25日19:14:34
         */
        yang_li_calendar = view.findViewById(R.id.yang_li_calendar);
        yin_li_calendar = view.findViewById(R.id.yin_li_calendar);
        today_weather = view.findViewById(R.id.today_weather);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("today_weather",MODE_PRIVATE);
        String w  = sharedPreferences.getString("weather","");
        Date date = new Date();
//        load("石家庄");

        yin_li_calendar.setText(getDate(date)[1]);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                yang_li_calendar.setText((String) msg.obj);
            }
        };
        Threads thread = new Threads();
        thread.start();
        /*
         *  功能：闹钟跳转
         *  作者：靳朋朝
         *  时间：2019年11月26日18:41:11
         */
        alarm = view.findViewById(R.id.chizixin);
        Glide.with(getActivity()).load(R.mipmap.nz1).into(alarm);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        //天气
        /*
         *  功能：获取实时天气信息
         *  作者：章鹏
         *  时间：2019年12月10号
         */
        weather = view.findViewById(R.id.kantianqi);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), WeatherActivity.class);
                startActivity(intent);
            }
        });
        Glide.with(getActivity()).load(R.mipmap.tq1).into(weather);
        //新闻
        news = view.findViewById(R.id.quxinwen);
        /*
         *  功能：获取实时新闻信息
         *  作者：章鹏
         *  时间：2019年12月10号
         */

        Glide.with(getActivity()).load(R.mipmap.xw1).into(news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    /*
     * 功能：顶部日期
     * 作者：靳朋朝
     * 完成时间：2019年11月25日19:11:27
     */
    public String[] getDate(Date date) {
        String[] d = new String[2];
        LunarCalendar lunarCalendar = new LunarCalendar();//实例化时间类
        String animal = lunarCalendar.animalsYear();//获取阴历日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");//修改日期格式
        String day = sdf.format( date );
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        d[1] =  "【"+animal+"】农历"+lunarCalendar.toString();
        return d;
    }
    private void load(final String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=httpLogin.JasonAccpt2(city);
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler2.sendMessage(message);
            }
        }).start();
    }
    class Threads extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy年MM月dd日   HH:mm:ss");
                    String str = sdf.format(new Date());
                    handler.sendMessage(handler.obtainMessage(100, str));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void initUserInfo(){
        //准备数据
        SharedPreferences sp = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        if(sp.getString("test",null)==null || !sp.getString("test","").equals("ok")){
            final SharedPreferences.Editor editor = sp.edit();
            final String phoneNums = sp.getString("phone","");
            final int type = sp.getInt("type",0);
            JSONObject json = new JSONObject();
            try {
                json.put("phone",phoneNums);
                json.put("type",type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("最新个人信息","new");
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    ParentUserInfo userInfo = gson.fromJson(data,ParentUserInfo.class);
                                    String phone = userInfo.getPhone();
                                    String id = userInfo.getId();
                                    String nickName = userInfo.getNikeName();
                                    String sex = userInfo.getSex();
                                    String area = userInfo.getArea();
                                    String achieve = userInfo.getAcieve();
                                    String personalWord = userInfo.getPersonalWord();
                                    String headImg = userInfo.getHeaderImg();
                                    saveUserInfo(phone,id,nickName,sex,area,achieve,personalWord,headImg);
                                    editor.putString("test","ok");
                                    editor.commit();
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
    public void saveUserInfo(String phone,String id,String nickName,String sex,String area,String achieve,String personalWord,String headimg){
        sp2 = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        editor2 = sp2.edit();
        editor2.putString("phone",phone);
        editor2.putString("id",id);
        editor2.putString("nickName",nickName);
        editor2.putString("sex",sex);
        editor2.putString("area",area);
        editor2.putString("achieve",achieve);
        editor2.putString("personalWord",personalWord);
        editor2.putString("headImg",headimg);
        editor2.commit();
    }
    public void getAlarm(){
        SharedPreferences sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("alarm",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String json = data;
                                //得到集合对应的具体类型
                                Type type = new TypeToken<List<AlarmBean>>(){}.getType();
                                List<AlarmBean> alarm = gson.fromJson(json,type);
                                size = alarm.size();
                                for (int i=0;i<size;i++){
                                    int alarmId = alarm.get(i).getAlarmId();
                                    String time = alarm.get(i).getAlarmTime();
                                    String content = alarm.get(i).getContent();
                                    String sendperson = alarm.get(i).getSendPersonId();
                                    String[] timer = time.split(":");
                                    int clocktype = alarm.get(i).getClocktype();
                                    editor.putInt("alarmId"+i,alarmId);
                                    editor.putString("hour"+i,timer[0]);
                                    editor.putString("minute"+i,timer[1]);
                                    editor.putString("content"+i,content);
                                    editor.putString("sendperson"+i,sendperson);
                                    editor.putInt("clocktype"+i, clocktype);
                                    editor.commit();
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
}
