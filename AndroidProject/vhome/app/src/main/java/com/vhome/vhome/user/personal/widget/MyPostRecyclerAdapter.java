package com.vhome.vhome.user.personal.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.adapter.ShowPostImgAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.myself.ShowMyselfActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPostRecyclerAdapter extends RecyclerView.Adapter<MyPostRecyclerAdapter.MyViewHolder> {
    private List<PostBean> list;
    private Context context;
    private onMyLikeClick onMyLikeClick;
    private View inflater;
    private OnItemClickListener listener;
    public interface onMyLikeClick{
        public void myLikeClick(int position,int status);
        public void myCollectClick(int position,int status);
    }
    public void setOnMyLikeClick( onMyLikeClick onMyLikeClick){
        this.onMyLikeClick = onMyLikeClick;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemLongClickListener {
        void onClick(int position);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    public MyPostRecyclerAdapter(Context context, List<PostBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item_hotspot,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(i);
                }
                return true;
            }
        });
        //设置gridview点击事件不响应
//        holder.gvPostShow.setClickable(false);
//        holder.gvPostShow.setPressed(false);
//        holder.gvPostShow.setEnabled(false);
        //通过判断是否收藏点赞，设置收藏图标
        setImg(i,holder);
        //设置发帖人logo
        String user_logo = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+ list.get(i).getHeadimg()+".jpg";;
        Log.e("hostadapter","头像"+list.get(i).getHeadimg());
        Glide.with(context)
                .load(user_logo)
                .priority(Priority.HIGH)
                .error(R.mipmap.errorimg1)
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .centerCrop()
                .into(holder.ivHotPerson);
//        holder.ivHotPerson.setImageResource();
        holder.tvHotName.setText(list.get(i).getNickName());
        //当点击头像或名字的 时候跳转到个人页
        holder.ivHotPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent person = new Intent();
                person.putExtra("personId",list.get(i).getPersonId());
                person.setClass(context, ShowMyselfActivity.class);
                context.startActivity(person);
            }
        });
        holder.tvHotName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent person = new Intent();
                person.putExtra("personId",list.get(i).getPersonId());
                person.setClass(context, ShowMyselfActivity.class);
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
        ArrayList<String> imgsList = gson.fromJson(imgs, new TypeToken<List<String>>() {
        }.getType());
        Log.i("hotspotadaper", "图片列表数据个数：" + imgsList.size());
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
        final MyViewHolder finalHolder = holder;
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
                    list.get(i).setLike_status(0);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good);
                    //点赞个数减一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())-1;
                    list.get(i).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                } else {
                    Log.e("点赞", "修改点赞图标第个：" + i);
                    list.get(i).setLike_status(1);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
                    //点赞个数加一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())+1;
                    list.get(i).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }
    private void setImg(int i, MyPostRecyclerAdapter.MyViewHolder holder) {
        if (list.get(i).getSave_status() == 1) {
            holder.ivHotSave.setImageResource(R.mipmap.post_save1);
            Log.e("收藏", "收藏过的第个：" + i);
        }else {
            holder.ivHotSave.setImageResource(R.mipmap.post_save);
        }
        if (list.get(i).getLike_status()==1){
            holder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
            Log.e("点赞", "点赞过的第个：" + i);
        }else {
            holder.ivHotlike.setImageResource(R.mipmap.post_img_good);
        }
    }
    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHotPerson;
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
        public MyViewHolder(View view) {
            super(view);
            ivHotPerson = view.findViewById(R.id.iv_hot_person);
            tvHotName = view.findViewById(R.id.tv_hot_name);
            tvHotContent = view.findViewById(R.id.tv_hot_content);
            tvHotTime = view.findViewById(R.id.tv_hot_time);
            ivHotSave = view.findViewById(R.id.iv_hot_save);
            tvHotComnum = view.findViewById(R.id.tv_hot_comnum);
            ivHotlike = view.findViewById(R.id.iv_hot_like);
            tvHotLikenum = view.findViewById(R.id.tv_hot_likenum);
            gvPostShow = view.findViewById(R.id.gv_post_show);
            rlPostSave = view.findViewById(R.id.rl_post_save);
            rlPostComment = view.findViewById(R.id.rl_post_comment);
            rlPostLike = view.findViewById(R.id.rl_post_like);
        }
    }
}
