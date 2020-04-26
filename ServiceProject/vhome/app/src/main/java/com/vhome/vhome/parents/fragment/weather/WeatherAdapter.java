package com.vhome.vhome.parents.fragment.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import com.vhome.chat.R;

class ViewHolder1{


    ImageView imageView;

    TextView temperature;

    TextView weather;

    TextView date;

    TextView date1;

}

public class WeatherAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    private List<WeatherBean> mDatas;


    public WeatherAdapter(Context context, List<WeatherBean> listNewsBean){

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

        final ViewHolder1 viewHolder;

        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.weather_item, null);

            viewHolder = new ViewHolder1();

            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.show_image);

            viewHolder.weather=(TextView)convertView.findViewById(R.id.weather);

            viewHolder.temperature=(TextView)convertView.findViewById(R.id.temperature);

            viewHolder.date=(TextView)convertView.findViewById(R.id.date);

            viewHolder.date1=(TextView)convertView.findViewById(R.id.weather_date);

            convertView.setTag(viewHolder);

        } else{

            viewHolder = (ViewHolder1) convertView.getTag();

        }
        WeatherBean weatherBean=mDatas.get(position);

        if(weatherBean.getWeather().equals("晴")){
            viewHolder.imageView.setImageResource(R.drawable.taiyang);
        }else if(weatherBean.getWeather().contains("雨")){
            viewHolder.imageView.setImageResource(R.drawable.yu);
        }else {
            viewHolder.imageView.setImageResource(R.drawable.yin);
        }

        viewHolder.date.setText(weatherBean.getWeek());

        viewHolder.weather.setText(weatherBean.getWeather());

        viewHolder.temperature.setText(weatherBean.getTemperature());

        viewHolder.date1.setText(weatherBean.getDate());

        return convertView;

    }
}
