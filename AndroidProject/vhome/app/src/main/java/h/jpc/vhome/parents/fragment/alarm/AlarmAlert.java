package h.jpc.vhome.parents.fragment.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.Switch;
import h.jpc.vhome.R;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.ClockDetail.position;
import static h.jpc.vhome.parents.fragment.alarm.AlarmService.is;
public class AlarmAlert extends Activity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this, R.raw.clockmusic2);
        mediaPlayer.start();
        new AlertDialog.Builder(AlarmAlert.this)
//                .setIcon(R.drawable.clock)
                .setTitle("闹钟响了")
                .setCancelable(false)
                .setMessage("时间到了！")
                .setPositiveButton("关掉"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlarmAlert.this.finish();
                                Log.e("position--AlarmAlert",position+"");
                                try {
                                    list.get(position).setClockType(Clock.clock_close);
                                }catch (IndexOutOfBoundsException e) {
                                    Log.e("被老子捕获了","你个渣渣"+is);
//                                    list.get(is).setClockType(Clock.clock_close);//待定！！！！！！！！
                                } finally {
                                    mediaPlayer.stop();
                                }
                            }
                        }).show();
    }
}
