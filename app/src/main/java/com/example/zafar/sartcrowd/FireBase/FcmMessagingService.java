package com.example.zafar.sartcrowd.FireBase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.example.zafar.sartcrowd.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

// To Receive Push Notification When APP is Running/Foreground
public class FcmMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()>0) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String action = remoteMessage.getData().get("click_action");

            Map<String, String>  data = remoteMessage.getData();
            Intent intent = new Intent(action);
            intent.putExtra("title",title);
            intent.putExtra("body",body);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
            sendNotification(data);
        }
    }

    private void sendNotification(Map<String, String> serverData) {

     //   Toast.makeText(this, "Notification of SCrowd", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(serverData.get("click_action"));
        String title = serverData.get("title");
        String body = serverData.get("body");
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        intent.putExtra("order" , serverData.get("order"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ok)
                .setContentTitle(serverData.get("title"))
                .setContentText(serverData.get("body"))
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }
}
