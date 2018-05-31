package com.company.eldhelp;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Calendar cal = Calendar.getInstance();
        Intent intent2 = getIntent();

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();

        cal.set(Integer.parseInt(intent2.getStringExtra("year")),
                Integer.parseInt(intent2.getStringExtra("month")),
                Integer.parseInt(intent2.getStringExtra("day")),
                Integer.parseInt(intent2.getStringExtra("hour")),
                Integer.parseInt(intent2.getStringExtra("minute")));

        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // cal.add(Calendar.SECOND, 5);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Your Event is added!", Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(AlarmActivity.this, EventActivity.class);
        startActivity(intent1);

    }
}
