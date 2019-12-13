package h.jpc.vhome.children.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.children.fragment.dialog.MyDialog;
import h.jpc.vhome.children.fragment.historyAdapter.AlarmBean;
import h.jpc.vhome.children.fragment.historyAdapter.HistoryWarnAdapter;
import h.jpc.vhome.children.fragment.imageloader.GlideImageLoader;
import h.jpc.vhome.children.fragment.slideadapter.ListViewCompat;
import h.jpc.vhome.children.fragment.slideadapter.SlideView;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;


public class WarnFragment extends Fragment implements AdapterView.OnItemLongClickListener, View.OnClickListener, SlideView.OnSlideListener {
    private static final String TAG = "MainActivity";
    private ListViewCompat mListView;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private SlideView mLastSlideViewWithStatusOn;
    private SlideAdapter adapter;
    private MyDialog myDialog;
    private Button addNewNormalWarn;
    private Button sendNewWarn;
    private Button quaryAllWarn;
    private EditText newNormalWarnMsg;
    private Banner banner;
    private List<Integer> images;
    private List<String> titles;
    private  List<String> peopleList;
    private  List<String> hourList;
    private  List<String> minuteList;
    private  List<AlarmBean> alarmBeanList;
    private SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children_warn, null);
        //setView();
        addNewNormalWarn = view.findViewById(R.id.addNormalWarn);
        sendNewWarn = view.findViewById(R.id.sendNewWarn);
        quaryAllWarn = view.findViewById(R.id.quaryAllWarn);
        newNormalWarnMsg = view.findViewById(R.id.new_normal_warn_text);
        banner = view.findViewById(R.id.banner);
        setBinder();
        findMyRelation();
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
                        Toast.makeText(getActivity(),editText.getText(),Toast.LENGTH_SHORT).show();
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
                peopleData();
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

                //这呢 别找了
                sp = getActivity().getSharedPreferences("childUserInfo",MODE_PRIVATE);
                final String senderId = sp.getString("id","");

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
                        String content = editText.getText().toString();
                        sendNewAlarm(receiver,hour,minute,"792997",content);
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        //能查的数据为 所绑定关联的接收人的所有闹钟
        quaryAllWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_quary_all_warn, null);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                ListView lvHistory = (ListView)view.findViewById(R.id.history_warn);
                historyWarnData();
                HistoryWarnAdapter historyWarnAdapter = new HistoryWarnAdapter(getActivity(),alarmBeanList,R.layout.item_history_alarm_warn);
                lvHistory.setAdapter(historyWarnAdapter);
                historyWarnAdapter.notifyDataSetChanged();
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });

        mListView = view.findViewById(R.id.list);
        normalWarnData();
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(this);
        return view;
    }

    public void sendNewAlarm(String[] receiver,String[] hour,String[] minute,String senderId,String content){
        //准备数据
        AlarmBean alarmBean = new AlarmBean();
        String time = hour[0]+":"+minute[0];
        alarmBean.setReceivePersonId(receiver[0]);
        alarmBean.setSendPersonId(senderId);
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
                                Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"发送失败error500",Toast.LENGTH_SHORT).show();
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
    public void historyWarnData(){
//        alarmBeanList = new ArrayList<>();
//        AlarmBean alarmBean = new AlarmBean();
//        alarmBean.setContent("鸡哥哥，记得按时吃屎！！！");
//        alarmBean.setAlarmTime("07:08");
//        alarmBean.setSendPerson("靳爹爹");
//        alarmBean.setReceivePerson("鸡哥哥");
//        alarmBeanList.add(alarmBean);
//        AlarmBean alarmBean1 = new AlarmBean();
//        alarmBean1.setContent("鸡哥哥，记得按时吃屎！！！");
//        alarmBean1.setAlarmTime("07:08");
//        alarmBean1.setSendPerson("靳爹爹");
//        alarmBean1.setReceivePerson("鸡哥哥");
//        alarmBeanList.add(alarmBean1);
//        AlarmBean alarmBean13 = new AlarmBean();
//        alarmBean13.setContent("鸡哥哥，记得按时吃屎！！！");
//        alarmBean13.setAlarmTime("07:08");
//        alarmBean13.setSendPerson("靳爹爹");
//        alarmBean13.setReceivePerson("鸡哥哥");
//        alarmBeanList.add(alarmBean13);
    }
    public void normalWarnData(){
        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.msg = "青岛爆炸满月：大量鱼虾死亡"+i;
            } else {
                item.msg = "欢迎你使用微信"+i;
            }
            mMessageItems.add(item);
        }
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
    public void peopleData(){
        //sPeople查询该账号关联的人，植入数据
        peopleList = new ArrayList<>();
        peopleList.add("195412");
        peopleList.add("222222");
    }
    public void setBinder(){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images = new ArrayList<>();
        images.add(R.mipmap.sss);
        images.add(R.mipmap.sss);
        images.add(R.mipmap.sss);
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        titles = new ArrayList<>();
        titles.add("jwbsb1");
        titles.add("jwbsb2");
        titles.add("jwbsb3");
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
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mMessageItems.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "删除成功",Toast.LENGTH_SHORT).show();
                }
            });

            return slideView;
        }

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
        peopleData();
        hourData();
        minuteData();
        sPeople.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sPeople",sPeople.getSelectedItem()+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sHour.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sPeople",sHour.getSelectedItem()+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sMinute.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sPeople",sMinute.getSelectedItem()+"");
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
                Toast.makeText(getActivity(),editText.getText(),Toast.LENGTH_SHORT).show();
                myDialog.dismiss();
            }
        });
        myDialog.setCancelable(true);
        myDialog.show();
        return true;
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
        int type = sharedPreferences.getInt("type",0);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receivePhone",myPhone);
            jsonObject.put("receiveType",type);
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
                        Type type = new TypeToken<List<AlarmBean>>(){}.getType();
                        List<String> parentPhones = gson.fromJson(json,type);
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
