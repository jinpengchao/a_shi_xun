package h.jpc.vhome.parents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import h.jpc.vhome.MainActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.ConversationListFragment;
import h.jpc.vhome.parents.Receiver.TrackReceiver;
import h.jpc.vhome.parents.Services.TrackService;
import h.jpc.vhome.parents.TrackUtil.BitmapUtil;
import h.jpc.vhome.parents.TrackUtil.CommonUtil;
import h.jpc.vhome.parents.TrackUtil.Constants;
import h.jpc.vhome.parents.TrackUtil.MapUtil;
import h.jpc.vhome.parents.TrackUtil.ToastUtils;
import h.jpc.vhome.parents.TrackUtil.ViewUtil;
import h.jpc.vhome.parents.fragment.CommunityFragment;
import h.jpc.vhome.parents.fragment.HomeFragment;
import h.jpc.vhome.parents.fragment.MyselfFragment;
import h.jpc.vhome.parents.fragment.alarm.AlarmService;
import h.jpc.vhome.parents.model.CurrentLocation;
import h.jpc.vhome.user.entity.EventBean;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static h.jpc.vhome.parents.HttpLinked.connection;

public class ParentMain extends AppCompatActivity implements SensorEventListener {
    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();

    private SDKReceiver mReceiver;

    //定时器
    private Timer timer;
    private int sumTime = 25;
    private MyApp trackApp = null;

    private ViewUtil viewUtil = null;

    private PowerManager powerManager = null;

    private PowerManager.WakeLock wakeLock = null;

    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    /**
     * 轨迹监听器(用于接收纠偏后实时位置回调)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity监听器(用于接收实时定位回调)
     */
    private OnEntityListener entityListener = null;

    /**
     * 实时定位任务
     */
    private RealTimeHandler realTimeHandler = new RealTimeHandler();

    private RealTimeLocRunnable realTimeLocRunnable = null;


    private int notifyId = 0;
    /**
     * 打包周期
     */
    public int packInterval = Constants.DEFAULT_PACK_INTERVAL;

    /**
     * 查询历史轨迹开始时间
     */
    public long startTime = CommonUtil.getCurrentTime();

    /**
     * 查询历史轨迹结束时间
     */
    public long endTime = CommonUtil.getCurrentTime();
    /**
     * 轨迹点集合
     */
    private List<LatLng> trackPoints;

    private boolean firstLocate = true;

    private TrackReceiver trackReceiver = null;

    private NotificationManager notificationManager = null;

    private SensorManager mSensorManager;

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;

    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;

