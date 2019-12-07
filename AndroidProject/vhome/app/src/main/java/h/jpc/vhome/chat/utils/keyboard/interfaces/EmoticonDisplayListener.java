package h.jpc.vhome.chat.utils.keyboard.interfaces;

import android.view.ViewGroup;

import h.jpc.vhome.chat.utils.keyboard.adpater.EmoticonsAdapter;

public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
