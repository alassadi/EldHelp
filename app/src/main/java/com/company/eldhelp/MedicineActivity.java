package com.company.eldhelp;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.company.eldhelp.Interface.MedicineOnClickListener;
import java.util.ArrayList;
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
        setContentView(R.layout.activity_medicine);

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
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicineActivity.this);
        alertDialog.setTitle("Add Medicine");
        alertDialog.setMessage("Enter Your Medicine: ");

        LinearLayout linearLayout = new LinearLayout(MedicineActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText medicineName = new EditText(MedicineActivity.this);
        medicineName.setHint("Name");
        linearLayout.addView(medicineName);

        final EditText medicineTime = new EditText(MedicineActivity.this);
        medicineTime.setHint("Time");
        linearLayout.addView(medicineTime);


        alertDialog.setView(linearLayout);
        //alerDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Medicine medicine = new Medicine(String.valueOf(medicineName.getText()), String.valueOf(medicineTime.getText()));
                sqliteHelper.addMedicine(medicine);

                Toast.makeText(getApplicationContext(), "Your Medicine is added!", Toast.LENGTH_LONG).show();


                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }
}
