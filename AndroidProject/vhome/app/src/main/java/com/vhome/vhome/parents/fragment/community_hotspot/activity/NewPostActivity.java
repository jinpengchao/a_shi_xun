package com.vhome.vhome.parents.fragment.community_hotspot.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.adapter.AddPostImgAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.util.CompressImg;
import com.vhome.vhome.user.personal.util.Dialogchoosephoto;
import com.vhome.vhome.util.ConnectionUtil;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPostActivity extends Activity {

    private Button btnPostPublish;
    private EditText edtPostPublish;
    private MyClickListener listener;
    private TextView tvPostCancel;
    private GridView gvNewPost;
    private AddPostImgAdapter adapter;
    private List<Map<String,Object>> datas;
    private List<String> imgsName;
    private Dialog dialog;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private int addPostResult = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        getViews();
        registerListener();

        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        datas = new ArrayList<>();
        adapter = new AddPostImgAdapter(datas,this);
        gvNewPost.setAdapter(adapter);
        adapter.setOnMyDelClick(new AddPostImgAdapter.onMyDelClick() {
            @Override
            public void myDelClick(int position) {
                datas.remove(position);
                imgsName.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        gvNewPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new Dialogchoosephoto(NewPostActivity.this){
                    @Override
                    public void useCamera() {
                        camera();
                    }
                    @Override
                    public void useGalley() {
                        gallery();
                    }
                    @Override
                    public void useCancel() {
                    }

                }.show();
            }
        });
        imgsName = new ArrayList<>();

    }
    /**
     * 拍照
     */
    public void camera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            File dir = new File(IMAGE_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            tempFile = new File(dir,
                    System.currentTimeMillis() + "_" + PHOTO_FILE_NAME);
            //从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            Intent intent = new Intent();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(intent.CATEGORY_DEFAULT);
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        } else {
            Toast.makeText(this, "未找到存储卡，无法拍照！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 从相册获取2
     */
    public void gallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    Log.i("path","相册照片的路径是"+path);
                    compressImage(path);
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                if (resultCode != RESULT_CANCELED) {
                    // 从相机返回的数据
                    if (hasSdcard()) {
                        if (tempFile != null) {
//                            photoPath(tempFile.getPath());
                            compressImage(tempFile.getPath());
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                        }

                        Log.i("images", "拿到照片path=" + tempFile.getPath());
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };

    /**
     * 压缩图片
     *
     * @param path
     */
    private void compressImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                File oldf = new File(path);
                if (oldf.exists()) {
                    Log.d("images", "源文件存在" + path);
                } else {
                    Log.e("images", "源文件不存在" + path);
                }

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                    Log.i("wzw","创建文件成功");
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
                Log.i("wzw","file地址"+file.getAbsolutePath());
                CompressImg compressImg = new CompressImg();
                compressImg.bitmapCompress(oldf,file.getAbsolutePath(),1024,768);
//                MCompressor.from()
//                        .fromFilePath(path).toFilePath(file.getAbsolutePath().toString()).compress();
                if (file.exists()) {
                    Log.d("images", "压缩后的文件存在" + file.getAbsolutePath());
                    //添加文件的名字到list数组
                    imgsName.add(file.getName());
                } else {
                    Log.e("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);

            }
        }.start();

    }

    public void photoPath(String path) {
        Map<String,Object> map=new HashMap<>();
        map.put("path",path);
        datas.add(map);
        adapter.notifyDataSetChanged();
    }

    private void getViews() {
        edtPostPublish = findViewById(R.id.edt_post_publish);
        btnPostPublish = findViewById(R.id.btn_post_publish);
        tvPostCancel = findViewById(R.id.tv_post_cancel);
        gvNewPost = findViewById(R.id.gv_post_new);
    }

    private void registerListener() {
        listener = new MyClickListener();
        btnPostPublish.setOnClickListener(listener);
        tvPostCancel.setOnClickListener(listener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_post_publish:
                    //保存自己发表的帖子到数据库，并显示
                    if (datas.size()<1){
                        int i = saveMyPost();
                        if (i==1){
                            finish();
                        }
                    }else {
                        saveMyPost();
                        upLoadImg();
                    }
                    break;
                case R.id.tv_post_cancel:
                    finish();
                    break;
            }
        }
    }

    /**
     * 保存帖子
     */
    private int saveMyPost() {
        //准备数据
        //获取用户信息
        String infoPath = (new MyApp()).getPathInfo();
        SharedPreferences sp = getSharedPreferences(infoPath,MODE_PRIVATE);
        //获取输入的帖子的内容
        String postContent = edtPostPublish.getText().toString().trim();
        if (null==postContent||"".equals(postContent)){
            Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
            return 0;
        }else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);

            PostBean p = new PostBean();
            p.setNickName(sp.getString("nickName",""));
            p.setHeadimg(sp.getString("headImg",""));
            p.setPersonId(sp.getString("id",""));
            p.setPostContent(postContent);
            p.setTime(time);
            p.setExamine("待审核");
            Gson gson = new Gson();
            String imgs = gson.toJson(imgsName);
            p.setImgs(imgs);
            final String data = gson.toJson(p);

            new Thread(){
                @Override
                public void run() {
                    String ip = (new MyApp()).getIp();
                    try {
                        URL url = new URL("http://"+ip+":8080/vhome/SavePostServlet");
                        ConnectionUtil util = new ConnectionUtil();
                        //发送数据
                        HttpURLConnection connection = util.sendData(url,data);
                        //获取数据
                        String data = util.getData(connection);

                        if(null!=data){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplication(),"保存成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        return 1;
    }

    private void upLoadImg() {
        String postContent = edtPostPublish.getText().toString().trim();
        if (null==postContent||"".equals(postContent)){
            Toast.makeText(NewPostActivity.this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
        }else {
            String url = "http://"+(new MyApp()).getIp()+":8080/vhome/PostImgServlet";
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("msg","上传图片");
            for (int i=0;i<datas.size();i++){
                params.addBodyParameter("imgs",new File(datas.get(i).get("path").toString()));
            }
            x.Ext.init(getApplication());
            params.setMultipart(true);
            x.http().post(params, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("upLoadImg","图片上传成功！");
                    for (int i=0;i<datas.size();i++){
                        File file = new File(datas.get(i).get("path").toString());
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("upLoadImg","图片上传失败！");
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }
                @Override
                public void onFinished() {

                    setResult(addPostResult);
                    finish();
                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }

//        setResult(addPostResult);
//        finish();
    }
}
