package h.jpc.vhome.parents.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.Post;

public class HotSpotAdapter extends BaseAdapter {
    private List<Post> list;
    private int itemLayoutId;
    private Context context;
    public HotSpotAdapter(Context context,List<Post> list,int itemLayoutId) {
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
        ViewHolder holder = null;
        if(null==view){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(itemLayoutId,null);
            holder = new ViewHolder();
            holder.ivHotPerson = view.findViewById(R.id.iv_hot_person);
            holder.tvHotName = view.findViewById(R.id.tv_hot_name);
            holder.tvHotContent = view.findViewById(R.id.tv_hot_content);
            holder.tvHotTime = view.findViewById(R.id.tv_hot_time);
            holder.tvHotComnum = view.findViewById(R.id.tv_hot_comnum);
            holder.ivHotlike = view.findViewById(R.id.iv_hot_like);
            holder.tvHotLikenum = view.findViewById(R.id.tv_hot_likenum);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
//        holder.ivHotPerson.setImageResource();
        holder.tvHotContent.setText(list.get(i).getPostContent());
        String time = list.get(i).getPostTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String now = new SimpleDateFormat("MM.dd HH:mm").format(date);
        holder.tvHotTime.setText(now);
        return view;
    }
    static final class ViewHolder{
        ImageView ivHotPerson;
        TextView tvHotName;
        TextView tvHotContent;
        TextView tvHotTime;
        TextView tvHotComnum;
        ImageView ivHotlike;
        TextView tvHotLikenum;
    }
}
