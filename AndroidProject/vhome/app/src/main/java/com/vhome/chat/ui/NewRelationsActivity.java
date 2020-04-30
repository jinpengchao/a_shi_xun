/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vhome.chat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vhome.chat.R;
import com.vhome.chat.adapter.NewRelationsAdapter;
import com.vhome.chat.domain.SendPerson;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Application and notification
 *
 */
public class NewRelationsActivity extends BaseActivity {
    public static List<SendPerson> msgs  = new ArrayList<>();;
    private Handler handler;
    private NewRelationsAdapter adapter ;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_relations);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String data = b.getString("data");
                Gson gson = new Gson();
                msgs = gson.fromJson(data,new TypeToken<List<SendPerson>>(){}.getType());
                listView = (ListView) findViewById(R.id.list);
                adapter = new NewRelationsAdapter(getBaseContext(), msgs);
                listView.setAdapter(adapter);
            }
        };
        getList();
    }

    public void back(View view) {
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("hotspot","调用了onResume方法");
        getList();

    }
    public void getList() {
        //准备数据
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String receivePhone = sharedPreferences.getString("phone","");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receivePhone",receivePhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/ReceiveResponseOfRelations");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                util.sendMsg(data,1,handler);
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
