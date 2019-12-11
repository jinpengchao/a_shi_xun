package h.jpc.vhome.parents.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.parents.fragment.alarm.AlarmActivity;
import h.jpc.vhome.parents.fragment.calendar.LunarCalendar;
import h.jpc.vhome.parents.fragment.tools.AllToolsActivity;
import h.jpc.vhome.user.entity.ParentUserInfo;
import h.jpc.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    private TextView yang_li_calendar;
    private TextView yin_li_calendar;
    private ImageView alarm;
    private ImageView weather;
    private ImageView news;
    private ImageView tools;
    private SharedPreferences sp2;
    private SharedPreferences.Editor editor2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_parent_home,null);
        initUserInfo();

        /*
         * 功能：设置时间
         * 作者：靳朋朝
         * 完成时间：2019年11月25日19:14:34
         */
        yang_li_calendar = view.findViewById(R.id.yang_li_calendar);
        yin_li_calendar = view.findViewById(R.id.yin_li_calendar);
        Date date = new Date();
        yang_li_calendar.setText(getDate(date)[0]);
        yin_li_calendar.setText(getDate(date)[1]);
        /*
         *  功能：闹钟跳转
         *  作者：靳朋朝
         *  时间：2019年11月26日18:41:11
         */
        alarm = view.findViewById(R.id.chizixin);
        Glide.with(getActivity()).load(R.mipmap.nz).into(alarm);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });
        //天气
        weather = view.findViewById(R.id.kantianqi);
        Glide.with(getActivity()).load(R.mipmap.tq).into(weather);
        //新闻
        news = view.findViewById(R.id.quxinwen);
        Glide.with(getActivity()).load(R.mipmap.xw).into(news);
        /*
         *  功能：小工具跳转
         *  作者：靳朋朝
         *  时间：2019年11月26日09:05:26
         */
        tools = view.findViewById(R.id.xiaogongju);
        Glide.with(getActivity()).load(R.mipmap.gj).into(tools);

        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),AllToolsActivity.class);
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
        d[0] =  day+","+weekDays[w];
        d[1] =  "【"+animal+"】农历"+lunarCalendar.toString();
        return d;
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
}
