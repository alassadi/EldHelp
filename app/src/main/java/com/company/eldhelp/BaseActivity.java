package com.company.eldhelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    int drawerLayout = 0;
    int navView = 0;
    int mToolbar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());


    }

    protected abstract int getLayoutResource();


}
