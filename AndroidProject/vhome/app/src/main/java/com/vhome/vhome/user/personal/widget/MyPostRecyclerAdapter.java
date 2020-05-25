package com.vhome.vhome.user.personal.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.MyMedia;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.util.GetVideoThumbnail;
import com.vhome.vhome.parents.fragment.myself.ShowMyselfActivity;

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

import androidx.recyclerview.widget.RecyclerView;
import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;

import static com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter.isImgUrl;

public class MyPostRecyclerAdapter extends RecyclerView.Adapter<MyPostRecyclerAdapter.MyViewHolder> {
    private List<PostBean> list;
    private Context context;
    private onMyLikeClick onMyLikeClick;
    private View inflater;
    private OnItemClickListener listener;
    private ArrayList<MyMedia> medias;
    private GetVideoThumbnail getVideoThumbnail = new GetVideoThumbnail();
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
        inflater = LayoutInflater.from(context).inflate(R.layout.item_my_hotspot,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        medias = new ArrayList<>();
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


        //通过判断是否收藏点赞，设置收藏图标
        setImg(i,holder);
        //设置发帖人logo.
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
            try {
                setPicToView(path,list.get(i).getHeadimg(), returnBitMap(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        holder.ivHotPerson.setImageResource();
        holder.tvHotName.setText(list.get(i).getNickName());
        //当点击头像或名字的 时候跳转到个人
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
        if(list.get(i).getImgs()!=null&&!"".equals(list.get(i).getImgs())){
            String imgs = null;
            imgs = list.get(i).getImgs();
            Gson gson = new Gson();
            List<String> imgsList = gson.fromJson(imgs, new TypeToken<List<String>>(){}.getType());
            if (imgsList.size()>0){
                for (String name:imgsList) {
                    String pUrl = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/"+name;
                    boolean isPic = isImgUrl(name);
                    if (true==isPic){//当是图片url时，只添加图片
                        medias.add(new MyMedia(pUrl));
                    }else {//当是视频的时候，获取视频缩略图作为图片，并加入视频url
                        String picPath = getVideoThumbnail.getFirstThumbPath(context,name,pUrl);
                        medias.add(new MyMedia(picPath,pUrl));
                    }
                }
                ArrayList<NineGridItem> nineGridItemList = new ArrayList<>();
                for (MyMedia myMedia : medias) {
                    String thumbnailUrl = myMedia.getImageUrl();
                    String bigImageUrl = thumbnailUrl;
                    String videoUrl = myMedia.getVideoUrl();
                    nineGridItemList.add(new NineGridItem(thumbnailUrl, bigImageUrl, videoUrl));
                }
                NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(nineGridItemList);
                holder.nineGridViewGroup.setAdapter(nineGridViewAdapter);
            }
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
        if("已审核".equals(list.get(i).getExamine())){
            holder.ivExam.setImageResource(R.drawable.examsuccess);
        }else if("审核失败".equals(list.get(i).getExamine())){
            holder.ivExam.setImageResource(R.drawable.examfailed);
        }else
            holder.ivExam.setImageResource(R.drawable.examing);
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
        NineGridViewGroup nineGridViewGroup;
        RelativeLayout rlPostSave;
        RelativeLayout rlPostComment;
        RelativeLayout rlPostLike;
        ImageView ivExam;
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
            nineGridViewGroup = view.findViewById(R.id.nineGrid);
            rlPostSave = view.findViewById(R.id.rl_post_save);
            rlPostComment = view.findViewById(R.id.rl_posts_comment);
            rlPostLike = view.findViewById(R.id.rl_post_like);
            ivExam = view.findViewById(R.id.exam);
        }
    }
    public static Bitmap returnBitMap(String url) throws IOException {
        URL imgUrl = new URL(url);
        Bitmap bitmap = null;
        final HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        return bitmap;
    }
    private void setPicToView(String path ,String phone,Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        if (file.exists()){
            file.delete();
            file.mkdirs();
        }else
            file.mkdirs();// 创建文件夹
        String fileName = path + phone+".jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                file.delete();
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
