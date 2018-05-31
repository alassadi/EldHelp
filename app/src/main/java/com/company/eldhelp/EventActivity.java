package com.company.eldhelp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.CalendarContract;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.company.eldhelp.Interface.MedicineOnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EventActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    Database sqliteHelper;
    Button addButton;
    LayoutInflater inflater;
    View alertLayout;

    EditText eventName;
    EditText eventTime;
    EditText eventDate;
    Calendar myCalendar;

    TextToSpeech textToSpeech;

    public int getLayoutResource() {
        return R.layout.activity_event;
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.layout_custom_dialog2, null);
        myCalendar = Calendar.getInstance();
        eventName = alertLayout.findViewById(R.id.event_name);
        eventTime = alertLayout.findViewById(R.id.event_time);
        eventDate = alertLayout.findViewById(R.id.event_date);

        addButton = findViewById(R.id.button_addEvent);


        //Text to speech
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.setSpeechRate(0.5f);
                }

            }
        });



        //Database connection
        sqliteHelper = new Database(this);
        final ArrayList<Event> events = sqliteHelper.getAllEvents();

        //recyclerView
        recyclerView = findViewById(R.id.event_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(EventActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EventViewAdapter(this, events, new MedicineOnClickListener() {
            @Override
            public void onClick(View v, int position) {

                //on click lister for recylerView
                //text to speech onclick
                String toSpeak=events.get(position).getName()+" "+"         on"+" "+events.get(position).getTime()+"        at"+events.get(position).getDate();
                textToSpeech.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);

            }
        });

        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEventDialog();

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eventDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void showEventDialog() {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventActivity.this,R.style.DialogTheme);
        alertDialog.setCancelable(false);
        alertDialog.setView(alertLayout);



        eventTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventActivity.this,R.style.TimeTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 && selectedMinute < 10) {
                            eventTime.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                        } else if (selectedHour < 10) {
                            eventTime.setText("0" + selectedHour + ":" + selectedMinute);
                        } else if (selectedMinute < 10) {
                            eventTime.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            eventTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                };

                eventDate.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(EventActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = eventName.getText().toString();
                String time = eventTime.getText().toString();
                String date = eventDate.getText().toString();

                if (eventName.getText().length() > 0 && eventTime.getText().length() > 0 && eventDate.getText().length() > 0) {
                    Event event = new Event(name, time, date);

                    //set notification here 2 hours before the event
                    String hour = String.valueOf(time.charAt(0))+String.valueOf(time.charAt(1));
                    String min = String.valueOf(time.charAt(3))+String.valueOf(time.charAt(4));

                    int alarmValue = Integer.parseInt(hour);
                    if (alarmValue<2){
                        alarmValue=10+alarmValue;
                    } else {
                        alarmValue=alarmValue-2;
                    }
                    String hourToSet=String.valueOf(alarmValue);

                    setEventNotification(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH),
                            Integer.parseInt(hourToSet),Integer.parseInt(min));


                    sqliteHelper.addEvent(event);
                    Toast.makeText(getApplicationContext(), "Your Event is added!", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();

                    //Refresh the page
                    Intent intent=new Intent(EventActivity.this,EventActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Your Event is Not added!", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        adapter.notifyDataSetChanged();
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent intent1 = new Intent(EventActivity.this, MainActivity.class);
            EventActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_reminder) {
            Intent intent1 = new Intent(EventActivity.this, MedicineActivity.class);
            EventActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_event) {
            Intent intent1 = new Intent(EventActivity.this, EventActivity.class);
            EventActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_contact) {
            Intent intent1 = new Intent(EventActivity.this, ContactActivity.class);
            EventActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_map) {
            Intent intent1 = new Intent(EventActivity.this, MapsActivity.class);
            EventActivity.this.startActivity(intent1);
        }
        return false;
    }

    //Add calender event

    public void setEventNotification(int year, int month, int day, int hour, int min){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }


}