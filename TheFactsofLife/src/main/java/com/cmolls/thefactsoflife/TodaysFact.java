package com.cmolls.thefactsoflife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.util.Calendar;

public class TodaysFact extends Activity {

    private int factNumber;
    private String[] listOfFacts;
    private TextView fact;
    private SharedPreferences sharedPreferences;
    private ShareActionProvider mShareActionProvider;

    //todo: prevent incrementation on rotation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_todaysfact);
        sharedPreferences = getSharedPreferences("TheFactsOfLife", MODE_PRIVATE);
        factNumber = sharedPreferences.getInt("factNumber", 0);

        Intent intent = new Intent(NotificationService.ACTION_NOTIFY);
        Calendar date=Calendar.getInstance();
        if (date.get(Calendar.HOUR_OF_DAY) >= 17) {
            date.add(Calendar.DATE, 1);
        }
        date.set(Calendar.HOUR_OF_DAY, 17);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 00);
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        /////TODO Remove after testing
        //startService(intent);
        PendingIntent pi = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_NO_CREATE);
        if (pi == null)
        {
            pi = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.setRepeating(AlarmManager.RTC, date.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
        }



        listOfFacts = getResources().getStringArray(R.array.factlist);
        fact = (TextView) findViewById(R.id.tv_list_of_facts);
        nextTip();
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextTip();
            }
        });
    }

    private void nextTip(){
        factNumber = factNumber + 1;
        if(factNumber >= listOfFacts.length) factNumber = 0;

        String todaysFact = listOfFacts[factNumber];
        fact.setText(todaysFact);

        updateShareIntent(todaysFact);

        //chose to use apply, but commit could be more appropriate
        sharedPreferences.edit().putInt("factNumber", factNumber).apply();
    }

    private void updateShareIntent(String todaysFact) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Your friend wants to share a fact with you.\n\n" + todaysFact + "\n\nGet more facts at www.cmolls.com");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Your friend has shared a fact with you.");
        shareIntent.setType("text/plain");
        setShareIntent(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todays_fact, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        updateShareIntent(listOfFacts[factNumber]);
        return true;
    }
    private void setShareIntent (Intent shareIntent)  {
        if (mShareActionProvider != null)  {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
