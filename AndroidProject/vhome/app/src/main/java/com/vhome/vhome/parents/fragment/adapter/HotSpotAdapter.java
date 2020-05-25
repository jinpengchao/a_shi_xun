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
import com.bumptech.glide.signature.ObjectKey;
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
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.CommentActivity;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.MyMedia;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.util.GetVideoThumbnail;
import com.vhome.vhome.parents.fragment.myself.ShowMyselfActivity;
import com.vhome.vhome.user.personal.MySelfActivity;
import com.vhome.vhome.user.personal.OthersSerlfActivity;
import com.vhome.vhome.user.personal.widget.CircleImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;
import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;

public class HotSpotAdapter extends RecyclerView.Adapter<HotSpotAdapter.ViewHolder> {
    private static final String TAG = "HotSpotAdapter";
    private List<PostBean> list;
    private int itemLayoutId;
    private Context context;
    private onMyLikeClick onMyLikeClick;
    private ArrayList<MyMedia> medias;
    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");
    private GetVideoThumbnail getVideoThumbnail = new GetVideoThumbnail();
    private OnItemClickListener onItemClickListener = null;
    private OnItemLongClickListener onItemLongClickListener = null;

    public interface OnItemClickListener{
        void onItemClick(int positon);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface onMyLikeClick{
        void myLikeClick(int position,int status);
        void myCollectClick(int position,int status);
    }
    public void setOnMyLikeClick(onMyLikeClick onMyLikeClick){
        this.onMyLikeClick = onMyLikeClick;
    }
    public HotSpotAdapter(){

    }
    public HotSpotAdapter(Context context, List<PostBean> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ivHotPerson;
        TextView tvHotName;
        TextView tvHotContent;
        TextView tvHotTime;
        ImageView ivHotSave;
        TextView tvSave;
        ImageView ivHotComment;
        TextView tvHotComnum;
        ImageView ivHotlike;
        TextView tvHotLikenum;
        NineGridViewGroup nineGridViewGroup;
        public ViewHolder(@NonNull View view) {
            super(view);

            ivHotPerson = view.findViewById(R.id.iv_hot_person);
            tvHotName = view.findViewById(R.id.tv_hot_name);
            tvHotContent = view.findViewById(R.id.tv_hot_content);
            tvHotTime = view.findViewById(R.id.tv_hot_time);
            ivHotSave = view.findViewById(R.id.iv_hot_save);
            tvSave = view.findViewById(R.id.tv_save);
            ivHotComment = view.findViewById(R.id.iv_hot_comment);
            tvHotComnum = view.findViewById(R.id.tv_hot_comnum);
            ivHotlike = view.findViewById(R.id.iv_hot_like);
            tvHotLikenum = view.findViewById(R.id.tv_hot_likenum);
            nineGridViewGroup = view.findViewById(R.id.nineGrid);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotspot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        medias = new ArrayList<>();
        //通过判断是否收藏点赞，设置收藏图标
        setImg(position,holder);
        //设置评论图标
        holder.ivHotComment.setImageResource(R.mipmap.posts_comment);
        //设置发帖人logo
        //刷新本地头像
        String path = "/sdcard/"+list.get(position).getHeadimg()+"/";// sd路径
        Bitmap bt = BitmapFactory.decodeFile(path + list.get(position).getHeadimg()+".jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {

            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            (holder.ivHotPerson).setImageDrawable(drawable);
        } else {
            String url = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/" +list.get(position).getHeadimg() + ".jpg";
            Glide.with(context)
                    .load(url)
                    .signature(new ObjectKey(UUID.randomUUID().toString()))
                    .priority(Priority.HIGH)
                    .into(holder.ivHotPerson);
            try {
                setPicToView(path,list.get(position).getHeadimg(), returnBitMap(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        holder.ivHotPerson.setImageResource();
        holder.tvHotName.setText(list.get(position).getNickName());
        //当点击头像或名字的 时候跳转到个人页
        holder.ivHotPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent person = new Intent();
                person.putExtra("personId",list.get(position).getPersonId());
                person.putExtra("headImg",list.get(position).getHeadimg());
                SharedPreferences sharedPreferences = context.getSharedPreferences("parentUserInfo",Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("id","");
                if(id.equals(list.get(position).getPersonId())){
                    person.setClass(context, MySelfActivity.class);
                }else {
                    person.setClass(context, OthersSerlfActivity.class);
                }
                context.startActivity(person);
            }
        });
        holder.tvHotName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent person = new Intent();
                person.putExtra("personId",list.get(position).getPersonId());
                person.setClass(context, ShowMyselfActivity.class);
                context.startActivity(person);
            }
        });
        holder.tvHotContent.setText(list.get(position).getPostContent());
        //修改时间格式
        String time = list.get(position).getTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String now = new SimpleDateFormat("MM-dd HH:mm").format(date);
        holder.tvHotTime.setText(now);
        /**
         * 添加说说图片数据
         */
        if(list.get(position).getImgs()!=null&&!"".equals(list.get(position).getImgs())){
            Log.d(TAG, "onBindViewHolder: 图片数据："+list.get(position).getImgs()+"::内容："+list.get(position).getPostContent());
            String imgs = list.get(position).getImgs();
            Gson gson = new Gson();
            List<String> imgsList = gson.fromJson(imgs, new TypeToken<List<String>>() {
            }.getType());
            Log.d(TAG, "onBindViewHolder: imgsList数据个数："+imgsList.size());
            if (imgsList.size()>0){

                for (String name:imgsList) {
                    String pUrl = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/"+name;
                    boolean isPic = isImgUrl(name);
                    if (true==isPic){//当是图片url时，只添加图片
                        medias.add(new MyMedia(pUrl));
                        Log.d(TAG,"图片的url："+pUrl);
                    }else {//当是视频的时候，获取视频缩略图作为图片，并加入视频url
                        String picPath = getVideoThumbnail.getFirstThumbPath(context,name,pUrl);
                        Log.d(TAG, "onBindViewHolder: 视频展示图路径："+picPath);
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
                Log.d(TAG, "onBindViewHolder: medias数据："+medias.get(0).getImageUrl()+"::内容："+list.get(position).getPostContent());
                holder.nineGridViewGroup.setAdapter(nineGridViewAdapter);
            }
        }
        // 为满足九宫格适配器数据要求，需要构造对应的List

        //设置评论人数和点赞人数
        holder.tvHotLikenum.setText(list.get(position).getLikeNum() + "");
        holder.tvHotComnum.setText(list.get(position).getCommentNum() + "");

        //点击收藏的时候,收藏成功改变图标颜色
        final ViewHolder finalHolder = holder;
        holder.ivHotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyLikeClick.myCollectClick(position,list.get(position).getSave_status());
                if (1 == list.get(position).getSave_status()) {
                    list.get(position).setSave_status(0);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save);
                } else {
                    list.get(position).setSave_status(1);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save1);
//                    addPostCollection(i);
                }
            }
        });
        holder.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyLikeClick.myCollectClick(position,list.get(position).getSave_status());
                if (1 == list.get(position).getSave_status()) {
                    list.get(position).setSave_status(0);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save);
                } else {
                    list.get(position).setSave_status(1);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save1);
