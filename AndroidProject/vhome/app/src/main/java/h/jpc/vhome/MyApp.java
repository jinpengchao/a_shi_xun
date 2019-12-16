package h.jpc.vhome;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.TransportMode;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.multidex.MultiDex;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import h.jpc.vhome.chat.database.UserEntry;
import h.jpc.vhome.chat.entity.NotificationClickEventReceiver;
import h.jpc.vhome.chat.pickerimage.utils.StorageUtil;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.imagepicker.GlideImageLoader;
import h.jpc.vhome.chat.utils.imagepicker.ImagePicker;
import h.jpc.vhome.chat.utils.imagepicker.view.CropImageView;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.parents.TrackUtil.CommonUtil;
import h.jpc.vhome.parents.TrackUtil.NetUtil;
import h.jpc.vhome.parents.fragment.radio_ximalaya.utils.LogUtil;

public class MyApp extends com.activeandroid.app.Application {
    public static final String CONV_TITLE = "conv_title";
    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;
    public static final int BUSINESS_CARD = 7;
    public static final int REQUEST_CODE_SEND_FILE = 26;


    public static final int RESULT_CODE_ALL_MEMBER = 22;
    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static List<Message> forwardMsg = new ArrayList<>();

    public static long registerOrLogin = 1;
    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final int REQUEST_CODE_CROP_PICTURE = 18;
    public static final int REQUEST_CODE_CHAT_DETAIL = 14;
    public static final int RESULT_CODE_FRIEND_INFO = 17;
    public static final int REQUEST_CODE_ALL_MEMBER = 21;
    public static final int RESULT_CODE_EDIT_NOTENAME = 29;
    public static final String NOTENAME = "notename";
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;
    public static final int SEARCH_AT_MEMBER_CODE = 33;

    public static final int RESULT_BUTTON = 2;
    public static final int START_YEAR = 1900;
    public static final int END_YEAR = 2050;
    public static final int RESULT_CODE_SELECT_FRIEND = 23;

    public static final int REQUEST_CODE_SELECT_ALBUM = 10;
    public static final int RESULT_CODE_SELECT_ALBUM = 11;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int REQUEST_CODE_SEND_LOCATION = 24;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int REQUEST_CODE_FRIEND_LIST = 17;
    public static final int ON_GROUP_EVENT = 3004;
    public static final String DELETE_MODE = "deleteMode";
    public static final int RESULT_CODE_ME_INFO = 20;

    public static final String DRAFT = "draft";
    public static final String CONV_TYPE = "conversationType"; //value使用 ConversationType
    public static final String ROOM_ID = "roomId";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String MSG_JSON = "msg_json";
    public static final String MSG_LIST_JSON = "msg_list_json";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";

    public static final String MEMBERS_COUNT = "membersCount";

    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static String VIDEO_DIR = "sdcarVIDEOd/JChatDemo/sendFiles/";
    public static String THUMP_PICTURE_DIR;
    public static final String TARGET_ID = "targetId";
    public static final String ATUSER = "atuser";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static int maxImgCount;               //允许选择图片最大数
    public static final String GROUP_NAME = "groupName";
    public static String groupAvatarPath;

    public static Context context;
//    public static LocationService locationService;

    public static List<GroupInfo> mGroupInfoList = new ArrayList<>();
    public static List<UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<UserInfo> mSearchGroup = new ArrayList<>();
    public static List<UserInfo> mSearchAtMember = new ArrayList<>();
    public static List<Message> ids = new ArrayList<>();
    public static List<UserInfo> alreadyRead = new ArrayList<>();
    public static List<UserInfo> unRead = new ArrayList<>();
    public static List<String> forAddFriend = new ArrayList<>();
    public static List<String> forAddIntoGroup = new ArrayList<>();
    public static Conversation delConversation;
    public static ArrayList<String> selectedUser;
    //10.7.89.13
    //10.7.89.128  192.168.199.158
    private String ip = "10.7.89.237";
    private String pathInfo = "parentUserInfo";

    private static Handler sHandler=null;
    private static Context SContext=null;
    //--------------鹰眼轨迹-----------------
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    private LocRequest locRequest = null;

    public Context mContext = null;

    public SharedPreferences trackConf = null;

    private Notification notification = null;

    /**
     * 轨迹客户端
     */
    public LBSTraceClient mClient = null;

    /**
     * 轨迹服务
     */
    public Trace mTrace = null;

    /**
     * 轨迹服务ID
     */
    public long serviceId = 218002;//这里是申请的鹰眼服务id

    /**
     * Entity标识
     */
    public String entityName = "myTrace";

    public boolean isRegisterReceiver = false;

    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;

    public static int screenWidth = 0;

    public static int screenHeight = 0;
    //--------------鹰眼轨迹-------------------

