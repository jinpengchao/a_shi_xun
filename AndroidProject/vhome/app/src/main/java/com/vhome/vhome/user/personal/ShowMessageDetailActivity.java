package com.vhome.vhome.user.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.vhome.chat.R;

public class ShowMessageDetailActivity extends Activity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_message_detial);
        textView = findViewById(R.id.content);
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        textView.setText(content);
    }
}
