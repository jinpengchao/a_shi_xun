package h.jpc.vhome.parents.fragment.radio_ximalaya.utils;

import android.util.Log;

public class LogUtil {
    public static String sTAG="LogUtil";

    //控制是否要输出
    public static boolean sIsRelease=false;

    public static void init(String baseTag,boolean isRelease){
        sTAG=baseTag;
        sIsRelease=isRelease;
    }
    public static void d(String TAG,String content){
        if(!sIsRelease){
            Log.d("["+sTAG+"]"+TAG,content);
        }
    }
    public static void v(String TAG,String content){
        if(!sIsRelease){
            Log.d("["+sTAG+"]",content);
        }
    }
    public static void i(String TAG,String content){
        if(!sIsRelease){
            Log.d("["+sTAG+"]",content);
        }
    }
    public static void w(String TAG,String content){
        if(!sIsRelease){
            Log.d("["+sTAG+"]",content);
        }
    }
    public static void e(String TAG,String content){
        if(!sIsRelease){
            Log.d("["+sTAG+"]",content);
        }
    }
}
