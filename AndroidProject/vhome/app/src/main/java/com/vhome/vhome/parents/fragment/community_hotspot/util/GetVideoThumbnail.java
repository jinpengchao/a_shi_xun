package com.vhome.vhome.parents.fragment.community_hotspot.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.vhome.chat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class GetVideoThumbnail {
    public GetVideoThumbnail() {
        //no instance
    }
    /**
     * 获取视频首帧图并转化为bitmap
     * @param url
     * @return
     */
    public Bitmap voidToFirstBitmap(String url){

        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,96,96, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * 视频缩略图添加播放图标
     * @param context
     * @param background
     * @return
     */
    public Bitmap toConformBitmap(Context context,Bitmap background) {
        Bitmap foreground = BitmapFactory.decodeResource(context.getResources(), R.drawable.ease_video_play_btn_small_nor);
//        Resources r = context.getResources();
//        Bitmap foreground=BitmapFactory.decodeResource(r, R.drawable.accept);
//        Bitmap foreground = bmp.createBitmap(300, 300, Bitmap.Config.ARGB_8888 );
        if( background == null || foreground == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
//        create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
//        Bitmap bitmap = background.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap bitmap = Bitmap.createBitmap(bgWidth,bgHeight,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bitmap);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, bgWidth/2-foreground.getWidth()/2, bgHeight/2-foreground.getHeight()/2, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save();//保存
        cv.restore();//存储
        Log.d("获取", "toConformBitmap: 合并bitmap");
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
