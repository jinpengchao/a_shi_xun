package h.jpc.vhome.parents.fragment.alarm;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class AlarmService extends Service {
    private boolean flag;
    private Calendar calendar;
    private int num = 0;
    public static int is = 0;
    private MyBinder binder = new MyBinder();
    public AlarmService() {
        Log.i("lww", "Service构造方法");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("lww", "Service的onBind方法");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("lww", "Service的onCreate方法+++");
        //启动线程实现每隔1s计1个数的功能
        new Thread(){
            @Override
            public void run() {
                while(!flag){
                    try {
                        calendar = Calendar.getInstance();
                        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        final int second = calendar.get(Calendar.SECOND);
                        //以下数据皆来自数据库
                        int[] h = new int[5];h[0]=16;h[1]=15;h[2]=15;h[3]=15;h[4]=16;
                        int[] min = new int[5];min[0]=21;min[1]=18;min[2]=48;min[3]=49;min[4]=18;
                        for (is = 0; is < 5; is++) {
                            if (hour == h[is] && minute == min[is]) {
                                Intent i = new Intent();
                                i.setClass(AlarmService.this, AlarmAlert.class);
                                startActivity(i);
                                Log.e("alarm：alert", "ohYeah！" + is);
                                break;
                            }
                        }
                        //设置免打扰时间
                        if(hour >= 22  && minute >= 00){
                            Log.e("alarm：sleep","您要休息了。我在这给您道句晚安！");
                            break;
                        }
                        Log.e("alarm：wait","请再等等吧！！！！！");
                        Thread.sleep(60000-second*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("lww", "Service的onStartCommand方法");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = true;
        Log.i("lww", "Service的onDestroy方法");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("lww", "Service的onUnbind方法");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("lww", "Service的onRebind方法");
    }
    class MyBinder extends Binder{
        public int getCount(){
            return num;
        }
    }
}
