package h.jpc.vhome.parents.fragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        if(null==view){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(itemLayoutId,null);
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
        }else {
            holder = (ViewHolder) view.getTag();
        }
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
        List<String> imgsList = gson.fromJson(imgs,new TypeToken<List<String>>(){}.getType());
        Log.i("hotspotadaper","图片列表数据个数："+imgsList.size());
        ShowPostImgAdapter showPostImgAdapter = new ShowPostImgAdapter(imgsList,context);
        holder.gvPostShow.setAdapter(showPostImgAdapter);
        holder.tvHotLikenum.setText(list.get(i).getLikeNum()+"");
        holder.tvHotComnum.setText(list.get(i).getCommentNum()+"");
        final ViewHolder finalHolder = holder;
        //点击收藏的时候改变图标颜色
        holder.rlPostSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHolder.ivHotSave.setImageResource(R.mipmap.post_save1);
                addPostCollection(i);
            }
        });
        return view;
    }

    private void addPostCollection(int i) {
        //首先准备收藏的数据
        CollectionBean collection = new CollectionBean();
        PostBean post = list.get(i);
        collection.setPersonId(post.getPersonId());
        collection.setPostId(post.getId());
        Date collectDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(collectDate);
        collection.setTime(time);
        Gson gson = new Gson();
        final String data = gson.toJson(collection);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/SaveCollectionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url,data);
                    String receive = util.getData(connection);
                    if (null!=receive && !"".equals(receive)){
                        Log.i("hotSpotAdapter","收藏成功"+data);
                    }else {
                        Log.e("hotSpotAdapter","收藏失败！"+data);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    static final class ViewHolder{
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
