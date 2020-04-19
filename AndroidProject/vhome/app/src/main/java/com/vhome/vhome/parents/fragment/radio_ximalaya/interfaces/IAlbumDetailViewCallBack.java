package com.vhome.vhome.parents.fragment.radio_ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailViewCallBack {
    void onDetailListLoaded(List<Track> tracks);
    void onAlbumLoad(Album album);
}
