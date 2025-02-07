package com.example.catlib_0612;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class LongRunningService extends Service {
    private String TAG = "LongRunningService";
    //建立Calendar 物件
    private Calendar calendar;
    //存取目前時間
    private long currentSystemTime;
    //存取設定時間
    private long settime;
    //取得日、時、分三種時間輸入
    private String year,month;
    private String day;
    private String hour;
    private String min;
    private int flag;
    private String name;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        month = intent.getStringExtra("month");
        day = intent.getStringExtra("day");
        //name = intent.getStringExtra("name");
        //Log.d(TAG, "onStartCommand: name = "+name);

        Log.d(TAG, "onStartCommand: System.currentTimeMillis(); = "+System.currentTimeMillis());
        /*Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR,2024);
        c1.set(Calendar.MONTH, Integer.parseInt(month));
        c1.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
        c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        c1.set(Calendar.MINUTE, Integer.parseInt(min));
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        long triggerAtTime1 = c1.getTimeInMillis();
        Log.d(TAG, "onStartCommand: triggerAtTime1 = "+triggerAtTime1);
        long current =  SystemClock.elapsedRealtime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d(TAG, "sdf.format(uptimeMillis): " + sdf.format(triggerAtTime1));*/



        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //读者可以修改此处的Minutes从而改变提醒间隔时间
        //此处是设置每隔90分钟启动一次
        //这是90分钟的毫秒数
        //int Minutes = 90*60*1000;
        int Minutes = 5*1000;
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + Minutes;
        Log.d(TAG, "onStartCommand: triggerAtTime = "+triggerAtTime);
        //此处设置开启AlarmReceiver这个Service
        Intent i = new Intent(this, AlarmReceiver.class);
        //PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
        //manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        currentTime();
        //設定定時
        setTime(calendar);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        //manager.set(AlarmManager.RTC_WAKEUP, triggerAtTime1, pi);
        manager.set(AlarmManager.RTC_WAKEUP, settime, pi);

        //showtime();
        //manager.set(AlarmManager.RTC_WAKEUP, triggerAtTime1, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //在Service结束后关闭AlarmManager
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        manager.cancel(pi);

    }

    private void currentTime() {
        //        calendar實例化，取得預設時間、預設時區
        calendar = Calendar.getInstance();

        //        設定系統目前時間、目前時區(GMT+8)
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //        獲得系統目前時間
        currentSystemTime=System.currentTimeMillis();
        Log.d(TAG, "currentTime: currentSystemTime = "+currentSystemTime);
    }

    //        設定定時
    private void setTime(Calendar calendar) {
        calendar.set(Calendar.MONTH,Integer.parseInt(month));
        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //獲得定時時間
        settime = calendar.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d(TAG, "setTime: 1 settime = "+sdf.format(settime));
        //若定時時間(日、時、分)比目前小自動設定為下個月的時間(日、時、分)
        if (currentSystemTime > settime) {
            //            增加一個月
            calendar.add(Calendar.YEAR, 1);

            //        重新獲得定時時間
            settime = calendar.getTimeInMillis();
        }
        Log.d(TAG, "setTime: 2 settime = "+sdf.format(settime));
    }

    //顯示已完成設定的時間
    private void showtime() {
        String text = "";
        text =(calendar.get(Calendar.MONTH)+1)+"月"
                +calendar.get(Calendar.DAY_OF_MONTH)+"日\n"
                +calendar.get(Calendar.HOUR_OF_DAY)+":"
                + calendar.get(Calendar.MINUTE);

        Log.d(TAG, "showtime: text = "+text);
        //Toast.makeText(this,"設定成功\n" + "設定時間為\n"+text,Toast.LENGTH_LONG).show();
    }
}
