package com.vhome.vhome.parents.fragment.community_hotspot.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

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
     * 获取视频时长
     */
    public static int getVideoDuration(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        String strDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int duration = Integer.parseInt(strDuration) / 1000;
        return duration;
    }
    /**
     * 将bitmap转化成本地图片路径
     * @param context
     * @param bitmap
     * @return
     */
    public static String bitmapToStringPath(Context context, Bitmap bitmap){
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = "/sdcard/dskgxt/pic/";
        }else {
            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/dskgxt/pic/";
        }
        try {
            filePic = new File(savePath + UUID.randomUUID().toString() + ".jpg");
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
