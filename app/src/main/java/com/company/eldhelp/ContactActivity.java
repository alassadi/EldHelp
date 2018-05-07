package com.company.eldhelp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class ContactActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    Database sqliteHelper;
    Button addButton;


    private List<Contact> contacts = new ArrayList<>();

    public int getLayoutResource() {
        return R.layout.activity_contact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Database connection
        addButton = findViewById(R.id.button_addContact);
        sqliteHelper = new Database(this);
        ArrayList<Contact> contacts = sqliteHelper.getAllContacts();

        //recyclerView
        recyclerView = findViewById(R.id.contact_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ContactActivity.this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new ContactViewAdapter(this, contacts, new MedicineOnClickListener() {
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
                //showInputDialog();

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent intent1 = new Intent(ContactActivity.this, MainActivity.class);
            ContactActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_reminder) {
            Intent intent1 = new Intent(ContactActivity.this, MedicineActivity.class);
            ContactActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_event) {
            Intent intent1 = new Intent(ContactActivity.this, EventActivity.class);
            ContactActivity.this.startActivity(intent1);
        }

        return false;
    }

}

