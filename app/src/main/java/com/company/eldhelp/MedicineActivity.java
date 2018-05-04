package com.company.eldhelp;

import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class MedicineActivity extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    Database sqliteHelper;
    Button addButton;

    private List<Medicine> medicines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_medicine);

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

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}
