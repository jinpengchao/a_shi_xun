package h.jpc.vhome.parents.fragment.radio_ximalaya.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;


public class HistoryFragment extends BaseFragment {

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView =layoutInflater.inflate(R.layout.fragment_history,container,false);
        return rootView;
    }
}
