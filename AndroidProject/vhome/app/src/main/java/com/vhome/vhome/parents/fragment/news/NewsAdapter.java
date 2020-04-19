package com.vhome.vhome.parents.fragment.news;
/*
 *功能：NewsAdapter
 * 作者：章鹏
 * 完成时间：2019-11-25 19:15
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import com.vhome.chat.R;
import com.vhome.vhome.parents.MyImageView;


class ViewHolder{

    MyImageView item_img_icon;

    TextView item_tv_author_name;

    TextView item_tv_title;

    TextView item_tv_date;

    TextView item_tv_category;

}

public class NewsAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    private List<NewsBean> mDatas;


    public NewsAdapter(Context context, List<NewsBean> listNewsBean){

        this.mLayoutInflater = LayoutInflater.from(context);

        this.mDatas = listNewsBean;

    }



    @Override

    public int getCount() {

        return mDatas.size();

    }



    @Override

    public Object getItem(int position) {

        return mDatas.get(position);

    }



    @Override

    public long getItemId(int position) {

        return position;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.news_item, null);

            viewHolder = new ViewHolder();

            viewHolder.item_img_icon = (MyImageView) convertView.findViewById(R.id.item_img_icon);

            viewHolder.item_tv_author_name = (TextView) convertView.findViewById(R.id.item_tv_author_name);

            viewHolder.item_tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);

            viewHolder.item_tv_date = (TextView) convertView.findViewById(R.id.item_tv_date);

            viewHolder.item_tv_category= (TextView) convertView.findViewById(R.id.item_tv_category);

            convertView.setTag(viewHolder);

        } else{

            viewHolder = (ViewHolder) convertView.getTag();

        }

        NewsBean newsBean= mDatas.get(position);
        viewHolder.item_img_icon.setImageUrl(newsBean.getThumbnail_pic_s());
        viewHolder.item_tv_author_name.setText(newsBean.getAuthor_name());

        viewHolder.item_tv_title.setText(newsBean.getTitle());

        viewHolder.item_tv_date.setText(newsBean.getDate());

        viewHolder.item_tv_category.setText(newsBean.getCategory());

        return convertView;

    }




}
