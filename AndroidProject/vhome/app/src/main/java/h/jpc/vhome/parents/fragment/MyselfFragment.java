package h.jpc.vhome.parents.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.user.entity.ParentUserInfo;
import h.jpc.vhome.util.ConnectionUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends Fragment {
    private ImageView blurImageView;
    private ImageView header;
    private TextView nikeName;
    private TextView ids;
    private TextView areas;
    private ImageView sexs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_myself,null);
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        header = (ImageView) view.findViewById(R.id.parent_head);
        nikeName = (TextView) view.findViewById(R.id.parent_name);
        ids = (TextView) view.findViewById(R.id.parent_id);
        areas = (TextView) view.findViewById(R.id.parent_area);
        sexs = (ImageView) view.findViewById(R.id.parent_sex);

        initData();
        initUserInfo();
        return view;
    }
    private void initData(){
        Glide.with(getActivity()).load(R.mipmap.header)
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);
        Glide.with(getActivity()).load(R.mipmap.header)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(header);
    }
    private void initUserInfo(){
        //准备数据
        SharedPreferences sp = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        final String phoneNums = sp.getString("phone","");
        final int type = sp.getInt("type",0);
        JSONObject json = new JSONObject();
        try {
            json.put("phone",phoneNums);
            json.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                                Log.e("你出来了没有","???");
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
                                nikeName.setText(nickName);
                                ids.setText(id);
                                areas.setText(area);
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
    public void saveUserInfo(String phone,String id,String nickName,String sex,String area,String achieve,String personalWord,String headimg){
        SharedPreferences sp = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone",phone);
        editor.putString("id",id);
        editor.putString("nickName",nickName);
        editor.putString("sex",sex);
        editor.putString("area",area);
        editor.putString("achieve",achieve);
        editor.putString("personalWord",personalWord);
        editor.putString("headImg",headimg);
        editor.commit();
    }
}
