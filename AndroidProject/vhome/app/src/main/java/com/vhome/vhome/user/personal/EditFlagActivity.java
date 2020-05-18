package com.vhome.vhome.user.personal;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.domain.EaseUser;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.util.ClearWriteEditText;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;


public class EditFlagActivity extends Activity {
   private Button bt_finish;
   private EditText ed_flag;
   private ImageView back;
   private TextView maxview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flag);
        findId();
        initListener();
       
    }
    /**
     * 初始化id
     */
    private void findId() {
       bt_finish = (Button) findViewById(R.id.nickname_finish);
       ed_flag = (EditText) findViewById(R.id.ed_nickname);
       back = (ImageView) findViewById(R.id.back);
       maxview = (TextView) findViewById(R.id.max_num);
    }

    /**
     * 绑定事件
     */
    private void initListener() {
       bt_finish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               String new_nickname = String.valueOf(ed_flag.getText());
               editor.putString("personalWord",new_nickname);
               editor.commit();
               //更新数据库
               changeFlagname(new_nickname);
               finish();
           }
       });
       SharedPreferences sharedPreferences = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
       String name = sharedPreferences.getString("personalWord","");
       ed_flag.setText(name);
       maxview.setText(ed_flag.getText().length()+"/255");
       ed_flag.addTextChangedListener(new TextWatcher() {
            int current_Length=ed_flag.getText().length();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                current_Length = ed_flag.getText().length();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                maxview.setText(current_Length+"/255");
            }
            @Override
            public void afterTextChanged(Editable s) {
                maxview.setText(current_Length+"/255");
            }
        });
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }
    public void changeFlagname(String new_flag){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("flag","personalWord");
            jsonObject.put("data",new_flag);
            jsonObject.put("type",0);
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
                                Toast.makeText(EditFlagActivity.this,ok,Toast.LENGTH_SHORT);
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
    
}

