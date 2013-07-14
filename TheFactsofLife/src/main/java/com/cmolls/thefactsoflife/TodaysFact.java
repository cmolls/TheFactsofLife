package com.cmolls.thefactsoflife;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TodaysFact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaysfact);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todays_fact, menu);
        return true;
    }
    
}
