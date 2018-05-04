package com.company.eldhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_OPEN_NAVIGATION = "com.company.eldhelp";
    android.support.v7.widget.Toolbar toolbar;
    int drawerLayout = 0;
    int navView = 0;
    int mToolbar= 0;
    private String mString;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

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

    }
    protected abstract int getLayoutResource();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_main){
            Intent intent1 = new Intent(BaseActivity.this, MainActivity.class);
            BaseActivity.this.startActivity(intent1);
        }
        else if (id == R.id.nav_reminder){
            Intent intent1 = new Intent(BaseActivity.this,MedicineActivity.class);
            BaseActivity.this.startActivity(intent1);
        }

        return false;
    }

}
