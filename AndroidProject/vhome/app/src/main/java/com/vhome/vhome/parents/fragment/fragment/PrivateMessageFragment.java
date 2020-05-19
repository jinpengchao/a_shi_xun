package com.vhome.vhome.parents.fragment.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.util.ConnectionUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PrivateMessageFragment extends Fragment {
    private final static String TAG = "HotspotFragment";
    private ListView lvAdminMessage;
    private View view;
    private Handler handler;
    private List<PostBean> adminMessageList = new ArrayList<>();
    private HotSpotAdapter adapter;
    private SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_message,null);
        getViews();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                adminMessageList.clear();
                adminMessageList = gson.fromJson(data,new TypeToken<List<PostBean>>(){}.getType());;

                adapter = new HotSpotAdapter(getContext(),adminMessageList,R.layout.item_hotspot);
                lvAdminMessage.setAdapter(adapter);

                lvAdminMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent simple = new Intent();
                        simple.putExtra("post",adminMessageList.get(i));
                        simple.putExtra("personId",adminMessageList.get(i).getPersonId());
                        simple.setClass(getContext(), CommentActivity.class);
                        startActivity(simple);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        };
//        getdata();
        return view;
    }

    public final void getdata() {
        adminMessageList.clear();
        SharedPreferences sp = getActivity().getSharedPreferences((new MyApp()).getPathInfo(), MODE_PRIVATE);
        final String personId = sp.getString("id","");
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetPostsServlet?personId="+personId);
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    util.sendMsg(data,1,handler);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        sp=getActivity().getPreferences(MODE_PRIVATE);
        lvAdminMessage = view.findViewById(R.id.lv_hot_spot);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("hotspot","调用了onResume方法");
        getdata();

    }
}
