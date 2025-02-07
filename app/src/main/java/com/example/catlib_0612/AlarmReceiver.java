package com.example.catlib_0612;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private String TAG = "AlarmReceiver";
    private String CHANNEL_ID = "Coder";
    private String name;
    private SharedPreferences getPrefs;

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        getPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        name = getPrefs.getString("catName", "");
        Log.d(TAG, "onReceive: name = "+name);
        //设置通知内容并在onReceive()这个函数执行时开启
        /*NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new Notification(R.drawable.ic_launcher_background,"用电脑时间过长了！白痴！"
                ,System.currentTimeMillis());
        notification.setLatestEventInfo(context, "快去休息！！！",
                "一定保护眼睛,不然遗传给孩子，老婆跟别人跑啊。", null);
        notification.defaults = Notification.DEFAULT_ALL;
        manager.notify(1, notification);*/
        /**建置通知欄位的內容*/
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.birthdya_black)
                .setContentTitle("哈囉！"+name)
                .setContentText("生日快樂\uD83C\uDF82~")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        /**發出通知*/
        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,builder.build());

        //再次开启LongRunningService这个服务，从而可以
        //Intent i = new Intent(context, LongRunningService.class);
        //context.startService(i);
    }

}
