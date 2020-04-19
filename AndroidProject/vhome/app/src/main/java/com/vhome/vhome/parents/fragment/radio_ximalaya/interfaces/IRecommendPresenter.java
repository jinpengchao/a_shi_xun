package com.vhome.vhome.parents.fragment.radio_ximalaya.interfaces;


import com.vhome.vhome.parents.fragment.radio_ximalaya.base.IBasePresenter;

public interface IRecommendPresenter extends IBasePresenter<IRecommendViewCallBack> {
    void getRecommendList();

    void pull2RefreshMore();

    void loadMore();


}
