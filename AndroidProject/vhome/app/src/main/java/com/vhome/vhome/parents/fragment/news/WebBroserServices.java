package com.vhome.vhome.parents.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vhome.chat.R;

import java.util.ArrayList;
import java.util.List;

public class WebBroserServices extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_services);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView=findViewById(R.id.web_view);//绑定ID
//        webView.setWebViewClient(new WebViewClient());//添加WebViewClient实例
        webView.loadUrl(url);//添加浏览器地址
    }

}