    @Override
    public void onCreate() {
        super.onCreate();
        //连接喜马拉雅
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        if(DTransferConstants.isRelease) {
            String mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af";
            mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8");
            mXimalaya.setPackid("com.app.test.android");
            mXimalaya.init(this ,mAppSecret);
        } else {
            String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
            mXimalaya.setPackid("com.ximalaya.qunfeng");
            mXimalaya.init(this ,mAppSecret);
        }
        XmPlayerManager.getInstance(this).init();
        //初始化LogUtil
        LogUtil.init(this.getPackageName(),false);
        sHandler=new Handler();
        SContext=getBaseContext();


        x.Ext.init(this);
        context = getApplicationContext();
        THUMP_PICTURE_DIR = context.getFilesDir().getAbsolutePath() + "/JChatDemo";
        StorageUtil.init(context, null);
        Fresco.initialize(getApplicationContext());
//        SDKInitializer.initialize(getApplicationContext());
//        locationService = new LocationService(getApplicationContext());


        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.setDebugMode(true);
        SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
        //设置Notification的模式
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());
        initImagePicker();

        //鹰眼轨迹初始化
        mContext = getApplicationContext();
//        entityName = CommonUtil.getImei(this);
        entityName = "myTrace";
        // 若为创建独立进程，则不初始化成员变量
        if ("com.baidu.track:remote".equals(CommonUtil.getCurProcessName(mContext))) {
            return;
        }
        //地图显示
        SDKInitializer.initialize(mContext);
        initNotification();
        getScreenSize();
        mClient = new LBSTraceClient(mContext);
        mTrace = new Trace(serviceId, entityName);

        trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
        locRequest = new LocRequest(serviceId);

        mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }


            @Override
            public Map<String, String> onTrackAttributeCallback(long l) {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }
        });

        clearTraceStatus();
    }
    public  static Context getAppContext(){
        return SContext;
    }

    public  static Handler getsHandler(){
        return sHandler;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    public static void setPicturePath(String appKey) {
        if (!SharePreferenceManager.getCachedAppKey().equals(appKey)) {
            SharePreferenceManager.setCachedAppKey(appKey);
            PICTURE_DIR = "sdcard/JChatDemo/pictures/" + appKey + "/";
        }
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //鹰眼轨迹-----------------------------------------------
    public void setImei(String entityName) {
        this.entityName = entityName;
    }
    public String getImei() {
        return entityName;
    }
    /**
     * 当轨迹服务开启，且采集数据开启之后，显示在地图上的位置点可以用服务端纠偏后的最新点，
     * 因为通过mClient.queryRealTimeLoc获取的点可能不精确，出现漂移等情况。
     *
     */
    public void getCurrentLocation(OnEntityListener entityListener, OnTrackListener trackListener) {
        // 网络连接正常，开启服务及采集，则查询纠偏后实时位置；否则进行实时定位
        if (NetUtil.isNetworkAvailable(mContext)
                && trackConf.contains("is_trace_started")
                && trackConf.contains("is_gather_started")
                && trackConf.getBoolean("is_trace_started", false)
                && trackConf.getBoolean("is_gather_started", false)) {
            LatestPointRequest request = new LatestPointRequest(getTag(), serviceId, entityName);
            ProcessOption processOption = new ProcessOption();
            processOption.setRadiusThreshold(50);
            processOption.setTransportMode(TransportMode.walking);
            processOption.setNeedDenoise(true);
            processOption.setNeedMapMatch(true);
            request.setProcessOption(processOption);
            mClient.queryLatestPoint(request, trackListener);
        } else {
            mClient.queryRealTimeLoc(locRequest, entityListener);
        }
    }
    //查询轨迹绘制之用，所以必须是服务端纠偏之后的轨迹，不获取实时轨迹
    public void getCurrentLocation(OnTrackListener trackListener) {
        // 网络连接正常，开启服务及采集，则查询纠偏后实时位置；否则进行实时定位
        if (NetUtil.isNetworkAvailable(mContext)) {
            LatestPointRequest request = new LatestPointRequest(getTag(), serviceId, entityName);
            ProcessOption processOption = new ProcessOption();
            processOption.setRadiusThreshold(50);
            processOption.setTransportMode(TransportMode.walking);
            processOption.setNeedDenoise(true);
            processOption.setNeedMapMatch(true);
            request.setProcessOption(processOption);
            mClient.queryLatestPoint(request, trackListener);
        } else {
            Log.e("ERROR","网络错误");
        }
    }
    @TargetApi(16)
    private void initNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, ParentMain.class);

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.icon_tracing);

        // 设置PendingIntent
        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setLargeIcon(icon)  // 设置下拉列表中的图标(大图标)
                .setContentTitle("百度鹰眼") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.icon_tracing) // 设置状态栏内的小图标
                .setContentText("服务正在运行...") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    /**
     * 清除Trace状态：初始化app时，判断上次是正常停止服务还是强制杀死进程，根据trackConf中是否有is_trace_started字段进行判断。
     *
     * 停止服务成功后，会将该字段清除；若未清除，表明为非正常停止服务。
     */
    private void clearTraceStatus() {
        if (trackConf.contains("is_trace_started") || trackConf.contains("is_gather_started")) {
            SharedPreferences.Editor editor = trackConf.edit();
            editor.remove("is_trace_started");
            editor.remove("is_gather_started");
            editor.apply();
        }
    }

    /**
     * 初始化请求公共参数
     *
     * @param request
     */
    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(serviceId);
    }

    /**
     * 获取请求标识
     *
     * @return
     */
    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }
    //鹰眼轨迹---------------------------------------------
}
