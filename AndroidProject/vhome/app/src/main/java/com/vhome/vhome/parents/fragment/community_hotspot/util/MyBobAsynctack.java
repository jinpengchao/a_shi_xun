package com.vhome.vhome.parents.fragment.community_hotspot.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.vhome.vhome.parents.fragment.community_hotspot.listener.onGetVideoThumbListener;

import java.util.HashMap;

public class MyBobAsynctack extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "MyBobAsynctack";
    private String path;
    private onGetVideoThumbListener listener;
    public void setOnGetVideoThumbListener(onGetVideoThumbListener listener){
        this.listener = listener;
    }

    public MyBobAsynctack(String path) {
        this.path = path;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        //这里的创建缩略图方法是调用VideoUtil类的方法，也是通过 android中提供的 ThumbnailUtils.createVideoThumbnail(vidioPath, kind);
        Log.d(TAG, "doInBackground: 运行获取第一帧的方法、、、、、");
        Bitmap bitmap = getFirstBitmap(path);

        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d(TAG, "onPostExecute: 获取bitmap");
        listener.asyncTaskThumb(bitmap);
        super.onPostExecute(bitmap);
    }

    /**
     * 获取视频首帧图并转化为bitmap
     * @param url
     * @return
     */
    public static Bitmap getFirstBitmap(String url){
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
            Log.d(TAG, "getFirstBitmap: 获取视频bitmap：前："+bitmap);
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
        Log.d(TAG, "getFirstBitmap: 获取视频bitmap：："+bitmap);
        return bitmap;
    }
}
