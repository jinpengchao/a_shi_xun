package h.jpc.vhome.children.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.StatusCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.TrackUtil.CommonUtil;
import h.jpc.vhome.parents.TrackUtil.MapUtil;
import h.jpc.vhome.parents.TrackUtil.ViewUtil;

/**
 * 轨迹追踪
 */
public class TracingActivity extends BaseActivity{

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
    private int k=0;
    /**
     * 轨迹点集合
     */
    private List<LatLng> trackPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        trackApp = (MyApp) getApplicationContext();
        powerManager = (PowerManager) trackApp.getSystemService(Context.POWER_SERVICE);
        viewUtil = new ViewUtil();
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.an_tracing_mapView));
        mapUtil.setCenter(0);
        trackPoints = new ArrayList<>();
        initListener();
        //循环每隔30秒读取一次轨迹
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                trackApp.getCurrentLocation(trackListener);
                i--;
            }
        },0,1000*30);
        while(i==0){
            timer.cancel();
        }


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
                k++;
                Toast.makeText(TracingActivity.this,"获取定位次数"+k,Toast.LENGTH_SHORT).show();
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
                Log.e("经度", currentLatLng.longitude + "");
//                if (trackPoints.size() < 10) {
//                    mapUtil.drawHistoryTrack(trackPoints, false, 0);
//                } else {
//                    mapUtil.drawHistoryTrack(trackPoints, true, 0);
//                }
            }
        };
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


        if (trackApp.trackConf.contains("is_trace_started")
                && trackApp.trackConf.getBoolean("is_trace_started", true)) {
            // 退出app停止轨迹服务时，不再接收回调，将OnTraceListener置空
            trackApp.mClient.setOnTraceListener(null);
            trackApp.mClient.stopTrace(trackApp.mTrace, null);
            trackApp.mClient.clear();
        } else {
            trackApp.mClient.clear();
        }
        SharedPreferences.Editor editor = trackApp.trackConf.edit();
        editor.remove("is_trace_started");
        editor.remove("is_gather_started");
        editor.apply();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_antracing;
    }

}
