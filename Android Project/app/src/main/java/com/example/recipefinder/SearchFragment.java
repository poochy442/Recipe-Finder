package com.example.recipefinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class SearchFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_result, container, false);
        populateView(rootView);
        return rootView;
    }

    public void populateView(View root){
        // Setup variables
        Random rand = new Random();
        exampleImage = root.findViewById(R.id.main_image_view);
        searchButton = root.findViewById(R.id.main_button);
        searchText = root.findViewById(R.id.main_text_edit);

        int imageInt = rand.nextInt(exampleImages.length);

        Log.d("Search/onCreate", "Loading image: " + getString(R.string.image_api) + exampleImages[imageInt]);

        // Load image with Picasso
        Picasso.get()
                .load(getString(R.string.image_api) + exampleImages[imageInt])
                .resize(250, 250)
                .centerCrop()
                .into(exampleImage);

        // Create search button Listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                String searchString = searchText.getText().toString();
                ResultFragment rf = new ResultFragment();

                Log.d("Search/onClick", "Sending value: " + searchString);
                Bundle args = new Bundle();
                args.putString("searchValue", searchString);
                rf.setArguments(args);

                transaction.replace(R.id.main_content, rf);
                transaction.commit();
            }
        });
    }
}
