package com.example.recipefinder;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    boolean isCurrent;
    ImageView exampleImage;
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

        // Set content view
        setContentView(R.layout.activity_main);
        exampleImage = (ImageView) findViewById(R.id.main_image_view);

        Log.d("MainActivity", getString(R.string.image_api) + exampleImages[0]);

        Picasso.get()
                .load(getString(R.string.image_api) + exampleImages[0])
                .resize(250, 250)
                .centerCrop()
                .into(exampleImage);
    }
}
