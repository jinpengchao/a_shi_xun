package com.vhome.vhome.user.personal;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vhome.chat.R;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ViewGroup mContainer = (ViewGroup) findViewById(R.id.container);
        List<String> mActivitys = createList();

        for (final String string : mActivitys) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
            textView.setGravity(Gravity.CENTER);
            textView.setText(string);
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(StartActivity.this, Class.forName(string)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            mContainer.addView(textView);
        }
    }

    private List<String> createList() {
        List<String> activitys = new ArrayList<>();
        activitys.add("com.vhome.vhome.user.personal.Demo1Activity");
        activitys.add("com.vhome.vhome.user.personal.Demo2Activity");
        return activitys;
    }


}
