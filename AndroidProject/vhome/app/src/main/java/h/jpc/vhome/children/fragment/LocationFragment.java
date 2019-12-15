package h.jpc.vhome.children.fragment;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.children.ChildrenMain;
import h.jpc.vhome.parents.TrackUtil.BitmapUtil;

import static android.content.Context.MODE_PRIVATE;
import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;
import static h.jpc.vhome.parents.HttpLinked.connection;

public class LocationFragment extends Fragment {

    private Intent intent;
    private Spinner fath;
    private Spinner moth;
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
        BitmapUtil.init();
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
                        if(faEntity.equals("nasp")&&moEntity.equals("nasp")) {
                              Toast.makeText(getApplicationContext(),"您的父母还未注册",Toast.LENGTH_SHORT).show();
                        }
                        setMessage(faEntity,moEntity);

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

    private void init() {
        select0.add("查看父亲");
        select0.add("父亲当前轨迹");
        select0.add("父亲历史轨迹");
        select1.add("查看母亲");
        select1.add("母亲当前轨迹");
        select1.add("母亲历史轨迹");
        setSpinner();
    }

    public void initView(){
        fath = view.findViewById(R.id.bt_fath);
        moth = view.findViewById(R.id.bt_moth);
        mapView = view.findViewById(R.id.loc_map);
        bdMap = mapView.getMap();
        bdMap.setMyLocationEnabled(true);
    }

    private void setMessage(String faEntity,String moEntity) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("faEntity", faEntity);
        editor.putString("moEntity", moEntity);
        editor.commit();
    }

    public  void setSpinner() {
        final ArrayAdapter adapter0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, select0);
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, select1);
        adapter0.setDropDownViewResource(R.layout.item_drop);
        adapter1.setDropDownViewResource(R.layout.item_drop);
        fath.setAdapter(adapter0);
        moth.setAdapter(adapter1);
        fath.setSelection(0, true);
        moth.setSelection(0, true);
        fath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent();
                intent.putExtra("sex", "man");
                String faEntity = sp.getString("faEntity", " ");
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        if (faEntity.equals("nasp")) {
                            Toast.makeText(getApplicationContext(),
                                    "您还未关联您父亲的手机号",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Log.e("点击", "跳转");
                            intent.setClass(getApplicationContext(), TracingActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (faEntity.equals("nasp")) {
                            Toast.makeText(getApplicationContext(),
                                    "您还未关联您父亲的手机号",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Log.e("点击", "跳转");
                            intent.setClass(getApplicationContext(), TrackQueryActivity.class);
                            startActivity(intent);
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        moth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent();
                intent.putExtra("sex", "woman");
                String moEntity = sp.getString("moEntity", " ");
                switch (position) {
                    case 0:
                        adapter1.getItem(0).toString();
                        break;
                    case 1:
                        if (moEntity.equals("nasp")) {
                            Toast.makeText(getApplicationContext(),
                                    "您还未关联您母亲的手机号",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Log.e("点击", "跳转");
                            intent.setClass(getApplicationContext(), TracingActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (moEntity.equals("nasp")) {
                            Toast.makeText(getApplicationContext(),
                                    "您还未关联您母亲的手机号",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Log.e("点击", "跳转");
                            intent.setClass(getApplicationContext(), TrackQueryActivity.class);
                            startActivity(intent);
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
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
        return "null/nnull";
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.stop();
        select0.clear();
        select1.clear();
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
    public void onDestroy() {
        super.onDestroy();
        BitmapUtil.clear();
    }
}
