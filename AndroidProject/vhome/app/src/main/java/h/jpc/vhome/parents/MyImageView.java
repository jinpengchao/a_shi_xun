package h.jpc.vhome.parents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*

 * 將Stream流解碼爲圖片的自定義視圖

 */

public class MyImageView extends ImageView {



    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            byte[]result=(byte[])msg.obj;
            Bitmap bitmap=BitmapFactory.decodeByteArray(result,0,result.length);
            MyImageView.this.setImageBitmap(bitmap);

        };

    };



    public MyImageView(Context context) {

        super(context);

    }



    public MyImageView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    public void setImageUrl(final String urlString) {

        new Thread(new Runnable() {



            @Override

            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                final Request request=new Request.Builder().url(urlString).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message message= mHandler.obtainMessage();
                        message.what=1;
                        message.obj=response.body().bytes();
                        mHandler.sendMessage(message);

                    }
                });

            }

        }).start();



    }



}
