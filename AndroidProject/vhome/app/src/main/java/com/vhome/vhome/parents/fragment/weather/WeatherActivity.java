package com.vhome.vhome.parents.fragment.weather;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.vhome.chat.R;
import com.vhome.vhome.parents.HttpLogin;

public class WeatherActivity extends Activity {
    private List<WeatherBean> weather_information=new ArrayList<>();
    private ListView weather;
    private SmartRefreshLayout srl1;
    private WeatherAdapter weatherAdapter;
    private Button button;
    private EditText editText;
    private TextView textView1,textView2,textView3,textView4;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 3:
                    Bundle bundle1=new Bundle();
                    bundle1=msg.getData();
                    String result1=bundle1.getString("result1");
                    SharedPreferences sharedPreferences = getSharedPreferences("today_weather",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //显示jason数据
//                    Toast.makeText(getApplication(),result1,Toast.LENGTH_LONG).show();
                    try{
                        JSONObject jsonObject=new JSONObject(result1);
                        JSONObject jsonObject2=jsonObject.getJSONObject("result");
                        JSONObject jsonObject3=jsonObject2.getJSONObject("sk");
                        JSONObject jsonObject4=jsonObject2.getJSONObject("today");
                        textView2.setText("当前温度："+jsonObject3.getString("temp")+"摄氏度"+"(更新时间："+jsonObject3.getString("time")+")");
                        textView1.setText(jsonObject4.getString("city"));
                        textView3.setText(jsonObject4.getString("temperature")+"，"+jsonObject4.getString("wind")+"，"+jsonObject4.getString("dressing_index")+"，"+jsonObject4.getString("weather"));
                        textView4.setText(jsonObject4.getString("dressing_advice"));
                        JSONArray jsonArray=jsonObject2.getJSONArray("future");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            WeatherBean weatherBean=new WeatherBean();
                            weatherBean.setTemperature(jsonObject1.getString("temperature"));
                            weatherBean.setWeather(jsonObject1.getString("weather"));
                            weatherBean.setWeek(jsonObject1.getString("week"));
                            weatherBean.setDate(jsonObject1.getString("date"));
                            if(i==0){
                                editor.putString("temperature",jsonObject1.getString("temperature"));
                                editor.putString("weather",jsonObject1.getString("weather"));
                                editor.putString("week",jsonObject1.getString("week"));
                                editor.putString("date",jsonObject1.getString("date"));
                                editor.commit();
                            }
                            weather_information.add(weatherBean);
                        }
                        weatherAdapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //消去activity中的label
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather );
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置默认的天气
        load("定州");


        editText=(EditText)findViewById(R.id.city_name);
        textView1= (TextView) findViewById(R.id.city);
        textView2= (TextView) findViewById(R.id.now_temperature);
        textView3=(TextView) findViewById(R.id.today_weather);
        textView4=(TextView) findViewById(R.id.dressing_advice);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weather_information.clear();
                load(editText.getText().toString());
            }
        });

        srl1 = (SmartRefreshLayout)findViewById(R.id.srl1);
        srl1.setReboundDuration(2000);

        weather= (ListView) findViewById(R.id.lv_weather);
        weatherAdapter=new WeatherAdapter(getApplicationContext(),weather_information);
        weather.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();

        srl1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl1.finishRefresh();
//                Toast.makeText(getApplicationContext(), "刷新完成", Toast.LENGTH_SHORT).show();
            }
        });
        srl1.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(weather_information.size() > 6){
                    srl1.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getApplicationContext(),
                            "最多显示最近7天天气数据",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loadMoreData();
                    srl1.finishLoadMore();
                    Toast.makeText(getApplicationContext(),
                            "加载6条天气数据",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void load(final String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=httpLogin.JasonAccpt2(city);
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler.sendMessage(message);
            }
        }).start();
    }
    private void loadnew(final String city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLogin httpLogin=new HttpLogin();
                String result=httpLogin.JasonAccpt2(city);
                Bundle bundle=new Bundle();
                bundle.putString("result1",result);
                Message message=new Message();
                message.setData(bundle);
                message.what=3;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void refreshData(){
        weather_information.clear();
        SharedPreferences preferences=getSharedPreferences("cityInfo",Context.MODE_PRIVATE);
        String city=preferences.getString("city","NULL");
//        Toast.makeText(getApplication(),city,Toast.LENGTH_LONG).show();
        if(city.equals("NULL")){
//            Toast.makeText(getApplication(),"请打开定位进行刷新",Toast.LENGTH_LONG).show();
            load("石家庄");
        }else {
            load(city);
        }

        weatherAdapter.notifyDataSetChanged();
    }
    public void loadMoreData(){
        SharedPreferences preferences=getSharedPreferences("cityInfo",Context.MODE_PRIVATE);
        String city=preferences.getString("city","NULL");
//        Toast.makeText(getApplication(),city,Toast.LENGTH_LONG).show();
        if(city.equals("NULL")){
//            Toast.makeText(getApplication(),"请打开定位进行刷新",Toast.LENGTH_LONG).show();
            load("石家庄");
        }else {
            load(city);
        }
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
