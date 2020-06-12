package com.example.recipefinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Check if we need to do setup
        if(savedInstanceState != null){
            return;
        }

        // Set start fragment
        SearchFragment sf = new SearchFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, sf).commit();
    }
}
