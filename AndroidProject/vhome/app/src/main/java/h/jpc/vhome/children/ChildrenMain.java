package h.jpc.vhome.children;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.ConversationListFragment;
import h.jpc.vhome.chat.view.ScrollControlViewPager;
//import h.jpc.vhome.children.fragment.ChatFragment;
import h.jpc.vhome.children.fragment.LocationFragment;
import h.jpc.vhome.children.fragment.MyselfFragment;
import h.jpc.vhome.children.fragment.WarnFragment;
import h.jpc.vhome.children.fragment.historyAdapter.AlarmBean;
import h.jpc.vhome.util.ConnectionUtil;


public class ChildrenMain extends AppCompatActivity {
    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();

    private static String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_main);
        setTabHost();
        getMySendedAlarm();

    }
    public void setTabHost(){
        //获取FragmentTabHost对象
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化参数
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent
        );
        //tab1
        TabHost.TabSpec tabSpec1 = fragmentTabHost
                .newTabSpec("tag1")
                .setIndicator(getTabSpaceView("tag1",R.mipmap.warn,"提醒"));
        fragmentTabHost.addTab(tabSpec1,
                WarnFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2",R.mipmap.msg,"聊天"));
        fragmentTabHost.addTab(tabSpec2,
                ConversationListFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec3 = fragmentTabHost
                .newTabSpec("tag3")
                .setIndicator(getTabSpaceView("tag3",R.mipmap.path,"位置"));
        fragmentTabHost.addTab(tabSpec3,
                LocationFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec4 = fragmentTabHost
                .newTabSpec("tag4")
                .setIndicator(getTabSpaceView("tag4",R.mipmap.me,"我的"));
        fragmentTabHost.addTab(tabSpec4,
                MyselfFragment.class,
                null
        );
        //去除分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.mipmap.warn1);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.warn1);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.msg);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.path);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.warn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.msg1);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.path);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.warn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.msg);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.path1);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag4":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.warn);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.msg);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.path);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me1);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
    }
    public View getTabSpaceView(String tag, int imageResId, String title){
        //加载布局文件
        LayoutInflater layoutInflater1 = getLayoutInflater();
        View view = layoutInflater1.inflate(R.layout.tab_space_children,null);
        //获取控件对象（image对象）
        ImageView imageView = view.findViewById(R.id.icon);//去tab_space去找id
        imageView.setImageResource(imageResId);
        //Text对象
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;
    }
    public void getMySendedAlarm(){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        Log.e("new要死了，","啊啊啊");
        final String data = phone;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/showMysended");
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
                                String json = data;
                                //得到集合对应的具体类型
                                Type type = new TypeToken<List<AlarmBean>>(){}.getType();
                                List<AlarmBean> alarm = gson.fromJson(json,type);
                                WarnFragment.alarmBeanList = new ArrayList<>();
                                for (int i=0;i<alarm.size();i++){
                                    SharedPreferences sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    int alarmId = alarm.get(i).getAlarmId();
                                    String time = alarm.get(i).getAlarmTime();
                                    String content = alarm.get(i).getContent();
                                    String receivePerson = alarm.get(i).getReceivePersonId();
                                    String sendPerson = alarm.get(i).getSendPersonId();
                                    AlarmBean alarmBean = new AlarmBean();
                                    alarmBean.setAlarmId(alarmId);
                                    alarmBean.setAlarmTime(time);
                                    alarmBean.setContent(content);
                                    alarmBean.setReceivePersonId(receivePerson);
                                    alarmBean.setSendPersonId(sendPerson);
                                    WarnFragment.alarmBeanList.add(alarmBean);
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
