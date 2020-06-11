package com.example.recipefinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean isCurrent;
    ImageView exampleImage;
    Button searchButton;
    EditText searchText;
    final String[] exampleImages = {
            "Blue-Cheese-Burgers-246009.jpg",
            "Mexican-Street-Corn-Nachos-671890.jpg",
            "Spaghetti-Carbonara-535835.jpg",
            "Tex-Mex-Chicken---White-Cheddar-Spaghetti-587145.png",
            "strawberry-mango-chopped-spinach-quinoa-salad-with-sesame-lime-vinaigrette-809898.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rand = new Random();
        exampleImage = (ImageView) findViewById(R.id.main_image_view);
        searchButton = findViewById(R.id.main_button);
        searchText = findViewById(R.id.main_text_edit);

        int imageInt = rand.nextInt(exampleImages.length);

        Log.d("MainActivity", getString(R.string.image_api) + exampleImages[imageInt]);

        Picasso.get()
                .load(getString(R.string.image_api) + exampleImages[imageInt])
                .resize(250, 250)
                .centerCrop()
                .into(exampleImage);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("searchValue", searchText.getText());
                startActivity(intent);
            }
        });
    }
}
