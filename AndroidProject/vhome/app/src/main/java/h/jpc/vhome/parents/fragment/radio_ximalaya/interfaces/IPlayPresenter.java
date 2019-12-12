package h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import h.jpc.vhome.parents.fragment.radio_ximalaya.base.IBasePresenter;

public interface IPlayPresenter extends IBasePresenter<IPlayerCallBack> {
    void play();
    void pause();
    void stop();
    void playPre();
    void playNext();
    void swithPlayMode(XmPlayListControl.PlayMode mode);
    void getPlayList();
    void playByIndex(int index);
    void seekTo(int progress);
    boolean isPlay();

}
