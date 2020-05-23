package com.vhome.vhome.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 与服务器连接的工具类
 */
public class ConnectionUtil {
    /**
     * 之前没有连接时，获取数据，并以字符串的形式返回
     *
     * @param url
     * @return
     */
    public String getData(URL url) throws IOException {

        String data = null;
        URLConnection connection = null;
        connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        data = bufferedReader.readLine();
        bufferedReader.close();
        is.close();
        return data;
    }

    /**
     * 当有连接几经建立的时候
     *
     * @param connection
     * @return
     */
    public String getData(HttpURLConnection connection) throws IOException {
        InputStream is = null;
        String data = null;
        is = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        data = bufferedReader.readLine();
        bufferedReader.close();
        is.close();

        return data;
    }

    /**
     * 以HttpUrlConnection 发送字符串数据,返回连接
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    public HttpURLConnection sendData(URL url, String data) throws IOException {
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
        bw.write(data);
        bw.flush();
        bw.close();
        os.close();

        return connection;
    }

    /**
     * msg的 what标志是tag
     *
     * @param data
     * @param tag
     * @param handler
     */
    public void sendMsg(String data, int tag, Handler handler) {
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        Message msg = new Message();
        msg.setData(bundle);
        msg.what = tag;
        handler.sendMessage(msg);
    }
}
