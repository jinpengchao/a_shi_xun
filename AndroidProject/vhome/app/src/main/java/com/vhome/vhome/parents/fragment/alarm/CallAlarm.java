package com.vhome.vhome.parents.fragment.alarm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CallAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,AlarmAlert.class);
        int p = intent.getIntExtra("position",-1);
        Log.e("pppppppppppppppppppp",p+"");
        Bundle bundle = new Bundle();
        bundle.putInt("position",p);
        intent1.putExtras(bundle);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
