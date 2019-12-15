package h.jpc.vhome.children.fragment.historyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import h.jpc.vhome.R;

public class HistoryWarnAdapter extends BaseAdapter {
    private List<AlarmBean> alarmBeans;
    private Context context;
    private int itemLayout;

    public HistoryWarnAdapter(Context context, List<AlarmBean> alarmBeans, int itemLayout) {
        this.alarmBeans = alarmBeans;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return alarmBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayout, null);
            holder.history_content = convertView.findViewById(R.id.history_content);
            holder.history_time = convertView.findViewById(R.id.history_time);
            holder.history_receiver = convertView.findViewById(R.id.history_receiver);
            holder.history_sender = convertView.findViewById(R.id.history_sender);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AlarmBean alarmBean = alarmBeans.get(position);
        holder.history_content.setText(alarmBean.getContent());
        holder.history_time.setText(alarmBean.getAlarmTime());
        holder.history_receiver.setText(alarmBean.getReceivePersonId());
        holder.history_sender.setText(alarmBean.getSendPersonId());
        return convertView;
    }

    final static class ViewHolder{
        private TextView history_content;
        private TextView history_time;
        private TextView history_receiver;
        private TextView history_sender;
    }
}
