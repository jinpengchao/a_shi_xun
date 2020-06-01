package com.vhome.vhome.parents;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpLogin {

    public static String JasonAccpt1() {
        String address = "http://v.juhe.cn/toutiao/index?type=top&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt3() {
        String address = "http://v.juhe.cn/toutiao/index?type=shehui&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt2(String city) {
        String address = "http://v.juhe.cn/weather/index?format=2&cityname="+city+"&key=f6ae80bdd9b1fdf590c7bf54bb487244";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String JasonAccpt4() {
        String address = "http://v.juhe.cn/toutiao/index?type=tiyu&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt5() {
        String address = "http://v.juhe.cn/toutiao/index?type=yule&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt6() {
        String address = "http://v.juhe.cn/toutiao/index?type=guonei&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt7() {
        String address = "http://v.juhe.cn/toutiao/index?type=guoji&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt8() {
        String address = "http://v.juhe.cn/toutiao/index?type=junshi&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt9() {
        String address = "http://v.juhe.cn/toutiao/index?type=keji&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt10() {
        String address = "http://v.juhe.cn/toutiao/index?type=caijing&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String JasonAccpt11() {
        String address = "http://v.juhe.cn/toutiao/index?type=shishang&key=87b6d78117d053216d5ed84f04da2ba1";
        String result = "";
        try {
            URL url=new URL(address);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();
                result=new String(message.toByteArray());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
