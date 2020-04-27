package com.vhome.vhome.parents.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.ImagePagerActivity;

public class ShowPostImgAdapter extends BaseAdapter {

    private ArrayList<String> imgsList;
    private Context context;
    private LayoutInflater inflater;

    public ShowPostImgAdapter(ArrayList<String> imgsList, Context context) {
        this.imgsList = imgsList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgsList.size();
    }

    @Override
    public Object getItem(int i) {
        return imgsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_show_postimg, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+imgsList.get(i);
        Log.i("showimgadapter",i+"号图片地址"+url);
        /**
         * 加载图片
         */
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .build();
        ImageLoader.getInstance().displayImage(url,
                viewHolder.ivimage, options);

//        Glide.with(context)
//                .load(url)
//                .priority(Priority.HIGH)
//                .signature(new StringSignature(UUID.randomUUID().toString()))
//                .into(viewHolder.ivimage);

        //点击放大图片
        viewHolder.ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBrower(i, imgsList);
            }
        });

        return convertView;
    }

    //图片大图查看
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }

    public class ViewHolder {
        public ImageView ivimage;
        public View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.iv_post_simplegv);
            this.root = root;
        }
    }
}
