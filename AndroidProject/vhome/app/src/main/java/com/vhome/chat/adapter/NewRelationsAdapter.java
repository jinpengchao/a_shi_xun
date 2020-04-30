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
package com.vhome.chat.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.vhome.chat.R;
import com.vhome.chat.domain.Relations;
import com.vhome.chat.domain.SendPerson;
import com.vhome.chat.ui.NewRelationsActivity;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.util.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class NewRelationsAdapter extends BaseAdapter {

    private Context context;
    private List<SendPerson> list;
    public NewRelationsAdapter(Context context, List<SendPerson> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.em_row_invite_msg, null);
            holder.avator = (ImageView) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.agreeBtn = (Button) convertView.findViewById(R.id.agree);
            holder.refuseBtn = (Button) convertView.findViewById(R.id.refuse);
            // holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //做一个判断 为0运行这两个，其他不运行
        holder.agreeBtn.setVisibility(View.GONE);
        holder.refuseBtn.setVisibility(View.GONE);
        // set click listener
        holder.agreeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // accept invitation
                addRelations(position);
                //TODO：更新数据库tbl_reuest_relation的type为1
                holder.agreeBtn.setVisibility(View.GONE);
                holder.refuseBtn.setVisibility(View.GONE);
                holder.message.setText(context.getResources().getString(R.string.Has_agreed_to));
            }
        });
        holder.refuseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO：更新数据库tbl_reuest_relation的type为-1
                holder.agreeBtn.setVisibility(View.GONE);
                holder.refuseBtn.setVisibility(View.GONE);
                holder.message.setText(context.getResources().getString(R.string.Has_refused_to));
            }
        });
        String user_logo = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+ "header"+ NewRelationsActivity.msgs.get(position).getSendPhone()+".jpg";
        Glide.with(context)
                .load(user_logo)
                .priority(Priority.HIGH)
                .error(R.mipmap.errorimg1)
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .into(holder.avator);
        holder.name.setText(NewRelationsActivity.msgs.get(position).getSendName());
        return convertView;
    }
    public void addRelations(int position) {
        //准备数据
        SharedPreferences sp1 = context.getSharedPreferences("parentUserInfo",Context.MODE_PRIVATE);
        SharedPreferences sp2 = context.getSharedPreferences("childUserInfo",Context.MODE_PRIVATE);
        String receivePhone = sp1.getString("phone","indefindParent");
        String receiveName = "";
        String sendPhone = "";
        String sendName ="";
        if(receivePhone.equals("indefindParent")){
            receivePhone = sp2.getString("phone","indefindChild");
            receiveName = sp2.getString("nickName","");
            sendPhone = list.get(position).getSendPhone();
            sendName = list.get(position).getSendName();
        }else{
            receiveName = sp1.getString("nickName","");
            sendPhone = list.get(position).getSendPhone();
            sendName = list.get(position).getSendName();
        }
        Relations relations = new Relations();
        relations.setReceivePhone(receivePhone);
        relations.setReceiveName(receiveName);
        relations.setSendPhone(sendPhone);
        relations.setSendName(sendName);
        Gson gson = new Gson();
        final String data = gson.toJson(relations);
        Log.e("datass",data);
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/addNewRelation");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    final String data = util.getData(connection);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private static class ViewHolder {
        ImageView avator;
        TextView name;
        TextView message;
        Button agreeBtn;
        Button refuseBtn;
    }

}
