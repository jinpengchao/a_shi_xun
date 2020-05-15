package com.vhome.vhome.user.personal;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class ImageShowerActivity extends Activity {
    private ImageView bigImg;
    private TextView download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shower);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String phone = bundle.getString("phone");

        bigImg = (ImageView) findViewById(R.id.big_img);
        download = (TextView) findViewById(R.id.download_header);

        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
        Glide.with(this)
                .load(url)
                .priority(Priority.HIGH)
                .into(bigImg);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImageShowerActivity.this,"保存成功",Toast.LENGTH_LONG);
                try {
                    setPicToView(phone,returnBitMap(url));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
        String path = "/sdcard/vhome/header"+username+"/";
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

}
