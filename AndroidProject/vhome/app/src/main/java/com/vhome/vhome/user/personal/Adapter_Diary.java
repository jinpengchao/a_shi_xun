package com.vhome.vhome.user.personal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vhome.chat.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

class Adapter_Diary extends RecyclerView.Adapter<Adapter_Diary.ViewHolder>{
private ArrayList<String> datas= null;
        Adapter_Diary(ArrayList<String> datas) {
        this.datas = datas;
        }
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
        }
//获取数据的数量
@Override
public int getItemCount() {
        return datas.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public  ViewHolder(View view){
        super(view);
        mTextView = (TextView) view.findViewById(R.id.text);
    }
}
}
