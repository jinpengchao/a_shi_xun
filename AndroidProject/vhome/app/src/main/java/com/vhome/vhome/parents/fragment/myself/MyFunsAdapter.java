package com.vhome.vhome.parents.fragment.myself;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.user.entity.ParentUserInfo;

public class MyFunsAdapter extends BaseAdapter {
    private final static String TAG = "MyAttentionAdapter";
    private Context context;
    private List<ParentUserInfo> list;
    private int itemLayoutId;

    public MyFunsAdapter(Context context, List<ParentUserInfo> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyAttentionAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(itemLayoutId, viewGroup, false);
            viewHolder = new MyAttentionAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyAttentionAdapter.ViewHolder) view.getTag();
        }
        //设置头像
        String user_logo = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+list.get(i).getHeaderImg()+".jpg";;
        Glide.with(context).load(user_logo).priority(Priority.HIGH)
                .error(R.mipmap.errorimg1)
                .into(viewHolder.ivPerson);
        //设置昵称
        viewHolder.tvName.setText(list.get(i).getNikeName());
        //设置id
        viewHolder.tvId.setText(list.get(i).getId());
        //点击删除
        return view;
    }

    public static class ViewHolder {
        public ImageView ivPerson;
        public TextView tvName;
        public TextView tvId;
        public View root;

        public ViewHolder(View root) {
            ivPerson = (ImageView) root.findViewById(R.id.iv_hot_person);
            tvName = root.findViewById(R.id.tv_hot_name);
            tvId = root.findViewById(R.id.tv_id);
            this.root = root;
        }
    }

}
