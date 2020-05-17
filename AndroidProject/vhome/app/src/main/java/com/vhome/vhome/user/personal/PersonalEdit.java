package com.vhome.vhome.user.personal;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.user.personal.util.DialogChangesex;
import com.vhome.vhome.user.personal.util.Dialogchoosephoto;
import com.vhome.vhome.user.personal.util.JsonUtil;
import com.vhome.vhome.user.personal.util.Utils;
import com.vhome.vhome.user.personal.widget.CircleImageView;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import fule.com.mydatapicker.DatePickerDialog;
import fule.com.mydatapicker.DateUtil;
import fule.com.mywheelview.bean.AddressDetailsEntity;
import fule.com.mywheelview.bean.AddressModel;
import fule.com.mywheelview.weight.wheel.ChooseAddressWheel;
import fule.com.mywheelview.weight.wheel.OnAddressChangeListener;

public class PersonalEdit extends Activity {
    public static String path;
    public static String phone;
    public static String fileName;
    private Bitmap head;// 头像Bitmap
    private CircleImageView header;
    private TextView iD;
    private TextView nickName;
    private TextView sign;
    private ImageView sexIcon;
    private TextView sex;
    private TextView birth;
    private TextView area;
    private ImageView back;

    private ChooseAddressWheel chooseAddressWheel;
    private String address;
    private Dialog dateDialog;
    private RelativeLayout rl_nickName;
    private RelativeLayout rl_signal;
    private RelativeLayout rl_gender;
    private RelativeLayout rl_birth;
    private RelativeLayout rl_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_myself);
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        phone = sharedPreferences.getString("phone","");
        path = "/sdcard/header"+phone+"/";// sd路径
        findId();
        initWheel();
        initData();
        try {
            initInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            initInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this);
        chooseAddressWheel.setOnAddressChangeListener(new OnAddressChangeListener() {
            @Override
            public void onAddressChange(String province, String city, String district) {
                address = (province + "，" + city + "，" + district);
                area.setText(address);
                SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("area",address);
                editor.commit();
                changeInfo(address,"area",0);
                onResume();
                Toast.makeText(PersonalEdit.this,"地区修改成功~",Toast.LENGTH_LONG);
            }
        });

    }
    private void initData() {
        String address = Utils.readAssert(this, "address.txt");
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            AddressDetailsEntity data = model.Result;
            if (data == null) return;
            if (data.ProvinceItems != null && data.ProvinceItems.Province != null) {
                chooseAddressWheel.setProvince(data.ProvinceItems.Province);
                chooseAddressWheel.defaultValue(data.Province, data.City, data.Area);
            }
        }
    }
    /**
     * 初始化id
     */
    private void findId() {
        header = (CircleImageView) findViewById(R.id.head_icon);
        iD = (TextView) findViewById(R.id.vhome_id);
        nickName = (TextView) findViewById(R.id.nick_nam);
        sign = (TextView) findViewById(R.id.signature);
        sexIcon = (ImageView) findViewById(R.id.sex_icon);
        sex = (TextView) findViewById(R.id.sexs);
        birth = (TextView) findViewById(R.id.birthday);
        area = (TextView) findViewById(R.id.city_name);
        back = (ImageView) findViewById(R.id.back);
        rl_nickName = (RelativeLayout) findViewById(R.id.nickname);
        rl_signal = (RelativeLayout) findViewById(R.id.signal);
        rl_gender = (RelativeLayout) findViewById(R.id.gender);
        rl_birth = (RelativeLayout) findViewById(R.id.birth);
        rl_city = (RelativeLayout) findViewById(R.id.city);

    }

    private void initInfo() throws IOException {
        SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        //头像
//        //刷新本地头像
//        String url1 = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
//        setPicToView(returnBitMap(url1));

        Bitmap bt = BitmapFactory.decodeFile(path + "header" + phone + ".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            header.setImageDrawable(drawable);
        } else {
            String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+"header"+phone+".jpg";
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.rc_default_portrait)
                    .priority(Priority.HIGH)
                    .into(header);
            setPicToView(returnBitMap(url));
        }
        //Id
        String id = sharedPreferences.getString("id","undefined");
        iD.setText("微家ID:"+id);
        //昵称
        String name = sharedPreferences.getString("nickName","");
        nickName.setText(name);
        //签名
        String signature = sharedPreferences.getString("personalWord","");
        sign.setText(signature);
        //性别
        String gender = sharedPreferences.getString("sex","undefined");
        if(gender.equals("male")){
            sexIcon.setImageResource(R.drawable.man);
            sex.setText("男");
        }else if(gender.equals("female")){
            sexIcon.setImageResource(R.drawable.woman);
            sex.setText("女");
        }else {
            sexIcon.setImageResource(R.drawable.unknowsex);
            sex.setText("保密");
        }
        //生日
        String birthday = sharedPreferences.getString("birthday","");
        birth.setText(birthday);
        //地区
        String areas = sharedPreferences.getString("area","");
        area.setText(areas);
    }
    /**
     * 绑定事件
     */

    private void initListener() {
        rl_signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonalEdit.this, EditFlagActivity.class);
                startActivity(intent);
            }
        });
        rl_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
                String b = sharedPreferences.getString("birthday","");
                showDateDialog(DateUtil.getDateForString(b));
            }
        });
        rl_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAddressWheel.show(v);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonalEdit.this, EditNickNameActivity.class);
                startActivity(intent);
            }
        });

        rl_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogChangesex(PersonalEdit.this){

                    @Override
                    public void changeMan() {
                        SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String sex = "male";
                        editor.putString("sex",sex);
                        editor.commit();
                        changeInfo("male","sex",0);
                        onResume();
                        Toast.makeText(PersonalEdit.this,"修改成功~",Toast.LENGTH_LONG);
                    }

                    @Override
                    public void changeWoman() {
                        SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String sex = "female";
                        editor.putString("sex",sex);
                        editor.commit();
                        changeInfo("female","sex",0);
                        onResume();
                        Toast.makeText(PersonalEdit.this,"修改成功~",Toast.LENGTH_LONG);
                    }

                    @Override
                    public void useCancel() {
                        onResume();
                    }
                }.show();
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialogchoosephoto(PersonalEdit.this){
                    @Override
                    public void useCamera() {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "header"+phone+".jpg")));
                        startActivityForResult(intent2, 2);// 采用ForResult打开
                    }
                    @Override
                    public void useGalley() {
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent1, 1);
                    }

                    @Override
                    public void useCancel() {
                    }
                }.show();
           }
       });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/header"+phone+".jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        new Thread(){
                            @Override
                            public void run() {
                                uploadHeaderImg();
                            }
                        }.start();
                        saveHeaderImg();
                        setPicToView(head);// 保存在SD卡中
                        header.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
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
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        if (file.exists()){
            file.delete();
            file.mkdirs();
        }else
            file.mkdirs();// 创建文件夹
        String fileName = path + "header"+phone+".jpg";// 图片名字
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
    public void uploadHeaderImg(){
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/uploadHeader";
        String fileName = path + "header"+phone+".jpg";// 图片名字
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("msg","上传图片");
        params.addBodyParameter("imgs",new File(fileName));
        params.setMultipart(true);
        x.Ext.init(getApplication());
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(PersonalEdit.this, "头像上传成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(PersonalEdit.this, "头像上传失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
    public void saveHeaderImg(){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        int type = sp.getInt("type",0);
        String[] file = path.split("/");
        fileName = file[file.length-1];
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("type",type);
            jsonObject.put("fileName",fileName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String dataTo = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/saveheaderImg");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,dataTo);
                    //获取数据
                    final String dataFrom = util.getData(connection);
                    if(null!=dataFrom){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("headImg",dataFrom);
                                editor.commit();
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
    public void changeInfo(String sex,String flag,int type){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("flag",flag);
            jsonObject.put("data",sex);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/changeInfo");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String ok = data;
                                Log.e("修改成功",ok);
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
    /**
     * 显示日期
     * @param date
     */
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String newBirth = String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                birth.setText(newBirth);
                editor.putString("birthday",newBirth);
                editor.commit();
                changeInfo(newBirth,"birthday",0);
                onResume();
                Toast.makeText(PersonalEdit.this,"生日修改成功~",Toast.LENGTH_LONG);
            }

            @Override
            public void onCancel() {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

}

