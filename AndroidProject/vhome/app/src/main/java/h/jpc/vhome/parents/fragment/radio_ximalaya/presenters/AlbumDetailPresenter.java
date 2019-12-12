package h.jpc.vhome.parents.fragment.radio_ximalaya.presenters;


import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces.IAlbumDetailPresenter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces.IAlbumDetailViewCallBack;
import h.jpc.vhome.parents.fragment.radio_ximalaya.utils.Constants;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {
    private List<IAlbumDetailViewCallBack> mCallBacks=new ArrayList<>();

    private Album mTargetAlbum=null;

    private AlbumDetailPresenter(){

   }
   private static AlbumDetailPresenter sInstance=null;

   public  static AlbumDetailPresenter getInstance(){
       if(sInstance==null){
           synchronized (AlbumDetailPresenter.class){
               if(sInstance==null){
                   sInstance=new AlbumDetailPresenter();
               }
           }
       }
       return sInstance;
   }
    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(int albumId, int page) {
        //根据页码和专辑列表
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, albumId+"");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page+"");
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT+"");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                if (trackList != null) {
                    List<Track> tracks=trackList.getTracks();
                    handlerAlbumDetailResult(tracks);
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {

            }
        });
    }

    private void handlerAlbumDetailResult(List<Track> tracks) {
        for (IAlbumDetailViewCallBack mCallBack : mCallBacks) {
            mCallBack.onDetailListLoaded(tracks);
        }
    }





    public  void setTargetAlbum(Album targetAlbum){
       this.mTargetAlbum=targetAlbum;
    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallBack detailViewCallBack) {
        if(!mCallBacks.contains(detailViewCallBack)){
            mCallBacks.add(detailViewCallBack);
            if(mTargetAlbum!=null){
                detailViewCallBack.onAlbumLoad(mTargetAlbum);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(IAlbumDetailViewCallBack detailViewCallBack) {
        mCallBacks.remove(detailViewCallBack);
    }
}
