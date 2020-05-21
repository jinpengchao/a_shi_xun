package com.vhome.vhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.vhome.vhome.lead.ViewPageActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    private void initData() {

        goToMainActivity();
    }

    private void goToMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
//        startActivity(new Intent(mContext, ViewPageActivity.class));
        finish();
    }
}
