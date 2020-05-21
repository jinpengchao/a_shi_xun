package com.vhome.vhome.parents.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.myself.ShowMyselfActivity;
import com.vhome.vhome.user.personal.MySelfActivity;
import com.vhome.vhome.user.personal.OthersSerlfActivity;
import com.vhome.vhome.user.personal.widget.CircleImageView;

public class AdminMessageAdapter extends BaseAdapter {
    private List<PostBean> list;
    private int itemLayoutId;
    private Context context;
    private ArrayList<String> imgsList;

    public AdminMessageAdapter(Context context, List<PostBean> list, int itemLayoutId) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == view) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(itemLayoutId, null);
            holder = new ViewHolder();
            holder.ivHotPerson = view.findViewById(R.id.iv_hot_person);
            holder.tvHotName = view.findViewById(R.id.tv_hot_name);
            holder.tvHotContent = view.findViewById(R.id.tv_hot_content);
            holder.unreadPoint = view.findViewById(R.id.unread_msg_number);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvHotName.setText(list.get(i).getNickName());
        holder.tvHotContent.setText(list.get(i).getPostContent());
        String time = list.get(i).getTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;

    }

    private class ViewHolder {
        CircleImageView ivHotPerson;
        TextView tvHotName;
        TextView tvHotContent;
        TextView unreadPoint;
    }
}