    private String city;//获取地址信息进行获取天气参数之用
    private Handler handler;
    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();

            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Toast.makeText(ParentMain.this,"AK验证失败，地图功能无法正常使用", Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                Toast.makeText(ParentMain.this,"AK验证成功", Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(ParentMain.this,"网络错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);
        Intent intent = new Intent();
        intent.setClass(ParentMain.this, AlarmService.class);
        startService(intent);
        setTabHost();
        //鹰眼轨迹操作
        // AK的授权需要一定的时间，在授权成功之前地图相关操作会出现异常；
        // AK授权成功后会发送广播通知，我们这里注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        initListener();
        init();

    }
//    //后台运行
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    public void setTabHost(){
        //获取FragmentTabHost对象
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化参数
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent
        );
        //tab1
        TabHost.TabSpec tabSpec1 = fragmentTabHost
                .newTabSpec("tag1")
                .setIndicator(getTabSpaceView("tag1",R.mipmap.home,"首页"));
        fragmentTabHost.addTab(tabSpec1,
                HomeFragment.class,
                null
        );
        //tab2
        TabHost.TabSpec tabSpec2 = fragmentTabHost
                .newTabSpec("tag2")
                .setIndicator(getTabSpaceView("tag2",R.mipmap.comment,"社区"));
        fragmentTabHost.addTab(tabSpec2,
                CommunityFragment.class,
                null
        );
        //tab3
        TabHost.TabSpec tabSpec3 = fragmentTabHost
                .newTabSpec("tag3")
                .setIndicator(getTabSpaceView("tag3",R.mipmap.child,"子女"));
        fragmentTabHost.addTab(tabSpec3,
                ConversationListFragment.class,
                null
        );
        //tab4
        TabHost.TabSpec tabSpec4 = fragmentTabHost
                .newTabSpec("tag4")
                .setIndicator(getTabSpaceView("tag4",R.mipmap.me,"我"));
        fragmentTabHost.addTab(tabSpec4,
                MyselfFragment.class,
                null
        );
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.mipmap.home1);
        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home1);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment1);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child1);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.choseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.notChoseColor));
                        break;
                    case "tag4":
                        imageViewMap.get("tag1").setImageResource(R.mipmap.home);
                        imageViewMap.get("tag2").setImageResource(R.mipmap.comment);
                        imageViewMap.get("tag3").setImageResource(R.mipmap.child);
                        imageViewMap.get("tag4").setImageResource(R.mipmap.me1);
                        textViewMap.get("tag1").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag2").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag3").setTextColor(getResources().getColor(R.color.notChoseColor));
                        textViewMap.get("tag4").setTextColor(getResources().getColor(R.color.choseColor));
                        break;
                }
            }
        });
    }
    public View getTabSpaceView(String tag,  int imageResId,String title){
        //加载布局文件
        LayoutInflater layoutInflater1 = getLayoutInflater();
        View view = layoutInflater1.inflate(R.layout.tab_space_parent,null);
        ImageView imageView = view.findViewById(R.id.icon);//去tab_space去找id
        imageView.setImageResource(imageResId);
        //Text对象
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;
    }
    /**
     * 订阅事件(EventBean)
     * @param eventBean 发布的事件对象
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventBeanStikyEvent(EventBean eventBean){
        MyselfFragment myselfFragment = new MyselfFragment();
        myselfFragment.nikeName.setText(eventBean.getNickName());
        myselfFragment.areas.setText(eventBean.getArea());
    }
    //鹰眼轨迹------------------------------------------
    private void init() {
        trackApp = (MyApp) getApplicationContext();
        viewUtil = new ViewUtil();
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.tracing_mapView));
        mapUtil.setCenter(mCurrentDirection);//设置地图中心点
        powerManager = (PowerManager) trackApp.getSystemService(Context.POWER_SERVICE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //每次方向改变，重新给地图设置定位数据，用上一次的经纬度
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {// 方向改变大于1度才设置，以免地图上的箭头转动过于频繁
            mCurrentDirection = (int) x;
            if (!CommonUtil.isZeroPoint(CurrentLocation.latitude, CurrentLocation.longitude)) {
                mapUtil.updateMapLocation(new LatLng(CurrentLocation.latitude, CurrentLocation.longitude), (float) mCurrentDirection);
            }
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    //APP启动鹰眼轨迹即自动打开
    public void AutoStart(){
        if (trackApp.isTraceStarted) {
            trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);//停止服务
        } else {
            trackApp.mClient.startTrace(trackApp.mTrace, traceListener);//开始服务
        }
    }

    //24小时之后自动关闭
    public void AutoEnd(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                --sumTime;
                Log.e("剩余",sumTime+"小时");
            }
        };
        timer.schedule(task,0,1000*3600);
        if(sumTime==0){
            trackApp.mClient.stopGather(traceListener);
            trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);
            timer.cancel();//销毁定时器
        }
    }
    /**
     * 实时定位任务
     */
    class RealTimeLocRunnable implements Runnable {

        private int interval = 0;

        public RealTimeLocRunnable(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            trackApp.getCurrentLocation(entityListener, trackListener);
            realTimeHandler.postDelayed(this, interval * 1000);
        }
    }

    public void startRealTimeLoc(int interval) {
        realTimeLocRunnable = new RealTimeLocRunnable(interval);
        realTimeHandler.post(realTimeLocRunnable);
    }

    public void stopRealTimeLoc() {
        if (null != realTimeHandler && null != realTimeLocRunnable) {
            realTimeHandler.removeCallbacks(realTimeLocRunnable);
        }
        trackApp.mClient.stopRealTimeLoc();
    }

    private void initListener() {

        trackListener = new OnTrackListener() {

            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                //经过服务端纠偏后的最新的一个位置点，回调
                try {
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

                    if (firstLocate) {
                        firstLocate = false;
                        Toast.makeText(ParentMain.this, "起点获取中，请稍后...", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //当前经纬度
                    CurrentLocation.locTime = point.getLocTime();
                    CurrentLocation.latitude = currentLatLng.latitude;
                    CurrentLocation.longitude = currentLatLng.longitude;

                    if (trackPoints == null) {
                        return;
                    }
                    trackPoints.add(currentLatLng);
                    endTime = CommonUtil.getCurrentTime();
                    ToastUtils.showToastBottom(ParentMain.this, "差值：" + (endTime - startTime));
                    if (trackPoints.size() == 0) {
                        Toast.makeText(ParentMain.this, "获取位置失败", Toast.LENGTH_SHORT).show();
                    }else{
                        mapUtil.updateStatus(currentLatLng,true);
                        if(CurrentLocation.latitude!=0&&CurrentLocation.longitude!=0) {
                            download(handler, CurrentLocation.latitude, CurrentLocation.longitude //经纬度
                                    , "A7:2F:D7:BC:E8:0C:79:00:26:4C:80:0C:A5:77:91:F1:42:78:F9:90;com.example.vhomemapofparents");//安全码
                        }
                        Log.e("提示","纠偏之后的");

                    }
                } catch (Exception x) {

                }
            }

            @Override
            public void onDistanceCallback(DistanceResponse response) {
                super.onDistanceCallback(response);
            }
        };

        //接收实时定位回调
        entityListener = new OnEntityListener() {

            @Override
            public void onReceiveLocation(TraceLocation location) {

                if (StatusCodes.SUCCESS != location.getStatus() || CommonUtil.isZeroPoint(location.getLatitude(),
                        location.getLongitude())) {
                    return;
                }
                LatLng currentLatLng = mapUtil.convertTraceLocation2Map(location);
                CurrentLocation.locTime = CommonUtil.toTimeStamp(location.getTime());
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;
                if (null == currentLatLng) {
                    return;
                }
                if(CurrentLocation.latitude!=0&&CurrentLocation.longitude!=0) {
                    download(handler, CurrentLocation.latitude, CurrentLocation.longitude //经纬度
                            , "A7:2F:D7:BC:E8:0C:79:00:26:4C:80:0C:A5:77:91:F1:42:78:F9:90;com.example.vhomemapofparents");//安全码
                }
                if (null != mapUtil) {
                    mapUtil.animateMapStatus(currentLatLng);
                    Log.e("提示","实时的");
                }
            }
        };

        traceListener = new OnTraceListener() {

            /**
             * 绑定服务回调接口
             * @param errorNo  状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>1：失败</pre>
             */

            @Override
            public void onBindServiceCallback(int errorNo, String message) {
                viewUtil.showToast(ParentMain.this,
                        String.format("onBindServiceCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 开启服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>10000：请求发送失败</pre>
             *                <pre>10001：服务开启失败</pre>
             *                <pre>10002：参数错误</pre>
             *                <pre>10003：网络连接失败</pre>
             *                <pre>10004：网络未开启</pre>
             *                <pre>10005：服务正在开启</pre>
             *                <pre>10006：服务已开启</pre>
             */

            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    trackApp.isTraceStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_trace_started", true);
                    editor.commit();
                    registerReceiver();
                    if(errorNo==0){
                        if (trackApp.isGatherStarted) {
                            trackApp.mClient.stopGather(traceListener);
                        } else {
                            trackApp.mClient.setInterval(Constants.DEFAULT_GATHER_INTERVAL, packInterval);
                            trackApp.mClient.startGather(traceListener);//开启采集
                            startTime = CommonUtil.getCurrentTime();
                        }
                    }
                }
                viewUtil.showToast(ParentMain.this,
                        String.format("onStartTraceCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 停止服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>11000：请求发送失败</pre>
             *                <pre>11001：服务停止失败</pre>
             *                <pre>11002：服务未开启</pre>
             *                <pre>11003：服务正在停止</pre>
             */

            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    trackApp.isTraceStarted = false;
                    trackApp.isGatherStarted = false;
                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_trace_started");
                    editor.remove("is_gather_started");
                    editor.commit();
                    unregisterPowerReceiver();
                    firstLocate = true;
                }
                viewUtil.showToast(ParentMain.this,
                        String.format("onStopTraceCallback, errorNo:%d, message:%s ", errorNo, message));
            }


            /**
             * 开启采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>12000：请求发送失败</pre>
             *                <pre>12001：采集开启失败</pre>
             *                <pre>12002：服务未开启</pre>
             */
            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    trackApp.isGatherStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_gather_started", true);
                    editor.commit();

                    stopRealTimeLoc();
                    startRealTimeLoc(packInterval);
                }
                viewUtil.showToast(ParentMain.this,
                        String.format("onStartGatherCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 停止采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>13000：请求发送失败</pre>
             *                <pre>13001：采集停止失败</pre>
             *                <pre>13002：服务未开启</pre>
             */

            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    trackApp.isGatherStarted = false;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_gather_started");
                    editor.commit();

                    firstLocate = true;
                    stopRealTimeLoc();
                    startRealTimeLoc(Constants.LOC_INTERVAL);

                }
                viewUtil.showToast(ParentMain.this,
                        String.format("onStopGatherCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 0x03 || messageType > 0x04) {
                    viewUtil.showToast(ParentMain.this, pushMessage.getMessage());
                    return;
                }
                FenceAlarmPushInfo alarmPushInfo = pushMessage.getFenceAlarmPushInfo();
                if (null == alarmPushInfo) {
                    viewUtil.showToast(ParentMain.this,
                            String.format("onPushCallback, messageType:%d, messageContent:%s ", messageType,
                                    pushMessage));
                    return;
                }
                StringBuffer alarmInfo = new StringBuffer();
                alarmInfo.append("您于")
                        .append(CommonUtil.getHMS(alarmPushInfo.getCurrentPoint().getLocTime() * 1000))
                        .append(alarmPushInfo.getMonitoredAction() == MonitoredAction.enter ? "进入" : "离开")
                        .append(messageType == 0x03 ? "云端" : "本地")
                        .append("围栏：").append(alarmPushInfo.getFenceName());

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    Notification notification = new Notification.Builder(trackApp)
                            .setContentTitle("百度鹰眼报警推送")
                            .setContentText(alarmInfo.toString())
                            .setWhen(System.currentTimeMillis()).build();
                    notificationManager.notify(notifyId++, notification);
                }
            }

            @Override
            public void onInitBOSCallback(int errorNo, String message) {
                viewUtil.showToast(ParentMain.this,
                        String.format("onInitBOSCallback, errorNo:%d, message:%s ", errorNo, message));

            }
        };

    }

    static class RealTimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * 注册广播（电源锁、GPS状态）
     */
    private void registerReceiver() {
        if (trackApp.isRegisterReceiver) {
            return;
        }

        if (null == wakeLock) {
            wakeLock = powerManager
                    .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "name:track upload");
        }
        if (null == trackReceiver) {
            trackReceiver = new TrackReceiver(wakeLock);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(StatusCodes.GPS_STATUS_ACTION);
        trackApp.registerReceiver(trackReceiver, filter);
        trackApp.isRegisterReceiver = true;

    }

    private void unregisterPowerReceiver() {
        if (!trackApp.isRegisterReceiver) {
            return;
        }
        if (null != trackReceiver) {
            trackApp.unregisterReceiver(trackReceiver);
        }
        trackApp.isRegisterReceiver = false;
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

    public void download(final Handler handler, final Double latitude, final Double longitude, final String mCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL
                    String url_s = "http://api.map.baidu.com/geocoder" +
                            "?output=json&pois=1&ak=WPj4N17RL69xM8VCwtVLawXdk9DRuwBO&location=" +
                            latitude + "," + longitude +
                            "&mcode=" + mCode;
                    HttpURLConnection conn = connection(url_s);
                    //这里才真正获取到了数据
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader input = new InputStreamReader(inputStream);
                    BufferedReader buffer = new BufferedReader(input);
                    if (conn.getResponseCode() == 200) {  //200意味着返回的是"OK"
                        String inputLine;
                        StringBuffer resultData = new StringBuffer();  //StringBuffer字符串拼接很快
                        while ((inputLine = buffer.readLine()) != null) {
                            resultData.append(inputLine);
                        }
                        String text = resultData.toString();
                        if(!text.contains("<!DOCTYPE html>")) {
                            parseJson(text);
                            handler.sendEmptyMessage(1);
                        }else{
                            Log.e("ERROR","网络错误");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析返回的json串获取地址(只获取城市名)
    private String parseJson(String text) {
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(text);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject addressMore = result.getJSONObject("addressComponent");
            city = addressMore.getString("city");
            SharedPreferences sp = getSharedPreferences("cityInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("city",city);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 适配android M，检查权限
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        }

        if (trackApp.trackConf.contains("is_trace_started")
                && trackApp.trackConf.contains("is_gather_started")
                && trackApp.trackConf.getBoolean("is_trace_started", false)
                && trackApp.trackConf.getBoolean("is_gather_started", false)) {
            startRealTimeLoc(packInterval);
        } else {
            startRealTimeLoc(Constants.LOC_INTERVAL);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapUtil.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);

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

    @Override
    protected void onPause() {
        super.onPause();
        mapUtil.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRealTimeLoc();
        mSensorManager.unregisterListener(this);
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRealTimeLoc();
        trackPoints.clear();
        trackPoints = null;
        mapUtil.clear();
        unregisterPowerReceiver();


        if (trackApp.trackConf.contains("is_trace_started")
                && trackApp.trackConf.getBoolean("is_trace_started", true)) {
            // 退出app停止轨迹服务时，不再接收回调，将OnTraceListener置空
            trackApp.mClient.setOnTraceListener(null);
            trackApp.mClient.stopTrace(trackApp.mTrace, null);
            trackApp.mClient.clear();
        } else {
            trackApp.mClient.clear();
        }
        trackApp.isTraceStarted = false;
        trackApp.isGatherStarted = false;
        SharedPreferences.Editor editor = trackApp.trackConf.edit();
        editor.remove("is_trace_started");
        editor.remove("is_gather_started");
        editor.commit();

        BitmapUtil.clear();
    }
}
