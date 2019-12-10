package h.jpc.vhome.parents.fragment.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.PostBean;
import h.jpc.vhome.util.ConnectionUtil;

public class HotSpotAdapter extends BaseAdapter {
    private List<PostBean> list;
    private int itemLayoutId;
    private Context context;

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
        //设置gridview点击时间不响应
        holder.gvPostShow.setClickable(false);
        holder.gvPostShow.setPressed(false);
        holder.gvPostShow.setEnabled(false);
        //通过判断是否收藏点赞，设置收藏图标
        setImg(i,holder);
//        holder.ivHotPerson.setImageResource();
        holder.tvHotName.setText(list.get(i).getNickName());
        holder.tvHotContent.setText(list.get(i).getPostContent());
        //修改时间格式
        String time = list.get(i).getTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String now = new SimpleDateFormat("MM.dd HH:mm").format(date);
        holder.tvHotTime.setText(now);
        //加载说说图片
        String imgs = null;
        imgs = list.get(i).getImgs();
        Gson gson = new Gson();
        List<String> imgsList = gson.fromJson(imgs, new TypeToken<List<String>>() {
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
        final ViewHolder finalHolder = holder;
        holder.rlPostSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Log.e("收藏", "修改图标第个：" + i);
//                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save1);
//                    addPostCollection(i);
                if (1 == list.get(i).getSave_status()) {
                    Toast.makeText(view.getContext(), "已经收藏过了！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("收藏", "修改图标第个：" + i);
                    list.get(i).setSave_status(1);
                    finalHolder.ivHotSave.setImageResource(R.mipmap.post_save1);
                    addPostCollection(i);
                }
            }
        });

//        当点击喜欢的时候，添加到点赞表并改变图标。
        holder.rlPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (1 == list.get(i).getLike_status()) {
                    Toast.makeText(view.getContext(), "已经点赞过了！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("点赞", "修改点赞图标第个：" + i);
                    list.get(i).setLike_status(1);
                    finalHolder.ivHotlike.setImageResource(R.mipmap.post_img_good1);
                    //点赞个数加一
                    int cnum = Integer.parseInt(finalHolder.tvHotLikenum.getText().toString().trim())+1;
                    list.get(i).setLikeNum(cnum);
                    finalHolder.tvHotLikenum.setText(cnum+"");
                    addPostLike(i);
                }
            }
        });
//        点击评论表的时候，跳转到评论页面
//        holder.rlPostComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent commentIntent = new Intent();
//                commentIntent.setClass(context, CommentActivity.class);
//                context.startActivity(commentIntent);
//            }
//        });
        return view;

    }

    private void addPostLike(int i) {
        SharedPreferences sp = context.getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
        GoodPostBean goodPost = new GoodPostBean();
        goodPost.setPostId(list.get(i).getId());
        String goodPersonId = sp.getString("id", "");
        goodPost.setGoodPersonId(goodPersonId);
        goodPost.setPublishPersonId(list.get(i).getPersonId());
        Date likeDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(likeDate);
        goodPost.setTime(time);
        Gson gson = new Gson();
        final String likeData = gson.toJson(goodPost);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/SaveGoodPostServlet");
                    ConnectionUtil connectionUtil = new ConnectionUtil();
                    HttpURLConnection connection = connectionUtil.sendData(url,likeData);
                    String receive = connectionUtil.getData(connection);
                    if (null != receive && !"".equals(receive)) {
                        Log.i("hotSpotAdapter", "点赞成功" + likeData);
                    } else {
                        Log.e("hotSpotAdapter", "点赞失败！" + likeData);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void setImg(int i,ViewHolder holder) {
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

    private void addPostCollection(int i) {
        CollectionBean collection = new CollectionBean();
        PostBean post = list.get(i);
        SharedPreferences sp = context.getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
        String personId = sp.getString("id", "");
        Log.i("热点：收藏人id===", personId);
        collection.setPersonId(personId);
        collection.setPostId(post.getId());
        //首先准备收藏的数据
        Date collectDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(collectDate);
        collection.setTime(time);
        final Gson gson = new Gson();
        final String data = gson.toJson(collection);
        //开启线程保存到数据库
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + (new MyApp()).getIp() + ":8080/vhome/SaveCollectionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url, data);
                    String receive = util.getData(connection);
                    if (null != receive && !"".equals(receive)) {
                        Log.i("hotSpotAdapter", "收藏成功" + data);
                    } else {
                        Log.e("hotSpotAdapter", "收藏失败！" + data);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    static final class ViewHolder {
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
    }
}
