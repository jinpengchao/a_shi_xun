package h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces;


import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public interface IPlayerCallBack {
    void onPlayStart();
    void onPlayPause();
    void onPlayStop();
    void onPlayError();
    void nextPlay(Track track);
    void onPrePlay(Track track);
    void onListLoaded(List<Track> list);
    void onPlayModeChange(XmPlayListControl.PlayMode playMode);
    void onProgressChange(int currentProgress, int total);
    void onAdLoading();
    void onAdFinished();
    //更新当前节目的标题
    void onTrackUpdate(Track track);
}
