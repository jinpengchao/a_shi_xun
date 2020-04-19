package com.vhome.vhome.parents.fragment.radio_ximalaya.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;


public class SubscriptionFragment  extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView =layoutInflater.inflate(R.layout.fragment_subsription,container,false);
        return rootView;
    }
}
