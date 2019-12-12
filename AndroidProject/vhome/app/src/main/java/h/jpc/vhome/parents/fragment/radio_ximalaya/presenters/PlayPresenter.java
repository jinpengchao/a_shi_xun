package h.jpc.vhome.parents.fragment.radio_ximalaya.presenters;



import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

import h.jpc.vhome.MyApp;
import h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces.IPlayPresenter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces.IPlayerCallBack;
import h.jpc.vhome.parents.fragment.radio_ximalaya.utils.LogUtil;

public class PlayPresenter implements IPlayPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

    private List<IPlayerCallBack> iPlayerCallBacks=new ArrayList<>();

    private final XmPlayerManager playerManage;
    private String TAG="PlayPresenter";
    private Track mCurrentTrack;

    private  PlayPresenter(){
        playerManage = XmPlayerManager.getInstance(MyApp.getAppContext());
        playerManage.addAdsStatusListener(this);
        //注册播放器
        playerManage.addPlayerStatusListener(this);

    }
    private static PlayPresenter playPresenter;
    public static  PlayPresenter getPlayPresenter(){
        if(playPresenter==null){
            synchronized (PlayPresenter.class){
                if(playPresenter==null){
                    playPresenter=new PlayPresenter();
                }
            }
        }
        return playPresenter;
    }
    public boolean isPlayListSet=false;
    public  void setPlayList(List<Track> list, int playIndex){
        if (playerManage!=null){
            playerManage.setPlayList(list,playIndex);
            isPlayListSet=true;
            mCurrentTrack=list.get(playIndex);
        }else {
            LogUtil.d(TAG,"null");
        }

    }

    @Override
    public void play() {
        if(isPlayListSet){
            playerManage.play();
        }
    }

    @Override
    public void pause() {
        if (playerManage != null) {
            playerManage.pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void playPre() {
        if (playerManage != null) {
            playerManage.playPre();
        }
    }

    @Override
    public void playNext() {
        if (playerManage != null) {
            playerManage.playNext();
        }
    }

    @Override
    public void swithPlayMode(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {
        if (playerManage != null) {
            List<Track> playList=playerManage.getPlayList();
            for (IPlayerCallBack iPlayerCallBack : iPlayerCallBacks) {
                iPlayerCallBack.onListLoaded(playList);
            }
        }
    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int progress) {

    }

    @Override
    public boolean isPlay() {
        return playerManage.isPlaying();
    }

    @Override
    public void registerViewCallback(IPlayerCallBack iPlayerCallBack) {
        iPlayerCallBack.onTrackUpdate(mCurrentTrack);
        if(!iPlayerCallBacks.contains(iPlayerCallBack)){
            iPlayerCallBacks.add(iPlayerCallBack);
        }
    }

    @Override
    public void unRegisterViewCallback(IPlayerCallBack iPlayerCallBack) {
        iPlayerCallBacks.remove(iPlayerCallBack);
    }


    //============广告相关的回调方法start=============
    @Override
    public void onStartGetAdsInfo() {
        LogUtil.d(TAG,"onStartGetAdsInfo...");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        LogUtil.d(TAG,"onGetAdsInfo...");
    }

    @Override
    public void onAdsStartBuffering() {
        LogUtil.d(TAG,"onAdsStartBuffering...");
    }

    @Override
    public void onAdsStopBuffering() {
        LogUtil.d(TAG,"onAdsStopBuffering...");

    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtil.d(TAG,"onStartPlayAds...");

    }

    @Override
    public void onCompletePlayAds() {
        LogUtil.d(TAG,"onCompletePlayAds...");

    }

    @Override
    public void onError(int what, int extra) {
        LogUtil.d(TAG,"onError... what="+what+"extra="+extra);

    }
    //=========广告相关回调方法end==========
//=================播放器相关的start===============
    @Override
    public void onPlayStart() {
        for (IPlayerCallBack iPlayerCallBack : iPlayerCallBacks) {
            iPlayerCallBack.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        for (IPlayerCallBack iPlayerCallBack : iPlayerCallBacks) {
            iPlayerCallBack.onPlayPause();
        }
    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundPlayComplete() {

    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel1) {
        //代表的是当前播放的内容，如果通过getKind()方法获取他是什么类型的
        //track代表的是资源类型
        if (curModel1 instanceof Track) {
            Track currentTrack=(Track)curModel1;
            mCurrentTrack=currentTrack;
            for (IPlayerCallBack iPlayerCallBack : iPlayerCallBacks) {
                iPlayerCallBack.onTrackUpdate(mCurrentTrack);
            }
        }
    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {

    }

    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onPlayProgress(int currPos, int duration) {
        //单位是毫秒
        for (IPlayerCallBack iPlayerCallBack : iPlayerCallBacks) {
            iPlayerCallBack.onProgressChange(currPos,duration);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }
    //=================播放器相关的end===============
}
