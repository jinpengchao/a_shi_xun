package h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendViewCallBack {
    void onRecommendListLoaded(List<Album> result);

}
