package com.vhome.vhome.parents.fragment.radio_ximalaya.presenters;

import androidx.annotation.Nullable;
import com.vhome.vhome.parents.fragment.radio_ximalaya.interfaces.IRecommendPresenter;
import com.vhome.vhome.parents.fragment.radio_ximalaya.interfaces.IRecommendViewCallBack;
import com.vhome.vhome.parents.fragment.radio_ximalaya.utils.Constants;
import com.vhome.vhome.parents.fragment.radio_ximalaya.utils.LogUtil;

import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.download.RecommendDownload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private  static final String TAG="RecommendPresenter";
    private List<IRecommendViewCallBack> mCallBacks=new ArrayList<>();
    private RecommendPresenter(){}
    private static  RecommendPresenter sInstance=null;
    public static RecommendPresenter getsInstance(){
        if(sInstance==null){
            synchronized (RecommendPresenter.class){
                if(sInstance==null){
                    sInstance=new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getRecommendList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CALC_DIMENSION, "3");
        map.put(DTransferConstants.PAGE,"5");
        CommonRequest.getRecommendDownloadList(map, new IDataCallBack<RecommendDownload>() {
            @Override
            public void onSuccess(RecommendDownload recommendDownload) {
                if (recommendDownload!=null){
                    List<Album> albumList=recommendDownload.getAlbums();
                    handlerRecommendResult(albumList);
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"error--->"+i);
                LogUtil.d(TAG,"errorMSg--->"+s);
            }
        });
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }


    private void handlerRecommendResult(List<Album> albumList) {
        //通知UI更新
        if(mCallBacks!=null){
            for(IRecommendViewCallBack callback: mCallBacks){
                callback.onRecommendListLoaded(albumList);
            }
        }

    }





    @Override
    public void registerViewCallback(IRecommendViewCallBack iRecommendViewCallBack) {
        if(mCallBacks!=null&&!mCallBacks.contains(iRecommendViewCallBack)){
            mCallBacks.add(iRecommendViewCallBack);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallBack iRecommendViewCallBack) {
        if (mCallBacks!=null){
            mCallBacks.remove(mCallBacks);
        }
    }
}
