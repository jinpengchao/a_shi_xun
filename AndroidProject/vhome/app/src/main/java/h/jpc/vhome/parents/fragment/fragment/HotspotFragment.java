package h.jpc.vhome.parents.fragment.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.entity.Post;

public class HotspotFragment extends Fragment {
    private ListView lvHotSpot;
    private MyClickListener listener;
    private View view;
    private Handler handler;
    private List<Post> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_spot,null);
        getViews();
        registerListener();
        list.clear();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                list = gson.fromJson(data,new TypeToken<List<Post>>(){}.getType());

            }
        };
        getdata();
        return view;
    }

    private void getdata() {
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetPostServlet");
                    URLConnection connection = url.openConnection();
                    InputStream is = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                    String data = bufferedReader.readLine();
                    bufferedReader.close();
                    is.close();

                    Bundle bundle = new Bundle();
                    bundle.putString("data",data);
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        lvHotSpot = view.findViewById(R.id.lv_hot_sopt);

    }

    private void registerListener() {
        listener = new MyClickListener();

    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

        }
    }
}
