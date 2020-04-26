package com.vhome.vhome.parents.fragment.radio_ximalaya.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vhome.chat.R;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.InnerHolder> {
    private List<Track> detailData=new ArrayList<>();
    //格式化时间
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mDuration =new SimpleDateFormat("mm:ss");
    private IteClickListener mItemClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail,parent,false);

        return new InnerHolder(itemView);
    }

    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        //找到
        View itemView=holder.itemView;
        TextView orderTv =itemView.findViewById(R.id.order_text);
        TextView titleTv=itemView.findViewById(R.id.detail_item_title);
        TextView playCount=itemView.findViewById(R.id.detail_item_play_count);
        TextView duration=itemView.findViewById(R.id.detail_item_duration);
        TextView updateDateTv=itemView.findViewById(R.id.detail_item_update_time);

        Track track=detailData.get(position);
        orderTv.setText(position+"");
        titleTv.setText(track.getTrackTitle());
        playCount.setText(track.getPlayCount()+"");
        int durationMil=track.getDuration() * 1000;
        String mduration1=mDuration.format(durationMil);
        duration.setText(mduration1);
        String updateTime=simpleDateFormat.format(track.getUpdatedAt());
        updateDateTv.setText(updateTime);

        //设置item的点击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(detailData,position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return detailData.size();
    }

    public void setData(List<Track> tracks) {
        detailData.clear();
        detailData.addAll(tracks);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }
    }

    public void setIteClickListener(IteClickListener listener){
        this.mItemClickListener=listener;
    }
    public interface IteClickListener{
        void onItemClick(List<Track> detailData, int position);
    }
}
