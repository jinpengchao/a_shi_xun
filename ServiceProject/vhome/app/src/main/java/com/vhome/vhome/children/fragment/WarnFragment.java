package com.vhome.vhome.children.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.ChildrenMain;
import com.vhome.vhome.children.fragment.dialog.MyDialog;
import com.vhome.vhome.children.fragment.historyAdapter.AlarmBean;
import com.vhome.vhome.children.fragment.historyAdapter.HistoryWarnAdapter;
import com.vhome.vhome.children.fragment.imageloader.GlideImageLoader;
import com.vhome.vhome.children.fragment.slideadapter.ListViewCompat;
import com.vhome.vhome.children.fragment.slideadapter.SlideView;
import com.vhome.vhome.parents.fragment.HomeFragment;
import com.vhome.vhome.user.entity.ParentUserInfo;
import com.vhome.vhome.user.entity.User;
import com.vhome.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;


public class WarnFragment extends Fragment implements View.OnClickListener, SlideView.OnSlideListener {
    private static final String TAG = "MainActivity";
    private ListViewCompat mListView;
    private ListView lvHistory;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private SlideView mLastSlideViewWithStatusOn;
    private SlideAdapter adapter;
    private MyDialog myDialog;
    private Button addNewNormalWarn;
    private Button sendNewWarn;
    private Button quaryAllWarn;
    private HistoryWarnAdapter historyWarnAdapter;
    private Banner banner;
    private List<Integer> images;
    private List<String> titles;
    private  List<String> peopleList;
    private  List<String> hourList;
    private  List<String> minuteList;
    public static List<AlarmBean> alarmBeanList = new ArrayList<>();
    private SmartRefreshLayout srl;
    private SharedPreferences sp;
    private TextView info;
    private Set<String> set;
    private SharedPreferences sp2;
    private SharedPreferences.Editor editor2;
    public static int normalSize;
    private Handler myhandler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mMessageItems.clear();
            setMyNormalAlarm();
            adapter = new SlideAdapter();
            mListView.setAdapter(adapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children_warn, null);


