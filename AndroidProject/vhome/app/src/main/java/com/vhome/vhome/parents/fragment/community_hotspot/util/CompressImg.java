package com.vhome.vhome.parents.fragment.community_hotspot.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CompressImg {

    /**
     * 可能会有点耗时，可以在子线程调用
     *
     * @param srcFile 要压缩的图片文件
     * @param path    压缩后的图片文件路径
     * @return File 压缩成功后的图片文件
     */
    public File bitmapCompress(File srcFile, String path, int tagWidth, int tagHeight) {

        if (srcFile == null || !srcFile.exists()) {
            throw new RuntimeException("图片文件不存在");
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        } else {
            if (path.contains(".")) {
                path = path.substring(0, path.lastIndexOf("."));
                path = path + ".jpg";//jpg格式
            } else {
                path = path + ".jpg";//jpg格式
            }
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置为true，不会申请内存，可以得到原生的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
        //旋转操作
        // 获取图片旋转角度，旋转图片
        int degree = getRotateDegree(srcFile.getAbsolutePath());
        int outWidth = options.outWidth;//原生的宽
        int outHeight = options.outHeight;//原生的高
        /**
         * 图片大小（分辨率）压缩
         * options.inSampleSize  这是压缩比率，实际压缩比率根据自己需求通过算法计算
         */
        options.inSampleSize = getSampleSize(outWidth, outHeight, tagWidth, tagHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap2 = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
        /**
         * 图片旋转
         */
//        int degree = getRotateDegree(srcFile.getAbsolutePath());
        bitmap2 = rotateImage(bitmap2,degree);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        /**
         * 这里是图片质量压缩，第二个参数表示压缩率，100表示不压缩，0表示最大压缩
         */
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        bitmap2.recycle();

        FileOutputStream outputStream = null;
        File tagFile = new File(path);
        try {
            if (!tagFile.exists()) {
                tagFile.createNewFile();
            }
            outputStream = new FileOutputStream(tagFile);
            outputStream.write(stream.toByteArray());
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tagFile;
    }

    /**
     * 压缩比率 每次减少0.5倍
     * @param srcWidth 原生的宽
     * @param srcHeight 原生的高
     * @param dstWidth 目标宽
     * @param dstHeight 目标高
     * @return
     */
    public int getSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {

        int widthSize = 0;
        int heightSize = 0;

        while (srcWidth > dstWidth) {
            widthSize += 2;
            srcWidth = srcWidth / 2;
        }

        while (srcHeight > dstHeight) {
            heightSize += 2;
            srcHeight = (srcHeight / 2);
        }

        if (widthSize > heightSize) {
            return widthSize;
        } else {
            return heightSize;
        }
    }

    /**
     * 旋转图片
     * @param bitmap
     * @param degree
     * @return
     */
    public Bitmap rotateImage(Bitmap bitmap, float degree) {
        //create new matrix
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }

    /**
     * 获取图片的旋转角度。
     * 只能通过原始文件获取，如果已经进行过bitmap操作无法获取。
     */
    private static int getRotateDegree(String path) {
        int result = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    result = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    result = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    result = 270;
                    break;
            }
        } catch (IOException ignore) {
            return 0;
        }
        return result;
    }
}
