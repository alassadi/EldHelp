package com.company.eldhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.company.eldhelp.Interface.MedicineOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MedicineActivity extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    private List<Medicine>medicines=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        //Database connection

        Medicine fatih=new Medicine("alvedon","20:30");
        medicines.add(fatih);
        Medicine abood=new Medicine("Panadol","08:00");
        medicines.add(abood);



        //recyclerView

        recyclerView=findViewById(R.id.medicine_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(MedicineActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new MedicineViewAdapter(this, medicines, new MedicineOnClickListener() {
            @Override
            public void onClick(View v, int position) {

                //on click lister for recylerView
                Toast.makeText(getApplicationContext(),"Test Onclick",Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
