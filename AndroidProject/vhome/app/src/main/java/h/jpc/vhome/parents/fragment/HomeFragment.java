package h.jpc.vhome.parents.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.alarm.AlarmActivity;
import h.jpc.vhome.parents.fragment.calendar.LunarCalendar;
import h.jpc.vhome.parents.fragment.tools.AllToolsActivity;


public class HomeFragment extends Fragment {
    private TextView yang_li_calendar;
    private TextView yin_li_calendar;
    private Button alarm;
    private ImageView news;
    private ImageView weather;
    private Button tools;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_parent_home,null);
        /*
         * 功能：设置时间
         * 作者：靳朋朝
         * 完成时间：2019年11月25日19:14:34
         */
        yang_li_calendar = view.findViewById(R.id.yang_li_calendar);
        yin_li_calendar = view.findViewById(R.id.yin_li_calendar);
        Date date = new Date();
        yang_li_calendar.setText(getDate(date)[0]);
        yin_li_calendar.setText(getDate(date)[1]);
        /*
         *  功能：闹钟跳转
         *  作者：靳朋朝
         *  时间：2019年11月26日18:41:11
         */
        alarm = view.findViewById(R.id.chizixin);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });
        /*
         *  功能：小工具跳转
         *  作者：靳朋朝
         *  时间：2019年11月26日09:05:26
         */
        tools = view.findViewById(R.id.xiaogongju);
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),AllToolsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
     /*
     * 功能：顶部日期
     * 作者：靳朋朝
     * 完成时间：2019年11月25日19:11:27
     */
    public String[] getDate(Date date) {
        String[] d = new String[2];
        LunarCalendar lunarCalendar = new LunarCalendar();//实例化时间类
        String animal = lunarCalendar.animalsYear();//获取阴历日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");//修改日期格式
        String day = sdf.format( date );
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        d[0] =  day+","+weekDays[w];
        d[1] =  "【"+animal+"】农历"+lunarCalendar.toString();
        return d;
    }
}
