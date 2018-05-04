package com.company.eldhelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private static final String EXTRA_OPEN_NAVIGATION = "com.company.eldhelp";
    private String mString;
    private ImageView mSmileyGood;
    private ImageView mSmileyOkay;
    private ImageView mSmileyBad;
    private ImageView mCall1;
    private ImageView mCall2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mSmileyGood = findViewById(R.id.smiley1);
        mSmileyOkay = findViewById(R.id.smiley2);
        mSmileyBad = findViewById(R.id.smiley3);

        mSmileyGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "That is wonderful!", Toast.LENGTH_LONG).show();
            }
        });

        mSmileyOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please call your doctor!", Toast.LENGTH_LONG).show();
            }
        });

        mSmileyBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please call an emergency contact!", Toast.LENGTH_LONG).show();
            }
        });

        mCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "1-402-935-2050";                            // random number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + number));                       //connect intent with phone number
                startActivity(i);
            }
        });

        mCall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "2-532-949-3072";                            // random number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + number));                       //connect intent with phone number
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
            MainActivity.this.startActivity(intent1);
        } else if (id == R.id.nav_reminder) {
            Intent intent1 = new Intent(MainActivity.this, MedicineActivity.class);
            MainActivity.this.startActivity(intent1);
        }

        return false;
    }
}