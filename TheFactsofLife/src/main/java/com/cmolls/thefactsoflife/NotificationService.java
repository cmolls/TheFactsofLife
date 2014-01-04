package com.cmolls.thefactsoflife;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Marilyn on 10/5/13.
 */
public class NotificationService extends IntentService {
    private static final String NAME = "NotificationService";
    public static final String ACTION_NOTIFY = "com.cmolls.thefactsoflife.ACTION_NOTIFY";
    private static final int mId = 1;

    public NotificationService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(ACTION_NOTIFY))
        {
            makeNotification();
            Log.d("cmolls", "Notify");
        }

    }

    private void makeNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_action_about)
                        .setContentTitle("New fact")
                        .setContentText("Tap for fact")
                        .setTicker("New fact")
                ;

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, TodaysFact.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(TodaysFact.class);
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
}
