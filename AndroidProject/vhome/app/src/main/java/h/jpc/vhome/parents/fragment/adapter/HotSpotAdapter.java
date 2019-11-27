package h.jpc.vhome.parents.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import h.jpc.vhome.parents.entity.Post;

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
        }
        return null;
    }
    static final class ViewHolder{
        ImageView ivType;
        TextView tvName;
        TextView tvDes;
        TextView tvPrice;
    }
}
