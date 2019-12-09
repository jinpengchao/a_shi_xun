package h.jpc.vhome.parents.fragment.fragment;


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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.PostBean;
import h.jpc.vhome.parents.fragment.adapter.HotSpotAdapter;
import h.jpc.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import h.jpc.vhome.util.ConnectionUtil;

public class HotspotFragment extends Fragment {
    private ListView lvHotSpot;
    private MyClickListener listener;
    private View view;
    private Handler handler;
    private List<PostBean> list = new ArrayList<>();
    private int CHANGE_STATUS = 2;
    private int POST_STATUS = 1;
    private Button addPost;
    private int addPostCode = 100;
    private int addPostResult = 200;
    private HotSpotAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_spot,null);
        getViews();
        registerListener();
//        list.clear();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                list = gson.fromJson(data,new TypeToken<List<PostBean>>(){}.getType());
                Log.i("hotspotFragment","list数据个数"+list.size());
                adapter = new HotSpotAdapter(getContext(),list,R.layout.item_hotspot);
                lvHotSpot.setAdapter(adapter);

                lvHotSpot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent simple = new Intent();
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

    private void getdata() {
        SharedPreferences sp = getActivity().getSharedPreferences((new MyApp()).getPathInfo(), Context.MODE_PRIVATE);
        final String personId = sp.getString("id","");
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetPostsServlet?personId="+personId);
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    util.sendMsg(data,POST_STATUS,handler);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        lvHotSpot = view.findViewById(R.id.lv_hot_spot);
        addPost = view.findViewById(R.id.addPost);
    }

    private void registerListener() {
        listener = new MyClickListener();
        addPost.setOnClickListener(listener);
    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.addPost:
                    Intent intenet  = new Intent();
                    intenet.setClass(getActivity(), NewPostActivity.class);
                    startActivityForResult(intenet,addPostCode);
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i("hotspot","调用了result方法");
//        if(requestCode==addPostCode){
//            Log.e("hotspot","调用了getdata()方法");
//            getdata();
//        }
    }

    @Override
    public void onResume() {

        super.onResume();
        Log.e("hotspot","调用了onresume方法");
        getdata();
    }
}