//                    addPostCollection(i);
                }
            }
        });

//        当点击喜欢的时候，添加到点赞表并改变图标。
        holder.ivHotlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyLikeClick.myLikeClick(position,list.get(position).getLike_status());
                if (1 == list.get(position).getLike_status()) {
                    Log.i("点赞", "取消点赞成功" );
                    list.get(position).setLike_status(0);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good);
                    //点赞个数减一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())-1;
                    list.get(position).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                } else {
                    Log.i("点赞", "修改点赞图标第个：" + position);
                    list.get(position).setLike_status(1);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
                    //点赞个数加一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())+1;
                    list.get(position).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                }
            }
        });
        holder.tvHotLikenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyLikeClick.myLikeClick(position,list.get(position).getLike_status());
                if (1 == list.get(position).getLike_status()) {
                    Log.i("点赞", "取消点赞成功" );
                    list.get(position).setLike_status(0);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good);
                    //点赞个数减一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())-1;
                    list.get(position).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                } else {
                    Log.i("点赞", "修改点赞图标第个：" + position);
                    list.get(position).setLike_status(1);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
                    //点赞个数加一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())+1;
                    list.get(position).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener!=null){
                    onItemLongClickListener.onItemLongClick(position);
                }
                return true;
            }
        });
    }

    public String getPicPath(String url, String pUrl) {
        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();
        Bitmap bitmap = getVideoThumbnail.voidToFirstBitmap(pUrl);
        Bitmap vBitmap = getVideoThumbnail.toConformBitmap(context,bitmap);
        return getVideoThumbnail.bitmapToStringPath(context,vBitmap,url);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
        String fileName = path +phone+".jpg";// 图片名字
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
