package com.example.shantanu.samlple;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by shantanu on 26/9/18.
 */

public class Notification_Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent=new Intent(context,Repeating_activity.class);

        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,100,repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Please make sure that you take the Tablet!!")
                .setContentText("Make sure that you don't Forget!")
                .setAutoCancel(true);
            if(intent.getAction().equals("MY_NOTIFICATION_MESSAGE"))
            {
                notificationManager.notify(100,builder.build());
            }
    }
}
