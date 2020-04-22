package com.vhome.vhome.user.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhome.chat.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by WZJSB-01 on 2017/12/5.
 */

public class Fragment_Two extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_viewpager_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rec);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        initList();
        return view;
    }

    private void initList() {
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("日记"+i);
        }
        Adapter_Diary mAdapter = new Adapter_Diary(dataList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
