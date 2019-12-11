package h.jpc.vhome.parents.fragment.radio_ximalaya.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import h.jpc.vhome.R;

public class PlayerTrackPagerAdapter extends PagerAdapter {
    private List<Track> mData=new ArrayList<>();
    private View itemView;


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_page,container,false);
        container.addView(itemView);
        ImageView item=itemView.findViewById(R.id.track_pager_item);
        Track track=mData.get(position);
        String coverUrlLarge=track.getCoverUrlLarge();
        Picasso.with(container.getContext()).load(coverUrlLarge).into(item);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    public void setData(List<Track> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
