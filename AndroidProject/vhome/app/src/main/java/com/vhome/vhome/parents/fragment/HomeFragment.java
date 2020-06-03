package com.vhome.vhome.parents.fragment;

import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.fragment.historyAdapter.AlarmBean;
import com.vhome.vhome.parents.HttpLogin;
import com.vhome.vhome.parents.fragment.alarm.AlarmActivity;
import com.vhome.vhome.parents.fragment.calendar.LunarCalendar;
import com.vhome.vhome.parents.fragment.news.News1Activity;
import com.vhome.vhome.parents.fragment.news.NewsActivity;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import com.vhome.vhome.parents.fragment.weather.WeatherActivity;
import com.vhome.vhome.user.entity.ParentUserInfo;
import com.vhome.vhome.user.personal.widget.CircleImageView;
import com.vhome.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.superrtc.ContextUtils.getApplicationContext;


public class HomeFragment extends BaseFragment {
    private TextView yang_li_calendar;
    private TextView yin_li_calendar;
    private TextView today_weather;
    private ImageView bg1;
    private ImageView alarm;
    private ImageView weather;
    private ImageView news;
    private ImageView all_bg;

    protected EaseChatInputMenu inputMenu;
    public static int size;
    private SharedPreferences sp2;
    private SharedPreferences.Editor editor2;
//    private CircleImageView addHeader;
//    private LinearLayout mLinearLayout1;
//    private LinearLayout mLinearLayout2;
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
//                            Glide.with(getActivity()).load(R.drawable.sun).into(gif_weather);
//                        }else if(weather.equals("雨")){
//                            Glide.with(getActivity()).load(R.drawable.yu).into(gif_weather);
//                        }else {
//                            Glide.with(getActivity()).load(R.drawable.yin).into(gif_weather);
//                        }
//                        today_weather.setText(week + "  " + weather + "  " + temp);
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

        all_bg = view.findViewById(R.id.all_bg);
        Glide.with(getActivity()).asGif().load(R.drawable.all_bg).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(all_bg);
        bg1 = view.findViewById(R.id.bg1);
        bg1.setBackgroundResource(R.mipmap.bg);

//        addHeader= (CircleImageView) view.findViewById(R.id.button_add);
//        mLinearLayout1= (LinearLayout) view.findViewById(R.id.linearLayout1);
//        mLinearLayout2= (LinearLayout) view.findViewById(R.id.linearLayout2);
//        LayoutTransition transition =new LayoutTransition();
//        transition.getDuration(2000);//时间
//        //APPEARING添加view的动画
//        transition.setAnimator(LayoutTransition.APPEARING, AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.animator_scale));
//        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,transition.getAnimator(LayoutTransition.CHANGE_APPEARING));//CHANGE_APPEARING消失动画
//        transition.setAnimator(LayoutTransition.DISAPPEARING,transition.getAnimator(LayoutTransition.DISAPPEARING));//DISAPPEARING移除view的动画
//        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,transition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING));
//        mLinearLayout1.setLayoutTransition(transition);//把动画加到按钮上
//        mLinearLayout2.setLayoutTransition(transition);//把动画加到按钮上
//        addHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CircleImageView btn1=new CircleImageView(getActivity());
//                CircleImageView btn2=new CircleImageView(getActivity());
//                ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                btn1.setLayoutParams(params);
//                btn1.setImageResource(R.mipmap.voice_icon);
//                btn1.setScaleX(0f);
//                btn1.setScaleY(0f);
//                btn2.setLayoutParams(params);
//                btn2.setImageResource(R.mipmap.video_icon);
//                btn2.setScaleX(0f);
//                btn2.setScaleY(0f);
//                mLinearLayout1.addView(btn1);
//                mLinearLayout2.addView(btn2);
//
//                btn1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mLinearLayout2.removeView(v);
//                    }
//                });
//
//            }
//        });
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
        load("石家庄");

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
        Glide.with(getActivity()).load(R.mipmap.czx).into(alarm);

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
        Glide.with(getActivity()).load(R.mipmap.weather).into(weather);
        //新闻
        news = view.findViewById(R.id.quxinwen);
        /*
         *  功能：获取实时新闻信息
         *  作者：章鹏
         *  时间：2019年12月10号
         */

        Glide.with(getActivity()).load(R.mipmap.xw0).into(news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), News1Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        return null;
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
    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();
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
                                    String birthday = userInfo.getBirthday();
                                    String personalWord = userInfo.getPersonalWord();
                                    String headImg = userInfo.getHeaderImg();
                                    String status = userInfo.getStatus();
                                    saveUserInfo(phone,id,nickName,sex,area,achieve,birthday,personalWord,headImg,status);
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
    public void saveUserInfo(String phone,String id,String nickName,String sex,String area,String achieve,String birthday,String personalWord,String headimg,String status){
        sp2 = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        editor2 = sp2.edit();
        editor2.putString("phone",phone);
        editor2.putString("id",id);
        editor2.putString("nickName",nickName);
        editor2.putString("sex",sex);
        editor2.putString("area",area);
        editor2.putString("achieve",achieve);
        editor2.putString("birthday",birthday);
        editor2.putString("personalWord",personalWord);
        editor2.putString("headImg",headimg);
        editor2.putString("status","封禁");
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
