package com.techidea.theroywhy.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.techidea.theroywhy.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2017/2/10.
 */

public class NotificationActivity extends Activity {

    private int mId = 1005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_notify)
    void buttonNotify() {
//        systemNotify();
//        systemExtendNotify();
        customNotify();
    }

    private void systemNotify() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.notify_line)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ResultActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private void systemExtendNotify() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.notify_line)
                .setContentTitle("Event tracker")
                .setContentText("Events received");
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
//        ..
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine("event " + i);
        }
// Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle)
                .setNumber(2);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private void pictureExtendNotify() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.notify_line)
                .setContentTitle("Event tracker")
                .setContentText("Events received");

        NotificationCompat.BigPictureStyle bigPictureStyle =
                new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notify_line_200));
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.notify_line_200));
        bigPictureStyle.setBigContentTitle("Big Picture");
        mBuilder.setStyle(bigPictureStyle);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private void customNotify() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//4.0
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_big_notify);
            mBuilder.setContent(remoteViews);

            RemoteViews bigRemoteViews = new RemoteViews(getPackageName(), R.layout.view_notify);
            mBuilder.setCustomBigContentView(bigRemoteViews);
        } else {
            mBuilder.setSmallIcon(R.mipmap.notify_line)
                    .setContentTitle("QQ音乐")
                    .setContentText("See you again");
        }
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mId, mBuilder.build());
    }

}
