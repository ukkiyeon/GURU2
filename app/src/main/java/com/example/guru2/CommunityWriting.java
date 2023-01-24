package com.example.guru2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



public class CommunityWriting extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_writing);

        // Spinner
        Spinner yearSpinner = (Spinner)findViewById(R.id.spinner_city);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.city, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        yearSpinner.setAdapter(yearAdapter);

        Spinner monthSpinner = (Spinner)findViewById(R.id.spinner_gu);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.gu, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        monthSpinner.setAdapter(monthAdapter);
    }
}
