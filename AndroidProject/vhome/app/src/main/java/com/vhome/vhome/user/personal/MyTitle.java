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
import com.vhome.vhome.user.personal.util.MyProgressbar;
import com.vhome.vhome.util.ClearWriteEditText;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;


public class MyTitle extends Activity {
    private ImageView back;
    private MyProgressbar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_title);
        back = findViewById(R.id.back);
        progressbar = findViewById(R.id.my_progress);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取当前步数，获取当前等级，计算百分比
        progressbar.setProgress(76, 100);
    }
}

