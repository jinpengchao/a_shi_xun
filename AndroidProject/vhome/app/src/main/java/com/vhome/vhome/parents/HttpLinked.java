package com.vhome.vhome.parents;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpLinked {
    private static HttpURLConnection con;
    public static HttpURLConnection connection(String urlpath,String str)  {

        try {
            URL url = new URL(urlpath+str);
            con = (HttpURLConnection) url.openConnection();
            // 传送 json 数据一般是用 POST
            // 发送 POST 请求必须设置
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);
        } catch (IOException e) {
            Log.e("错误","连接错误");
        }
        return con;
    }

    public static HttpURLConnection connection(String urlpath)  {

        try {
            URL url = new URL(urlpath);
            con = (HttpURLConnection) url.openConnection();
            // 传送 json 数据一般是用 POST
            // 发送 POST 请求必须设置
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);
        } catch (IOException e) {
            Log.e("错误","连接错误");
        }
        return con;
    }
}