        addNewNormalWarn = view.findViewById(R.id.addNormalWarn);
        sendNewWarn = view.findViewById(R.id.sendNewWarn);
        quaryAllWarn = view.findViewById(R.id.quaryAllWarn);
        banner = view.findViewById(R.id.banner);
        info = view.findViewById(R.id.info);
        mListView = view.findViewById(R.id.list);
        srl = view.findViewById(R.id.srl);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
            }
        });
        findMyRelation();
        initUserInfo();
        getMyNormalAlarm();
        longClickItem();
        setBinder();
        //点击事件
        addNewNormalWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_add_new_normal_warn, null);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                Button cancle = (Button)view.findViewById(R.id.new_normal_warn_cancle);
                Button ok = (Button)view.findViewById(R.id.new_normal_warn_ok);
                final EditText editText = (EditText)view.findViewById(R.id.new_normal_warn_text);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //存入数据库放入常用列表
                        addNewNormal((editText.getText()).toString());
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        sendNewWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_send_new_warn, null);

                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                Button cancle = (Button)view.findViewById(R.id.new_normal_warn_cancle);
                Button ok = (Button)view.findViewById(R.id.new_normal_warn_ok);
                final Spinner sPeople = (Spinner)view.findViewById(R.id.spinner_people);
                final Spinner sHour = (Spinner)view.findViewById(R.id.spinner_hour);
                final Spinner sMinute = (Spinner)view.findViewById(R.id.spinner_minute);
                final String[] receiver = {""};
                final String[] hour = {""};
                final String[] minute = {""};
                hourData();
                minuteData();
                sPeople.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        receiver[0] = sPeople.getSelectedItem()+"";
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sHour.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        hour[0] = sHour.getSelectedItem()+"";
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sMinute.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        minute[0] = sMinute.getSelectedItem()+"";
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                ArrayAdapter adapterPeople = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,peopleList);
                sPeople.setAdapter(adapterPeople);
                ArrayAdapter adapterHour = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,hourList);
                sHour.setAdapter(adapterHour);
                ArrayAdapter adapterMinute = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,minuteList);
                sMinute.setAdapter(adapterMinute);
                final EditText editText = (EditText)view.findViewById(R.id.send_new_warn_text);
                sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
                final String sendPersonId = sp.getString("phone","");
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = editText.getText().toString();
                        sendNewAlarm(receiver,hour,minute,sendPersonId,content);
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        //能查的数据为 寄几发的闹钟
        quaryAllWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_quary_all_warn, null);
                SmartRefreshLayout srl2 = view.findViewById(R.id.srl2);
                TextView warnInfo = view.findViewById(R.id.warnInfo);
                srl2.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        srl2.finishRefresh();
                        historyWarnAdapter.notifyDataSetChanged();
                    }
                });
                if (alarmBeanList.size()==0){
                    warnInfo.setText("你暂时还未发送过提醒哦~");
                }
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                lvHistory = (ListView)view.findViewById(R.id.history_warn);
                historyWarnAdapter = new HistoryWarnAdapter(getActivity(),alarmBeanList,R.layout.item_history_alarm_warn);
                lvHistory.setAdapter(historyWarnAdapter);
                historyWarnAdapter.notifyDataSetChanged();
                myDialog.setCancelable(true);
                myDialog.show();
            }

        });

        return view;
    }
    public void refreshData(){
        mMessageItems.clear();
        getMyNormalAlarm();
        setMyNormalAlarm();
    }
    public void sendNewAlarm(String[] receiver,String[] hour,String[] minute,String sendPersonId,String content){
        //准备数据
        AlarmBean alarmBean = new AlarmBean();
        String time = hour[0]+":"+minute[0];
        alarmBean.setReceivePersonId(receiver[0]);
        alarmBean.setSendPersonId(sendPersonId);
        alarmBean.setAlarmTime(time);
        alarmBean.setContent(content);
        Gson gson = new Gson();
        final String data = gson.toJson(alarmBean);
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/sendnew");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlarmBean alarmBean = new AlarmBean(0,time,sendPersonId,receiver[0],content,1);
                                alarmBeanList.add(alarmBean);
                                Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"服务器走丢了~",Toast.LENGTH_SHORT).show();
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

    public void addNewNormal(String content){
        SharedPreferences sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
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
                    URL url = new URL("http://"+ip+":8080/vhome/addnormal");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("normalalarm",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Set<String> set = new HashSet<>();
                                for (int i=0;i<normalSize;i++){
                                    String co = sharedPreferences.getString("content"+i,"");
                                    set.add(co);
                                }
                                Log.e("set",set.size()+"");
                                if(!set.contains(content)){
                                    editor.putString("content" + normalSize, content);
                                    normalSize++;
                                    editor.commit();
                                    Toast.makeText(getActivity(),("添加成功！长按可发送哦~"),Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(getActivity(),"已经添加过相同的提醒了~",Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putString("我彻底彻底彻底","彻底服了Handler了！草！");
                                Message msg = new Message();
                                msg.setData(bundle);
                                msg.what=0;
                                myhandler.sendMessage(msg);
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
    public void setMyNormalAlarm(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("normalalarm",MODE_PRIVATE);
        if(normalSize==0){
            info.setText("你还没有添加常用闹钟哦~");
        }else {
            info.setText("");
            for (int i = 0; i < normalSize; i++) {
                String content = sharedPreferences.getString("content" + i, "");
                MessageItem item = new MessageItem();
                item.msg = content;
                mMessageItems.add(item);
            }
        }
    }
    public void getMyNormalAlarm(){
        SharedPreferences sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        final String data = phone;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/ReadNormalAlarmService");
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
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("normalalarm",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String json = data;
                                //得到集合对应的具体类型
                                Type type = new TypeToken<List<String>>(){}.getType();
                                List<String> myAlarm = gson.fromJson(json,type);
                                normalSize = myAlarm.size();
                                set = new HashSet<>();
                                if(normalSize==0){
                                    info.setText("你还没有添加常用提示哦~");
                                }else {
                                    for (int i = 0; i < myAlarm.size(); i++) {
                                        if(!set.contains( myAlarm.get(i))) {
                                            set.add(myAlarm.get(i));
                                        }
                                    }
                                    Iterator<String> it = set.iterator();
                                    int i = 0;
                                    editor.clear();
                                    editor.commit();
                                    while(it.hasNext()){
                                        editor.putString("content" + i, it.next());
                                        editor.commit();
                                        i++;
                                    }
                                    normalSize = set.size();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("我彻底彻底彻底","彻底服了Handler了！草！");
                                    Message msg = new Message();
                                    msg.setData(bundle);
                                    msg.what=0;
                                    myhandler.sendMessage(msg);
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
    public void hourData(){
        //设置小时为5:00-22:00
        hourList = new ArrayList<>();
        for (int i = 5;i<23;i++){
            if(i<10){
                hourList.add("0"+i);
            }else
                hourList.add(i+"");
        }
    }
    public void minuteData(){
        minuteList = new ArrayList<>();
        for (int i = 0;i<60;i++){
            if(i<10){
                minuteList.add("0"+i);
            }else
                minuteList.add(i+"");
        }
    }
    public void setBinder(){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images = new ArrayList<>();
        images.add(R.mipmap.p1);
        images.add(R.mipmap.p2);
        images.add(R.mipmap.p3);
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        titles = new ArrayList<>();
        titles.add("百善孝为先");
        titles.add("常回家看看");
        titles.add("回家");
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }
    private class SlideAdapter extends BaseAdapter {
        public LayoutInflater mInflater;
        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_listview_delete, null);
                slideView = new SlideView(getActivity());
                slideView.setContentView(itemView);
                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(WarnFragment.this);
                slideView.setTag(holder);

            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();
            holder.msg.setText(item.msg);
            String content = mMessageItems.get(position).msg;
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMessageItems.remove(position);
                    Log.e("content",content);
                    deleteNormal(content);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "删除成功",Toast.LENGTH_SHORT).show();
                }
            });

            return slideView;
        }
    }
    public void deleteNormal(String content){
        final String data = content;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/DelNormalAlarmServlet");
                    ConnectionUtil util = new ConnectionUtil();
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
    public class MessageItem {
        public String msg;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public TextView msg;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {;
            msg = (TextView) view.findViewById(R.id.msg);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }
    public void longClickItem(){
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view = getLayoutInflater().inflate(R.layout.dialog_send_new_warn, null);
                MessageItem item = mMessageItems.get(position);
                Log.e("ss",item.msg);
                EditText sendNewWarnMsg = view.findViewById(R.id.send_new_warn_text);
                sendNewWarnMsg.setText(item.msg);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);

                Button cancle = (Button)view.findViewById(R.id.new_normal_warn_cancle);
                Button ok = (Button)view.findViewById(R.id.new_normal_warn_ok);
                final Spinner sPeople = (Spinner)view.findViewById(R.id.spinner_people);
                final Spinner sHour = (Spinner)view.findViewById(R.id.spinner_hour);
                final Spinner sMinute = (Spinner)view.findViewById(R.id.spinner_minute);
                final String[] receiver = {""};
                final String[] hour = {""};
                final String[] minute = {""};
                hourData();
                minuteData();
                sPeople.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        receiver[0] = sPeople.getSelectedItem()+"";
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sHour.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        hour[0] = sHour.getSelectedItem()+"";
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sMinute.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        minute[0] = sMinute.getSelectedItem()+"";
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                ArrayAdapter adapterPeople = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,peopleList);
                sPeople.setAdapter(adapterPeople);
                ArrayAdapter adapterHour = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,hourList);
                sHour.setAdapter(adapterHour);
                ArrayAdapter adapterMinute = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,minuteList);
                sMinute.setAdapter(adapterMinute);
                final EditText editText = (EditText)view.findViewById(R.id.send_new_warn_text);
                sp = getActivity().getSharedPreferences("user",MODE_PRIVATE);
                final String sendPersonId = sp.getString("phone","");
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = editText.getText().toString();
                        sendNewAlarm(receiver,hour,minute,sendPersonId,content);
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
                return true;
            }
        });
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.holder:
                break;
            default:
                break;
        }
    }
    public void findMyRelation() {
        //获得接收人电话
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String myPhone = sharedPreferences.getString("phone","");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sendPhone",myPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = jsonObject.toString();
        new Thread() {
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://" + ip + ":8080/vhome/FindMyRelation");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url, data);
                    //获取数据
                    final String data = util.getData(connection);
                    if (null != data) {
                        Gson gson = new Gson();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("relation",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String json = data;
                        //得到集合对应的具体类型
                        Type type = new TypeToken<List<String>>(){}.getType();
                        peopleList = new ArrayList<>();
                        peopleList = gson.fromJson(json,type);
                        Log.e("sbcnmdjwbsbyg",peopleList.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
                                    String headImg = userInfo.getHeaderImg();
                                    saveUserInfo(phone,id,nickName,sex,area,headImg);
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
    public void saveUserInfo(String phone,String id,String nickName,String sex,String area,String headimg){
        sp2 = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        editor2 = sp2.edit();
        editor2.putString("phone",phone);
        editor2.putString("id",id);
        editor2.putString("nickName",nickName);
        editor2.putString("sex",sex);
        editor2.putString("area",area);
        editor2.putString("headImg",headimg);
        editor2.commit();
    }
}
