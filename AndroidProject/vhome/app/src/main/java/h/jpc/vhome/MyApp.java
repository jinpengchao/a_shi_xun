package h.jpc.vhome;

import android.app.Application;

public class MyApp extends Application {
    private String ip = "10.7.89.1";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
