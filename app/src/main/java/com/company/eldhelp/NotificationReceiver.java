package com.company.eldhelp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by fatih on 2018-05-08.
 */

public class NotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        //create notification here
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher); // notification icon
        builder.setContentTitle("EVENT"); // title for notification
        builder.setContentText("Event starts in 2 hours");// message for notification
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setDefaults(Notification.DEFAULT_ALL);//
        //.setSound(mysound) // set alarm sound for notification
        builder.setAutoCancel(false); // clear notification after click

        Intent intent1 = new Intent(context, EventActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //set time to notification

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1, builder.build());


    }
}
