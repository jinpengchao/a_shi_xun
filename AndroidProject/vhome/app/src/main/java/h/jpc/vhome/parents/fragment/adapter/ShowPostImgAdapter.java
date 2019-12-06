package h.jpc.vhome.parents.fragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.util.List;

import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;

public class ShowPostImgAdapter extends BaseAdapter {

    private List<String> imgsList;
    private Context context;
    private LayoutInflater inflater;

    public ShowPostImgAdapter(List<String> imgsList, Context context) {
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
        Log.i("showimgadapter","图片地址"+url);
        Glide.with(context)
                .load(url)
                .priority(Priority.HIGH)
                .into(viewHolder.ivimage);

        return convertView;
    }
    public class ViewHolder {
        public final ImageView ivimage;
        public final View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.iv_post_simplegv);
            this.root = root;
        }
    }
}
