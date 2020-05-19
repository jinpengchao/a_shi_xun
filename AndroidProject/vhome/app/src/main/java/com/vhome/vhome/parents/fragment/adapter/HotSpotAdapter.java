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

public class HotSpotAdapter extends BaseAdapter {
    private List<PostBean> list;
    private int itemLayoutId;
    private Context context;
    private onMyLikeClick onMyLikeClick;
    private ArrayList<String> imgsList;

    public interface onMyLikeClick{
        public void myLikeClick(int position,int status);
        public void myCollectClick(int position,int status);
    }
    public void setOnMyLikeClick(onMyLikeClick onMyLikeClick){
        this.onMyLikeClick = onMyLikeClick;
    }

    public HotSpotAdapter(Context context, List<PostBean> list, int itemLayoutId) {
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
            holder.tvHotTime = view.findViewById(R.id.tv_hot_time);
            holder.ivHotSave = view.findViewById(R.id.iv_hot_save);
            holder.tvHotComnum = view.findViewById(R.id.tv_hot_comnum);
            holder.ivHotlike = view.findViewById(R.id.iv_hot_like);
            holder.tvHotLikenum = view.findViewById(R.id.tv_hot_likenum);
            holder.gvPostShow = view.findViewById(R.id.gv_post_show);
            holder.rlPostSave = view.findViewById(R.id.rl_post_save);
            holder.rlPostComment = view.findViewById(R.id.rl_post_comment);
            holder.rlPostLike = view.findViewById(R.id.rl_post_like);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //设置gridview点击事件不响应
//        holder.gvPostShow.setClickable(false);
//        holder.gvPostShow.setPressed(false);
//        holder.gvPostShow.setEnabled(false);
        //通过判断是否收藏点赞，设置收藏图标
        setImg(i,holder);
        //设置发帖人logo
        //刷新本地头像
        String path = "/sdcard/"+list.get(i).getHeadimg()+"/";// sd路径
        Bitmap bt = BitmapFactory.decodeFile(path + list.get(i).getHeadimg()+".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {

            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            (holder.ivHotPerson).setImageDrawable(drawable);
        } else {
            String url = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" +list.get(i).getHeadimg() + ".jpg";
            Glide.with(context)
                    .load(url)
                    .priority(Priority.HIGH)
                    .into(holder.ivHotPerson);
        }
//        holder.ivHotPerson.setImageResource();
        holder.tvHotName.setText(list.get(i).getNickName());
        //当点击头像或名字的 时候跳转到个人页
        holder.ivHotPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent person = new Intent();
                person.putExtra("personId",list.get(i).getPersonId());
                person.putExtra("headImg",list.get(i).getHeadimg());
                SharedPreferences sharedPreferences = context.getSharedPreferences("parentUserInfo",Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("id","");
                if(id.equals(list.get(i).getPersonId())){
                    person.setClass(context, MySelfActivity.class);
                }else {
                    person.setClass(context, OthersSerlfActivity.class);
                }
                context.startActivity(person);
            }
        });
        holder.tvHotContent.setText(list.get(i).getPostContent());
        //修改时间格式
        String time = list.get(i).getTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String now = new SimpleDateFormat("MM-dd HH:mm").format(date);
        holder.tvHotTime.setText(now);
        //加载说说图片
        String imgs = null;
        imgs = list.get(i).getImgs();
        Gson gson = new Gson();
        imgsList = gson.fromJson(imgs, new TypeToken<List<String>>() {
        }.getType());
        Log.e("hotspotadaper", "内容：："+list.get(i).getPostContent()+"：：图片列表数据：" + imgsList.toString());
        if (imgsList.size() > 0) {
            ShowPostImgAdapter showPostImgAdapter = new ShowPostImgAdapter(imgsList, context);
            holder.gvPostShow.setAdapter(showPostImgAdapter);
            showPostImgAdapter.notifyDataSetChanged();
        } else {
            holder.gvPostShow.setVisibility(View.GONE);
        }
        //设置评论人数和点赞人数
        holder.tvHotLikenum.setText(list.get(i).getLikeNum() + "");
        holder.tvHotComnum.setText(list.get(i).getCommentNum() + "");

        //点击收藏的时候,收藏成功改变图标颜色
        final ViewHolder finalHolder = holder;
        holder.rlPostSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyLikeClick.myCollectClick(i,list.get(i).getSave_status());
                if (1 == list.get(i).getSave_status()) {
                    list.get(i).setSave_status(0);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save);
                } else {
                    list.get(i).setSave_status(1);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save1);
//                    addPostCollection(i);
                }
            }
        });

//        当点击喜欢的时候，添加到点赞表并改变图标。
        holder.rlPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyLikeClick.myLikeClick(i,list.get(i).getLike_status());
                if (1 == list.get(i).getLike_status()) {
                    Log.i("点赞", "取消点赞成功" );
                    list.get(i).setLike_status(0);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good);
                    //点赞个数减一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())-1;
                    list.get(i).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                } else {
                    Log.i("点赞", "修改点赞图标第个：" + i);
                    list.get(i).setLike_status(1);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
                    //点赞个数加一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())+1;
                    list.get(i).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                }
            }
        });
        return view;

    }


    private void setImg(int i,ViewHolder holder) {
        if (list.get(i).getSave_status() == 1) {
            holder.ivHotSave.setImageResource(R.mipmap.post_save1);
        }else {
            holder.ivHotSave.setImageResource(R.mipmap.post_save);
        }
        if (list.get(i).getLike_status()==1){
            holder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
        }else {
            holder.ivHotlike.setImageResource(R.mipmap.post_img_good);
        }
    }


    private class ViewHolder {
        CircleImageView ivHotPerson;
        TextView tvHotName;
        TextView tvHotContent;
        TextView tvHotTime;
        ImageView ivHotSave;
        TextView tvHotComnum;
        ImageView ivHotlike;
        TextView tvHotLikenum;
        GridView gvPostShow;
        RelativeLayout rlPostSave;
        RelativeLayout rlPostComment;
        RelativeLayout rlPostLike;
    }
}
