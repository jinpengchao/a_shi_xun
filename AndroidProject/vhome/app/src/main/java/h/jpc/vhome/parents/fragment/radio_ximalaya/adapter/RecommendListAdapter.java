package h.jpc.vhome.parents.fragment.radio_ximalaya.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import h.jpc.vhome.R;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {
    private List<Album> mData=new ArrayList<>();
    private OnRecommendClickListener mItemClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);

        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener!=null){
                    int clickPosition=(int)view.getTag();
                    mItemClickListener.OnItemClick(clickPosition,mData.get(clickPosition));
                }
            }
        });
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if(mData!=null){
            mData.clear();
            mData.addAll(albumList);
        }
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder{
        public InnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //专辑的头像封面
            ImageView albumconverIv=itemView.findViewById(R.id.album_cover);
            //标题
            TextView albumTitle=itemView.findViewById(R.id.album_title_tv);
           //播发数量
            TextView ablumCount=itemView.findViewById(R.id.album_play_count);
            //内容数量
            TextView contentCount=itemView.findViewById(R.id.album_content_size);
            //描述
            TextView decriTv =itemView.findViewById(R.id.album_description_tv);

            albumTitle.setText(album.getAlbumTitle());
            decriTv.setText(album.getAlbumIntro());
            ablumCount.setText(album.getPlayCount()+"");
            contentCount.setText(album.getIncludeTrackCount()+"");

            Picasso.with(itemView.getContext()).load(album.getCoverUrlMiddle()).into(albumconverIv);
        }
    }
    public void setOnRecommendItemClickListener(OnRecommendClickListener listener){
        this.mItemClickListener=listener;

    }
    public interface OnRecommendClickListener{
        void OnItemClick(int position, Album album);
    }
}
