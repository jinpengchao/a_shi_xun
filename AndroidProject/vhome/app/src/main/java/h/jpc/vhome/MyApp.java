package h.jpc.vhome;

import android.app.Application;

import org.xutils.x;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }

    //10.7.89.13
    //10.7.89.128
    private String ip = "10.7.82.34";
    //本地用户信息的地址
    private String pathInfo = "parentUserInfo";

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
}
