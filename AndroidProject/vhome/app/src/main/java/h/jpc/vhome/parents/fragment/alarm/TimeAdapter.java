package h.jpc.vhome.parents.fragment.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import h.jpc.vhome.R;

import static android.content.Context.ALARM_SERVICE;
import static h.jpc.vhome.parents.fragment.alarm.Clock.clock_close;
import static h.jpc.vhome.parents.fragment.alarm.Clock.clock_open;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    List<Clock> list;
    LayoutInflater layoutInflater;
    Context context;
    Calendar calendar = Calendar.getInstance();
    public static int pos;
    public static int po;
    private ClockDetail clockDetail;
    public TimeAdapter(List<Clock> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_parent_alarm, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final TimeAdapter.ViewHolder viewHolder, final int i) {
        final Clock clock = list.get(i);
        pos = i;

        Log.e("i=======",i+"  "+clock.getClockType());
        if (clock.getClockType() == clock_open){
            viewHolder.aSwitch.setChecked(true);
            viewHolder.hour.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.minute.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.net.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.from.setTextColor(context.getResources().getColor(R.color.notChoseColor));
            viewHolder.content.setTextColor(context.getResources().getColor(R.color.notChoseColor));
        }else if (clock.getClockType() == clock_close){
            viewHolder.aSwitch.setChecked(false);
            viewHolder.hour.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.minute.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.from.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.net.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.content.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        viewHolder.hour.setText(clock.getHour()+"");
        viewHolder.minute.setText(clock.getMinute()+"");
        viewHolder.sendPersonId.setText(clock.getSendPersonId()+"");
        viewHolder.minute.setText(clock.getMinute()+"");
        viewHolder.content.setText(clock.getContent());
        viewHolder.todetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("toDetail",clock.getClockType()+"");
                Intent intent = new Intent(context, ClockDetail.class);
                intent.putExtra("position", i);
                context.startActivity(intent);
            }
        });

        viewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clock.setClockType(clock_open);
                    clockDetail = new ClockDetail();
                    clockDetail.changeAlarm(context,"","","",clock_open);
                    Toast.makeText(context, "开启闹钟", Toast.LENGTH_SHORT).show();
                    viewHolder.hour.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.minute.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.net.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.from.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                    viewHolder.content.setTextColor(context.getResources().getColor(R.color.notChoseColor));
                } else if (!isChecked){
                    clock.setClockType(clock_close);
                    clockDetail = new ClockDetail();
                    clockDetail.changeAlarm(context,"","","",clock_close);
                    Toast.makeText(context, "关闭闹钟", Toast.LENGTH_SHORT).show();
                    viewHolder.hour.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.minute.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.net.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.sendPersonId.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.from.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.content.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hour;
        TextView minute;
        TextView content;
        TextView sendPersonId;
        TextView from;
        TextView net;
        Switch aSwitch;
        LinearLayout todetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            minute = itemView.findViewById(R.id.minute);
            net = itemView.findViewById(R.id.net);
            sendPersonId = itemView.findViewById(R.id.send_person);
            from = itemView.findViewById(R.id.from);
            content = itemView.findViewById(R.id.content_item);
            aSwitch = itemView.findViewById(R.id.switch_control);
            todetail = itemView.findViewById(R.id.todetail);
        }
    }
}
