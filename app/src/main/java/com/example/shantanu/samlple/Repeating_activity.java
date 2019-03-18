package com.example.shantanu.samlple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shantanu on 26/9/18.
 */

public class Repeating_activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_daily);
        Intent intenter = new Intent(Repeating_activity.this, MainActivity.class);
        startActivity(intenter);
    }
}
