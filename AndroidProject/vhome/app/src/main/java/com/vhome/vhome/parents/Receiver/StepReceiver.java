package com.vhome.vhome.parents.Receiver;

import android.content.Context;
import android.content.Intent;

import com.today.step.lib.BaseClickBroadcast;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.ParentMain;

public class StepReceiver extends BaseClickBroadcast {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        MyApp myApp = (MyApp) context.getApplicationContext();
        if (!myApp.isForeground()) {
            Intent mainIntent = new Intent(context, ParentMain.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        } else {

        }
    }
}
