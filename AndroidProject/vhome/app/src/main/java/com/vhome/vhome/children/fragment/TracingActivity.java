package com.vhome.vhome.children.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.StatusCodes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.TrackUtil.CommonUtil;
import com.vhome.vhome.parents.TrackUtil.MapUtil;
import com.vhome.vhome.parents.TrackUtil.ViewUtil;
import com.vhome.vhome.util.ConnectionUtil;

/**
 * 轨迹追踪
 */
public class TracingActivity extends myBaseActivity{

    private MyApp trackApp = null;

    private ViewUtil viewUtil = null;

    private PowerManager powerManager = null;

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;

    private OnTrackListener trackListener = null;

    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;

    private int i=10;
    private Timer timer;
    /**
     * 轨迹点集合
     */
    private List<LatLng> trackPoints = new ArrayList<LatLng>();

    /**
     *步数
     */
    private TimerTask task;
    private TextView step;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackApp = (MyApp) getApplicationContext();
        powerManager = (PowerManager) trackApp.getSystemService(Context.POWER_SERVICE);
        init();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.obj!=null) {
                    step.setText(msg.obj + "");
                }
            }
        };
    }

    private void init() {
        viewUtil = new ViewUtil();
        mapUtil = MapUtil.getInstance();
        step = findViewById(R.id.today_step);
        mapUtil.init((MapView) findViewById(R.id.an_tracing_mapView));
        mapUtil.setCenter(mCurrentDirection);
        initListener();
        //循环每隔30秒读取一次轨迹
        timer=new Timer();
        //onStart()每次进入再次执行

        //获取绑定手机的IMEI码
        SharedPreferences sp = getSharedPreferences("myEntities",MODE_PRIVATE);
        Intent get = getIntent();
        String sex = get.getStringExtra("sex");
        String imei=null;
        if(sex.equals("man")){
            imei = sp.getString("faEntity"," ");
            trackApp.setImei(imei);
        }else{
            imei = sp.getString("moEntity"," ");
            trackApp.setImei(imei);
        }
    }


    private void initListener() {

        trackListener = new OnTrackListener() {
            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    return;
                }
                LatestPoint point = response.getLatestPoint();
                if (null == point || CommonUtil.isZeroPoint(point.getLocation().getLatitude(), point.getLocation()
                        .getLongitude())) {
                    return;
                }

                LatLng currentLatLng = mapUtil.convertTrace2Map(point.getLocation());
                if (null == currentLatLng) {
                    return;
                }
                trackPoints.add(currentLatLng);
                if (trackPoints.size() < 10) {
                    mapUtil.drawHistoryTrack(trackPoints, false);
                } else {
                    mapUtil.drawHistoryTrack(trackPoints, true);
                }
            }
        };
    }

    public String getTodayStep(){
        String data = null;
        String ip = (new MyApp()).getIp();
        try {
            URL url = new URL("http://"+ip+":8080/vhomeSpring/manageStep/find");
            //?绑定参数是get请求
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            if(connection.getResponseCode() == 200){
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                data = reader.readLine();
                Log.e("数据：",data);
                in.close();
                reader.close();
                return data;
            }else{
                System.out.println("post错误代码： "+connection.getResponseCode());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 适配android M，检查权限
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        }

        // 在Android 6.0及以上系统，若定制手机使用到doze模式，请求将应用添加到白名单。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = trackApp.getPackageName();
            boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName);
            if (!isIgnoring) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                trackApp.getCurrentLocation(trackListener);
                String step = getTodayStep();
                Log.e("步数：","多少"+step);
                Message message = handler.obtainMessage();
                message.obj = step;
                handler.sendMessage(message);
                --i;
                if(i==0){
                    this.cancel();
                }else{
                    Log.e("次数",i+"");
                }
            }
        },0,1000*10);
    }

    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 定位精确位置
        addPermission(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 读取手机状态
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapUtil.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapUtil.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != trackPoints) {
            trackPoints.clear();
        }

        trackPoints = null;
        mapUtil.clear();
        trackApp.mClient.setOnTraceListener(null);
        trackApp.mClient.stopTrace(trackApp.mTrace, null);
        trackApp.mClient.clear();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_antracing;
    }

}
