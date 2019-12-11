package h.jpc.vhome.parents.fragment.radio_ximalaya;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.radio_ximalaya.adapter.DetailListAdapter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.base.BaseActivity;
import h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces.IAlbumDetailViewCallBack;
import h.jpc.vhome.parents.fragment.radio_ximalaya.presenters.AlbumDetailPresenter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.presenters.PlayPresenter;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallBack, DetailListAdapter.IteClickListener {
    private ImageView mLargeCover;
    private ImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private AlbumDetailPresenter albumDetailPresenter;
    private  int mCurrentPage =1;
    private RecyclerView recyclerView;
    private DetailListAdapter detailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        albumDetailPresenter=AlbumDetailPresenter.getInstance();
        albumDetailPresenter.registerViewCallback(this);

    }

    private void initView() {
        mLargeCover=this.findViewById(R.id.iv_large_cover);
        mSmallCover=this.findViewById(R.id.iv_small_cover);
        mAlbumTitle=this.findViewById(R.id.tv_album_title);
        mAlbumAuthor=this.findViewById(R.id.tv_album_author);
        recyclerView=this .findViewById(R.id.album_detail_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //设置适配器
        detailListAdapter = new DetailListAdapter();
        recyclerView.setAdapter(detailListAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5) ;
                outRect.bottom= UIUtil.dip2px(view.getContext(),5) ;
                outRect.left= UIUtil.dip2px(view.getContext(),5) ;
                outRect.right= UIUtil.dip2px(view.getContext(),5) ;
            }
        });

        detailListAdapter.setIteClickListener(this);
    }


    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        //更新
        detailListAdapter.setData(tracks);
    }

    @Override
    public void onAlbumLoad(Album album) {
        //获取专辑的详情
        albumDetailPresenter.getAlbumDetail((int)album.getId(),mCurrentPage);

        mAlbumTitle.setText(album.getAlbumTitle());
        mAlbumAuthor.setText(album.getAnnouncer().getNickname());
        Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover);
        Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);
    }

    @Override
    public void onItemClick(List<Track> detailData, int position) {
        //设置播放器的数据
        PlayPresenter playPresenter= PlayPresenter.getPlayPresenter();
        playPresenter.setPlayList(detailData,position);
        //跳转到播放界面
        Intent intent=new Intent(this,PlayerActivity.class);
        startActivity(intent);
    }
}
