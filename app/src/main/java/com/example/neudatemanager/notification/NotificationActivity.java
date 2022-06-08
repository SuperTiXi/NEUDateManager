package com.example.neudatemanager.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.neudatemanager.R;
import com.example.neudatemanager.entity.Schedule;
import com.example.neudatemanager.ui.activity.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.text.ParseException;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //获取Notification对象并设置跳转
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this,0,intent,0);

        Schedule schedule = new Schedule();
        List<Schedule> scheduleList = schedule.getTodayScheduleByNotification(null,NotificationActivity.this);
        for(int i = 1;i<scheduleList.size();i++){
            Schedule s = scheduleList.get(i);
            Notification notification = null;
            try {
                long five = s.getFiveMinBeforeStart();
                notification = new NotificationCompat.Builder(NotificationActivity.this)
                        .setContentTitle("今日日程提醒")
                        .setContentText("日程"+"'"+s.getName()+"'"+"将于"+s.getStartTime()+"开始,并且在"+s.getEndTime()+"结束")
                        .setWhen(five)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)  //设置跳转
                        .setAutoCancel(true)  //设置自动取消
                        .setPriority(2)
                        .build();
                notification.flags = Notification.FLAG_ONGOING_EVENT;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            manager.notify(i,notification);
        }
    }
}