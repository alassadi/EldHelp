package com.company.eldhelp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.company.eldhelp.Interface.MedicineOnClickListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.DialogInterface;

public class MedicineActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    Database sqliteHelper;
    Button addButton;


    private List<Medicine> medicines = new ArrayList<>();

    public int getLayoutResource() {
        return R.layout.activty_medicine;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Database connection
        addButton = findViewById(R.id.button_addMedicine);
        sqliteHelper = new Database(this);
        ArrayList<Medicine> medicines = sqliteHelper.getAllElements();

        //recyclerView
        recyclerView = findViewById(R.id.medicine_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MedicineActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //create alarm


        for (int i=0; i<medicines.size(); i++){
            String title=medicines.get(i).getName();
            String time=String.valueOf(medicines.get(i).getTime());

            String hour1= String.valueOf(time.charAt(0));
            String hour2=String.valueOf(time.charAt(1));

            String min1=String.valueOf(time.charAt(3));
            String min2=String.valueOf(time.charAt(4));

            String our=hour1+hour2;
            String min=min1+min2;

            createAlarm(title,Integer.parseInt(our),Integer.parseInt(min),true,true);

        }

        adapter = new MedicineViewAdapter(this, medicines, new MedicineOnClickListener() {
            @Override
            public void onClick(View v, int position) {

                //on click lister for recylerView
                Toast.makeText(getApplicationContext(), "Test Onclick", Toast.LENGTH_LONG).show();
                //showNotification("FATIH","DENEME");

            }
        });
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();

            }
        });
    }

    private void showInputDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText medicineName = alertLayout.findViewById(R.id.et_username);
        final EditText medicineTime = alertLayout.findViewById(R.id.et_email);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicineActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setView(alertLayout);

        medicineTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MedicineActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 && selectedMinute < 10) {
                            medicineTime.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                        } else if (selectedHour < 10) {
                            medicineTime.setText("0" + selectedHour + ":" + selectedMinute);
                        } else if (selectedMinute < 10) {
                            medicineTime.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            medicineTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = medicineName.getText().toString();
                String time = medicineTime.getText().toString();

                if (medicineName.getText().length() > 0 && medicineTime.getText().length() > 0) {
                    Medicine medicine = new Medicine(name, time);
                    sqliteHelper.addMedicine(medicine);
                    Toast.makeText(getApplicationContext(), "Your Medicine is added!", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Your Medicine is Not added!", Toast.LENGTH_LONG).show();
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

    //create alarm
    public void createAlarm(String message, int hour, int minutes, boolean vibrate, boolean skipui){

        Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        //intent.putExtra(AlarmClock.EXTRA_DAYS,days);
        intent.putExtra(AlarmClock.EXTRA_VIBRATE,vibrate);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, skipui);

        startActivity(intent);
    }

    //create notification
    void showNotification(String title, String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");
                builder.setSmallIcon(R.mipmap.ic_launcher); // notification icon
                builder.setContentTitle(title); // title for notification
                builder.setContentText(content);// message for notification
                builder.setPriority(Notification.PRIORITY_MAX);
                builder.setDefaults(Notification.DEFAULT_ALL);//
                //.setSound(mysound) // set alarm sound for notification
                //.setAutoCancel(false); // clear notification after click

        Intent intent = new Intent(getApplicationContext(), MedicineActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //set time to notification

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1, builder.build());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent intent1 = new Intent(MedicineActivity.this, MainActivity.class);
            MedicineActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_reminder) {
            Intent intent1 = new Intent(MedicineActivity.this, MedicineActivity.class);
            MedicineActivity.this.startActivity(intent1);
        }

        return false;
    }
}
