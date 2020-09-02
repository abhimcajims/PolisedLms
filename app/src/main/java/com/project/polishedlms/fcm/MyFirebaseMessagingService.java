package com.project.polishedlms.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.polishedlms.activity.DashboardActivity;
import com.project.polishedlms.R;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {

            String screenOpenCheck = remoteMessage.getData().get("notification_type");

            String pushMessages = remoteMessage.getData().get("body");
            String pushTitle = remoteMessage.getData().get("title");

            Log.d("", "");
            showNotifications(pushMessages, pushTitle, screenOpenCheck);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        //notificationUtils = new NotificationUtils(context);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        //notificationUtils = new NotificationUtils(context);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    private static final int REQUEST_CODE = 123456;
    private int NOTIFICATION_ID = 24;


    private void showNotifications(String pushMessagess, String pushTitle, String screenOpenCheck) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "com.project";
        String channelName = "polishedlms";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent;
        intent = new Intent(this, DashboardActivity.class);

        if (screenOpenCheck.contains("studymaterialvideo"))
            intent.putExtra("title", "studymaterialvideo");
        else if (screenOpenCheck.contains("onlineclass"))
            intent.putExtra("title", "onlineclass");
        else if (screenOpenCheck.contains("assignquiz"))
            intent.putExtra("title", "assignquiz");
        else
            intent.putExtra("title", "onlineclass");


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new Notification.Builder(this)
                .setContentTitle(pushTitle)
                .setAutoCancel(true)
                //.setContentText(pushMessagess)
                .setContentIntent(pendingIntent)
                .setSound(sound)

                .setSmallIcon(R.drawable.profile_logo)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(pushMessagess))
                .build();


        notificationManager.notify(NOTIFICATION_ID, notification);


    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        new DashboardActivity().sendTokenToServer(s);
        Log.d(TAG, "onNewToken: " + s);
    }


}
