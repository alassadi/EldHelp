package com.company.eldhelp;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
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
import java.util.List;
import java.util.Locale;

public class ContactActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    Database sqliteHelper;
    Button addButton;
    LayoutInflater inflater ;
    View alertLayout ;

    EditText contactName ;
    EditText contactNumber ;

    public int getLayoutResource() {
        return R.layout.activity_contact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.layout_custom_dialog3, null);
        contactName = alertLayout.findViewById(R.id.contact_name);
        contactNumber = alertLayout.findViewById(R.id.contact_number);

        //Database connection
        addButton = findViewById(R.id.button_addContact);
        sqliteHelper = new Database(this);
        final ArrayList<Contact> contacts = sqliteHelper.getAllContacts();

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


                String number = contacts.get(position).getNumber();                            // random number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + number));                       //connect intent with phone number
                startActivity(i);


            }
        });
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContacts();
            }
        });
    }


    private void showContacts() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContactActivity.this,R.style.DialogTheme);
        alertDialog.setCancelable(false);
        alertDialog.setView(alertLayout);

        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = contactName.getText().toString();
                String number = contactNumber.getText().toString();

                if (contactName.getText().length() > 0 && contactNumber.getText().length() > 0 ) {
                    Contact contact = new Contact(name,number);
                    sqliteHelper.addContact(contact);
                    Toast.makeText(getApplicationContext(), "Your Contact is added!", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();

                    //Refresh the page
                    Intent intent=new Intent(ContactActivity.this,ContactActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Your Contact is Not added!", Toast.LENGTH_LONG).show();
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
            Intent intent1 = new Intent(ContactActivity.this, MainActivity.class);
            ContactActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_reminder) {
            Intent intent1 = new Intent(ContactActivity.this, MedicineActivity.class);
            ContactActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_event) {
            Intent intent1 = new Intent(ContactActivity.this, EventActivity.class);
            ContactActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_contact) {
            Intent intent1 = new Intent(ContactActivity.this, ContactActivity.class);
            ContactActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_map) {
            Intent intent1 = new Intent(ContactActivity.this, MapsActivity.class);
            ContactActivity.this.startActivity(intent1);
        }
        return false;
    }

}

