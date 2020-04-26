package com.vhome.vhome.parents.fragment.radio_ximalaya.interfaces;


import com.vhome.vhome.parents.fragment.radio_ximalaya.base.IBasePresenter;

public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailViewCallBack> {
    void pull2RefreshMore();

    void loadMore();

    void getAlbumDetail(int albumId, int page);

}
