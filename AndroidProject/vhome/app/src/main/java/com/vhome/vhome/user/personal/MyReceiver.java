package com.vhome.vhome.user.personal;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.myself.MyNewsActivity;

import androidx.core.app.NotificationCompat;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends JPushMessageReceiver {
    /**
     * 当收到自定义消息时回调
     * @param context
     * @param customMessage
     */
    private NotificationManager manager;
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        //获取收到的消息

        String title = customMessage.title;//消息标题
        String msg = customMessage.message;//消息内容
        String extra = customMessage.extra;//附加消息
        Log.i("lww",
                "自定义消息--" + title
                        + "; message:" + msg
                        + "; extra:" + extra);
        //收到的消息显示在页面上
//        Intent intent = new Intent(
//                context,
//                MainActivity.class);
//        intent.putExtra("msg", msg);
//        intent.putExtra("extra", extra);
//        context.startActivity(intent);
    }

    /**
     * 当打开通知消息时回调
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
//            Intent intent = new Intent(
//                    context,
//                    MyNewsActivity.class);
//            context.startActivity(intent);
//            Log.e("cnmmmmm","cccc");

    }

    /**
     * 当收到通知消息时回调
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        if(notificationMessage.isRichPush){//如果是富媒体消息
            //获取收到的富媒体消息保存的路径
            String webPath = notificationMessage._webPagePath;
            Log.i("lww", "富媒体消息存储路径："
                    + webPath);
        }else{//普通通知消息
            //获取系统提供的通知管理服务
            manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            //判断是否为8.0以上系统，是的话新建一个通道
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                //创建一个通道 一参：id  二参：name 三参：统通知的优先级
                @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("chId", "聊天信息", NotificationManager.IMPORTANCE_MAX);
                manager.createNotificationChannel(channel);//创建
                channel.setVibrationPattern(new long[]{0});//通道来控制震动
                manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);//获取管理类的实例
                Intent intent2=new Intent(context, MyNewsActivity.class);
                //PendingIntent点击通知后跳转，一参：context 二参：一般为0 三参：intent对象 四参：一般为0
                PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent2,0);
                Notification builder = new NotificationCompat.Builder(context, "chId")
                        .setContentTitle("微家官方")//标题"微家官方", "您的反馈已被回复，点击查看", ""
                        .setContentText("您收到了官方的消息！点击查看")//内容
                        .setSmallIcon(R.drawable.em_logo_uidemo)//图片
                        .setContentIntent(pendingIntent)//点击通知跳转
                        .setAutoCancel(true)//完成跳转自动取消通知
                        .build();
                manager.notify(1, builder);//让通知显示出来

            }else {
                manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);//获取管理类的实例
                Intent intent2=new Intent(context, MyNewsActivity.class);
                //PendingIntent点击通知后跳转，一参：context 二参：一般为0 三参：intent对象 四参：一般为0
                PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent2,0);
                Notification builder = new NotificationCompat.Builder(context, "chId")
                        .setContentTitle("欢迎加入微家大家庭！")//标题
                        .setContentText("您收到了官方的消息！点击查看")//内容
                        .setSmallIcon(R.drawable.em_logo_uidemo)//图片
                        .setContentIntent(pendingIntent)//点击通知跳转
                        .setAutoCancel(true)//完成跳转自动取消通知
                        .build();
                manager.notify(1, builder);//让通知显示出来
            }
        }
    }
}
