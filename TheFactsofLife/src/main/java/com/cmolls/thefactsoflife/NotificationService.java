package com.cmolls.thefactsoflife;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Marilyn on 10/5/13.
 */
public class NotificationService extends IntentService {
    private static final String NAME = "NotificationService";
    public static final String ACTION_NOTIFY = "com.cmolls.thefactsoflife.ACTION_NOTIFY";
    public NotificationService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(ACTION_NOTIFY))
        {
            Toast.makeText(this, "Notify", Toast.LENGTH_SHORT).show();
        }

    }
}
