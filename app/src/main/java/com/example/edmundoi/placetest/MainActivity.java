package com.example.edmundoi.placetest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void taptap(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("Tap","restaurant");
        startActivity(intent);

    }

}