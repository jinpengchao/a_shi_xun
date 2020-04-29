package com.vhome.vhome.parents.fragment.radio_ximalaya;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.radio_ximalaya.adapter.PlayerTrackPagerAdapter;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseActivity;
import com.vhome.vhome.parents.fragment.radio_ximalaya.interfaces.IPlayerCallBack;
import com.vhome.vhome.parents.fragment.radio_ximalaya.presenters.PlayPresenter;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.List;

public class PlayerActivity extends BaseActivity implements IPlayerCallBack {

    private ImageView controlBtn;
    private PlayPresenter playPresenter;
    private SimpleDateFormat minFormat=new SimpleDateFormat("mm:ss");
    private SimpleDateFormat hourFormat=new SimpleDateFormat("hh:mm:ss");
    private TextView mTotalDuration;
    private TextView mCurrentPosition;
    private SeekBar durationBar;
    private ImageView playpreBtn;
    private ImageView playNextBtn;
    private TextView trackTitle;
    private String mTrackTItleText;
    private ViewPager trackPageView;
    private PlayerTrackPagerAdapter trackPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //消去activity中的label
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_player);
        playPresenter = PlayPresenter.getPlayPresenter();
        playPresenter.registerViewCallback(this);
//        playPresenter.play();
        initView();
        //在界面初始化之后采取获取数据
        playPresenter.getPlayList();
        initEvent();
        startPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playPresenter != null) {
            playPresenter.unRegisterViewCallback(this);
            playPresenter=null;
        }

    }

    private void startPlay() {
        if (playPresenter != null) {
            playPresenter.play();
        }
    }

    private void initEvent() {
        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playPresenter.isPlay()) {
                    playPresenter.pause();
                }else{
                    playPresenter.play();
                }
            }
        });

        playpreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
                if (playPresenter != null) {
                    playPresenter.playPre();
                }
            }
        });
        playNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
                if (playPresenter != null) {
                    playPresenter.playNext();
                }
            }
        });
    }

    private void initView() {
        controlBtn = this.findViewById(R.id.play_stop_btn);
        mTotalDuration = this.findViewById(R.id.track_duration);
        mCurrentPosition = this.findViewById(R.id.current_position);
        durationBar = this.findViewById(R.id.track_seek_bar);
        playNextBtn = this.findViewById(R.id.next_btn);
        playpreBtn = this.findViewById(R.id.pre_btn);
        trackTitle = this.findViewById(R.id.track_title);
        if (!TextUtils.isEmpty(mTrackTItleText)) {
            trackTitle.setText(mTrackTItleText);
        }
        trackPageView = this.findViewById(R.id.track_page_view);
        trackPagerAdapter = new PlayerTrackPagerAdapter();
        trackPageView.setAdapter(trackPagerAdapter);
    }

    @Override
    public void onPlayStart() {
        controlBtn.setImageResource(R.drawable.zanting);
    }

    @Override
    public void onPlayPause() {
        controlBtn.setImageResource(R.drawable.ting);
    }

    @Override
    public void onPlayStop() {
        controlBtn.setImageResource(R.drawable.ting);
    }

    @Override
    public void onPlayError() {

    }

    @Override
    public void nextPlay(Track track) {

    }

    @Override
    public void onPrePlay(Track track) {

    }

    @Override
    public void onListLoaded(List<Track> list) {
        if (trackPagerAdapter != null) {
            trackPagerAdapter.setData(list);
        }
    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {

    }

    @Override
    public void onProgressChange(int currentProgress, int total) {
        //更新播放进度
        String totalDuration;
        String currentPosition;
        if (total>1000*60*60) {
            totalDuration=hourFormat.format(total);
            currentPosition=hourFormat.format(currentProgress);
        }else{
            totalDuration=minFormat.format(total);
            currentPosition=minFormat.format(currentProgress);
        }
        if (mTotalDuration != null) {
            mTotalDuration.setText(totalDuration);
        }
        if(mCurrentPosition!=null){
            mCurrentPosition.setText(currentPosition);
        }
        //计算进度
        int percent=(int)(currentProgress*1.0f/total*100);
        durationBar.setProgress(percent);
    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdFinished() {

    }

    @Override
    public void onTrackUpdate(Track track){
        this.mTrackTItleText=track.getTrackTitle();
        if(trackTitle!=null){
            trackTitle.setText(mTrackTItleText);
        }
        //当前节目改变的时候获取当前播放器中的位置
        //TODO:
    }
}
