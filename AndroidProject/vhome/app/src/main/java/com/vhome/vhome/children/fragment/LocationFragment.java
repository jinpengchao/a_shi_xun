package com.vhome.vhome.children.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.ChildrenMain;
import com.vhome.vhome.parents.TrackUtil.BitmapUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.vhome.vhome.parents.HttpLinked.connection;
import static com.superrtc.ContextUtils.getApplicationContext;
import static com.vhome.vhome.parents.TrackUtil.BitmapUtil.init;

public class LocationFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private Intent intent;
    private ImageView fath;
    private ImageView moth;
    private MapView mapView;
    private BaiduMap bdMap;
    private SharedPreferences sp;//获取entity_name
    private SharedPreferences share;//获取登录人手机号
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private List<String> select0 = new ArrayList<>();//spinner所用数组0
    private List<String> select1 = new ArrayList<>();//spinner所用数组1
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        view = inflater.inflate(R.layout.fragment_children_location,null);
        sp = getActivity().getSharedPreferences("myEntities",MODE_PRIVATE);
        share = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        init();
        initView();
        getLocation();//定位
        hideLogo();//隐藏百度logo
        zoomLevelOp();//设置比例尺
        String ip = (new MyApp()).getIp();
        final String url = "http://"+ip+":8080/vhome/SendIMEI";
        Log.e("url",url);
        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.getData()!=null){
                    Bundle get = msg.getData();
                    String[] params=  get.getString("pars").split("/n");
                    String faEntity=params[0];
                    String moEntity= params[1];
                    Log.e("entity",faEntity);
//                        if(faEntity.equals("nasp")&&moEntity.equals("nasp")) {
//                              Toast.makeText(getApplicationContext(),"您的父母还未注册",Toast.LENGTH_SHORT).show();
//                        }
                    setMessage("myTrace","myTrace");

                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                String phone = share.getString("phone","");
                String pars= getMapParams(phone,url);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("pars", pars);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }.start();
        return view;
    }

    public void initView(){
        fath = view.findViewById(R.id.menu_fa);
        moth = view.findViewById(R.id.menu_mo);
        mapView = view.findViewById(R.id.loc_map);
        bdMap = mapView.getMap();
        bdMap.setMyLocationEnabled(true);
        fath.setOnClickListener(this);
        moth.setOnClickListener(this);
    }

    private void setMessage(String faEntity,String moEntity) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("faEntity", faEntity);
        editor.putString("moEntity", moEntity);
        editor.commit();
    }

    //点击按钮后，加载弹出式菜单
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.menu_fa) {
            initMenu(v, R.menu.pop_menu_fa);
        }else{
            initMenu(v, R.menu.pop_menu_mo);
        }

    }
    //创建弹出式菜单对象
    public void initMenu(View v,int id){
        Context wrapper = new ContextThemeWrapper(getContext(), R.style.mainStyle);
        PopupMenu popup = new PopupMenu(wrapper, v);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(id, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(this);
        //显示(这一行代码不要忘记了)
        popup.show();
    }
    //弹出式菜单的单击事件处理
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        intent = new Intent();
        String faEntity = sp.getString("faEntity", " ");
        String moEntity = sp.getString("moEntity", " ");
        switch (item.getItemId()) {
            case R.id.fa_current:
                if (faEntity.equals("nasp")) {
                    Toast.makeText(getApplicationContext(),
                            "您还未关联您父亲的手机号",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.e("点击", "跳转");
                    intent.putExtra("sex", "man");
                    intent.setClass(getApplicationContext(), TracingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fa_history:
                if (faEntity.equals("nasp")) {
                    Toast.makeText(getApplicationContext(),
                            "您还未关联您父亲的手机号",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.e("点击", "跳转");
                    intent.putExtra("sex", "man");
                    intent.setClass(getApplicationContext(), TrackQueryActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mo_current:
                if (moEntity.equals("nasp")) {
                    Toast.makeText(getApplicationContext(),
                            "您还未关联您母亲的手机号",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.e("点击", "跳转");
                    intent.putExtra("sex", "woman");
                    intent.setClass(getApplicationContext(), TracingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mo_history:
                if (moEntity.equals("nasp")) {
                    Toast.makeText(getApplicationContext(),
                            "您还未关联您母亲的手机号",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.e("点击", "跳转");
                    intent.putExtra("sex", "woman");
                    intent.setClass(getApplicationContext(), TrackQueryActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void hideLogo(){
        View v = mapView.getChildAt(1);//第一层
        if(null!=v&&(v instanceof ImageView ||v instanceof ZoomControls)){
            v.setVisibility(View.INVISIBLE);
        }
    }
    private void zoomLevelOp(){
        bdMap.setMaxAndMinZoomLevel(19 ,13);
        //设置默认显示的范围
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16);
        bdMap.setMapStatus(msu);
    }

    private void getLocation(){
        //1. 创建定位服务客户端类的对象
        locationClient =
                new LocationClient(getApplicationContext());
        //2. 创建定位客户端选项类的对象，并设置定位参数
        locationClientOption = new LocationClientOption();
        //设置定位参数
        //打开GPS
        locationClientOption.setOpenGps(true);
        //设置定位间隔时间
        locationClientOption.setScanSpan(1000);
        //设置定位坐标系
        SDKInitializer.setCoordType(CoordType.GCJ02);
        //设置定位模式:高精度模式
        locationClientOption.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy
        );
        //需要定位的地址数据
        locationClientOption.setIsNeedAddress(true);
        //需要地址描述
        locationClientOption.setIsNeedLocationDescribe(true);
        //需要周边POI信息
        locationClientOption.setIsNeedLocationPoiList(true);
        //3. 将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4. 开始定位
        locationClient.start();
        //5. 给定位客户端端类的对象注册定位监听器
        locationClient.registerLocationListener(
                new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        //获取定位详细数据
                        //获取地址信息
                        String addr = bdLocation.getAddrStr();
                        //获取经纬度
                        double lat = bdLocation.getLatitude();
                        double lng = bdLocation.getLongitude();
                        //将定位数据显示在地图上
                        showLocOnMap(lat, lng);
                    }
                }
        );
    }

    private void showLocOnMap(double lat, double lng) {
        //  获取定位图标
        BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_point);
        //设置显示方式
        MyLocationConfiguration config =
                new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.COMPASS,//罗盘态
                        false,//是否需要方向
                        icon
                );
        //应用显示方式
        bdMap.setMyLocationConfiguration(config);
        LatLng latLng = new LatLng(
                lat + 0.00374531687912,
                lng + 0.008774687519);
        //显示
        MyLocationData locData = new MyLocationData
                .Builder()
                .latitude(latLng.latitude)
                .longitude(latLng.longitude)
                .build();
        bdMap.setMyLocationData(locData);

        //移动到中心位置
        MapStatusUpdate msu =
                MapStatusUpdateFactory.newLatLng(latLng);
        bdMap.animateMapStatus(msu);
    }

    private String getMapParams(String phone, String url) {
        HttpURLConnection con = connection(url,"?phone="+phone);
        BufferedWriter checkTo = null;
        BufferedReader checkFrom = null;
        String buffer;
        StringBuffer entity= new StringBuffer();
        //建立流
        try {
            //获取输入输出流建立连接
            checkTo = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            checkFrom = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((buffer = checkFrom.readLine())!=null){
                entity.append(buffer);
            }
            Log.e("参数",entity.toString());
            checkTo.close();
            checkFrom.close();
            return entity.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "nasp/nnasp";
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!locationClient.isStarted()){
            locationClient.start();
        }
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.stop();
        select0.clear();
        select1.clear();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BitmapUtil.clear();
    }
}