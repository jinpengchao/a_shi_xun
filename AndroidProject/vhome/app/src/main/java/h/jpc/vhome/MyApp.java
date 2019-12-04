package h.jpc.vhome;

import android.app.Application;

public class MyApp extends Application {
    //10.7.89.13
    //10.7.89.128
    private String ip = "10.7.89.128";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
