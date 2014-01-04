package com.cmolls.thefactsoflife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class TodaysFact extends Activity {

    private int factNumber;
    private String[] listOfFacts;
    private TextView fact;
    private SharedPreferences sharedPreferences;

    //todo: prevent incrementation on rotation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        startService(intent);
        PendingIntent pi = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_NO_CREATE);
        if (pi == null)
        {
            pi = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.setRepeating(AlarmManager.RTC, date.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
        }


        this.setContentView(R.layout.activity_todaysfact);

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
        if(factNumber >= listOfFacts.length) factNumber = 0;
        fact.setText(listOfFacts[factNumber]);
        factNumber = factNumber + 1;
        //chose to use apply, but commit could be more appropriate
        sharedPreferences.edit().putInt("factNumber", factNumber).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todays_fact, menu);
        return true;
    }
    
}
