package h.jpc.vhome.parents.fragment.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.Switch;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.HomeFragment;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.Clock.clock_close;
import static h.jpc.vhome.parents.fragment.alarm.ClockDetail.position;
public class AlarmAlert extends Activity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this, R.raw.clockmusic2);
        mediaPlayer.start();
        SharedPreferences sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent ii = getIntent();
        int position1 = ii.getIntExtra("position",-1);

        String sendPerson = sharedPreferences.getString("sendperson"+position1,"");
        String content = sharedPreferences.getString("content"+position1,"");
        if(position1 != -1) {
            new AlertDialog.Builder(AlarmAlert.this)
                    .setIcon(R.mipmap.clock)
                    .setTitle("小提醒来自：" + sendPerson)
                    .setCancelable(false)
                    .setMessage(content)
                    .setPositiveButton("知道了！"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlarmAlert.this.finish();
                                    try {
                                        editor.putInt("clocktype" + position1, clock_close);
                                    } catch (IndexOutOfBoundsException e) {
                                        Log.e("被老子捕获了吧", "你个渣渣" + HomeFragment.size);
                                    } finally {
                                        mediaPlayer.stop();
                                    }
                                }
                            }).show();
        }
    }
}
