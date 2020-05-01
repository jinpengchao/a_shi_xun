package com.hyphenate.easeui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.signature.StringSignature;
import com.hyphenate.easeui.MyApp;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView) throws IOException {
    	EaseUser user = getUserInfo(username);
        if(user != null){
            String path = "/sdcard/header"+username+"/";// sd路径
            Bitmap bt = BitmapFactory.decodeFile(path + "header"+username+".jpg");// 从SD卡中找头像，转换成Bitmap
            if (bt != null) {
                @SuppressWarnings("deprecation")
                Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
                imageView.setImageDrawable(drawable);
            } else {
                String url = "http://"+(new MyApp()).ip+":8080/vhome/images/"+"header"+username+".jpg";
                Glide.with(context)
                        .load(url)
                        .priority(Priority.HIGH)
                        .signature(new StringSignature(UUID.randomUUID().toString()))
                        .into(imageView);

                setPicToView(username,returnBitMap(url));
            }
        }else{
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

    public static Bitmap returnBitMap(String url) throws IOException {
        URL imgUrl = new URL(url);
        Bitmap bitmap = null;
        final HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        return bitmap;
    }
    private static void setPicToView(String username, Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        String path = "/sdcard/header"+username+"/";
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "header"+username+".jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * set user's nickname
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNickname() != null){
        		textView.setText("用户名不着急"+user.getNickname());
        	}else{
        		textView.setText("马上解决了你"+username);
        	}
        }
    }
    
}
