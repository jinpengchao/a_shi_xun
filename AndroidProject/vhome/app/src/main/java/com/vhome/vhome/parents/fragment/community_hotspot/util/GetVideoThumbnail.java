package com.vhome.vhome.parents.fragment.community_hotspot.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;

import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.listener.onGetVideoThumbListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetVideoThumbnail{
    private static final String TAG = "GetVideoThumbnail";
    public GetVideoThumbnail(){

    };
    //获取视频首帧缩略图路径
//    public String getFirstThumbPath(Context context,String name,String url) {
//        String savePath;
//        File filePic;
//        String ThumbPath = null;
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            savePath = "/sdcard/dskgxt/pic/";
//        }else {
//            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/dskgxt/pic/";
//        }
//        filePic = new File(savePath + name+".jpg");
//        if (!filePic.exists()) {
//            Log.d(TAG, "getFirstThumbPath: 本地不存在：");
//            ThumbPath = voidToFirstPath(context,url,name);
//        }else {
//            ThumbPath = filePic.getAbsolutePath();
//        }
//        return ThumbPath;
//    }
    public String getFirstThumbPath(Context context,String name,String url) {
        String savePath;
        File filePic;
        String ThumbPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = "/sdcard/dskgxt/pic/";
        }else {
            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/dskgxt/pic/";
        }
        filePic = new File(savePath + name+".jpg");
        if (!filePic.exists()) {
            Log.d(TAG, "getFirstThumbPath: 本地不存在：");
            Bitmap fbitmap = MyBobAsynctack.getFirstBitmap(url);
            Bitmap lbitmap = toConformBitmap(context,fbitmap);
            ThumbPath = bitmapToStringPath(context,lbitmap,name);
        }else {
            ThumbPath = filePic.getAbsolutePath();
        }
        return ThumbPath;
    }

    public String voidToFirstPath(Context context,String path,String name){
        final String[] ThumbPath = new String[1];
        //异步加载
        MyBobAsynctack myBobAsynctack = new MyBobAsynctack(path);
        myBobAsynctack.execute(path);
        myBobAsynctack.setOnGetVideoThumbListener(new onGetVideoThumbListener() {
            @Override
            public void asyncTaskThumb(Bitmap bitmap) {
                Log.d(TAG, "asyncTaskThumb: 获取第一帧视频图："+bitmap);
                Bitmap conBitmap = toConformBitmap(context,bitmap);
                ThumbPath[0] = bitmapToStringPath(context,conBitmap,name);
            }
        });
        return ThumbPath[0];
    }


    /**
     * 视频缩略图添加播放图标
     * @param context
     * @param background
     * @return
     */
    public Bitmap toConformBitmap(Context context,Bitmap background) {
        Bitmap foreground = BitmapFactory.decodeResource(context.getResources(), R.drawable.ease_video_play_btn_small_nor);
        if( background == null || foreground == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(bgWidth,bgHeight,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bitmap);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, bgWidth/2-foreground.getWidth()/2, bgHeight/2-foreground.getHeight()/2, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save();//保存
        cv.restore();//存储
        Log.d("获取", "toConformBitmap: 合并bitmap"+bitmap);
        return bitmap;
    }
    /**
     * 将bitmap转化成本地图片路径
     * @param context
     * @param bitmap
     * @return
     */
    public static String bitmapToStringPath(Context context, Bitmap bitmap,String name){
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = "/sdcard/dskgxt/pic/";
        }else {
            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/dskgxt/pic/";
        }
        try {
            filePic = new File(savePath + name+".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            Log.d(TAG, "bitmapToStringPath: 获取保存之前的bitmap"+bitmap+"  ///路径::"+filePic.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }
}
