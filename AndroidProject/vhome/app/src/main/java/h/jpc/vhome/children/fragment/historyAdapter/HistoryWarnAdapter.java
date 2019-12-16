package h.jpc.vhome.children.fragment.historyAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
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
            holder.todetail = convertView.findViewById(R.id.todetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AlarmBean alarmBean = alarmBeans.get(position);
        holder.history_content.setText(alarmBean.getContent());
        holder.history_time.setText(alarmBean.getAlarmTime());
        holder.history_receiver.setText(alarmBean.getReceivePersonId());
        holder.history_sender.setText(alarmBean.getSendPersonId());
//        holder.todetail.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                String content = alarmBeanList.get(arg2).getContent();
//                new AlertDialog.Builder(getContext())
//                        .setTitle("是否取消发送？")
//                        .setItems(R.array.choose,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        String[] PK = getResources()
//                                                .getStringArray(
//                                                        R.array.choose);
//                                        if (PK[which].equals("取消发送")) {
//                                            if (null!=alarmBeanList) {
//                                                alarmBeanList.remove(arg2);
//                                                Log.e("content",content+"");
//                                                deleteSendedAlarm(content);
//                                                historyWarnData(warnInfo);
//                                                if (alarmBeanList.size()==0){
//                                                    warnInfo.setText("暂时你还没有发送提醒哦~\r\n快去为爱的人发送一条提醒吧！");
//                                                }
//                                                historyWarnAdapter.notifyDataSetChanged();
//                                            }
//                                            Toast.makeText(getContext(),"取消成功啦~", Toast.LENGTH_LONG).show();
//                                        }
//                                        if (PK[which].equals("关闭")) {
//                                            String service = "showMysended";
//                                            getMySendedAlarm(service);
//                                        }
//                                    }
//                                }).show();
//                return true;
//            }
//        });
        return convertView;
    }

    final static class ViewHolder{
        private RelativeLayout todetail;
        private TextView history_content;
        private TextView history_time;
        private TextView history_receiver;
        private TextView history_sender;
    }
}
