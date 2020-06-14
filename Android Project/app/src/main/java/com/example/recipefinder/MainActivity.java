package com.example.recipefinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Check if we need to do setup
        if(savedInstanceState != null){
            return;
        }

        // Testing stuff!
        // Replaces preferences with starter preferences
        SharedPreferences sp = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        sp.edit().putInt("number", 10).putString("Day/Night", "dark").apply();

        ActionMenuItemView item = findViewById(R.id.item_settings);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change to Settings fragment
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SettingsFragment fragment = new SettingsFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.main_content, fragment);

                        transaction.addToBackStack("Settings");
                        transaction.commit();
                    }
                });
            }
        });

        // Set start fragment
        SearchFragment sf = new SearchFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, sf).commit();
    }
}
