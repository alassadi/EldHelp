package com.company.eldhelp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

    private static final String EXTRA_OPEN_NAVIGATION = "com.company.eldhelp";
    private String mString;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mString = getIntent().getStringExtra(EXTRA_OPEN_NAVIGATION);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Database connection
        addButton = findViewById(R.id.button_addMedicine);
        sqliteHelper = new Database(this);
        ArrayList<Medicine> medicines = sqliteHelper.getAllElements();

        //recyclerView
        recyclerView = findViewById(R.id.medicine_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MedicineActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MedicineViewAdapter(this, medicines, new MedicineOnClickListener() {
            @Override
            public void onClick(View v, int position) {

                //on click lister for recylerView
                Toast.makeText(getApplicationContext(), "Test Onclick", Toast.LENGTH_LONG).show();
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
                        medicineTime.setText(selectedHour + ":" + selectedMinute);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_main){
            Intent intent1 = new Intent(MedicineActivity.this, MainActivity.class);
            MedicineActivity.this.startActivity(intent1);
        }
        else if (id == R.id.nav_reminder){
            Intent intent1 = new Intent(MedicineActivity.this,MedicineActivity.class);
            MedicineActivity.this.startActivity(intent1);
        }

        return false;
    }
}
