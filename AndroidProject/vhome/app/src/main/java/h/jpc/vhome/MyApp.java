package h.jpc.vhome;

import android.app.Application;

public class MyApp extends Application {
    private String ip = "192.168.2.108";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
