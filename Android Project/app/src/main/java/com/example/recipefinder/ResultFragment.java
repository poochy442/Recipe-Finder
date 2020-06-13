package com.example.recipefinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {

    public static final int recipeAmount = 10;
    RequestQueue queue;
    ArrayList<Recipe> recipeList;
    LinearLayout linearLayout;
    String searchValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);

        // Set up variables
        linearLayout = rootView.findViewById(R.id.result_layout);
        recipeList = new ArrayList<>();

        Bundle args = getArguments();
        if(args != null)
            searchValue = args.getString("searchValue");
        Log.d("Result/create", "Received value: " + searchValue);

        populateRecipes();

        return rootView;
    }

    public void populateRecipes(){
        // Set up Volley to send HttpRequests
        queue = Volley.newRequestQueue(getContext());
        String apiKey = getString(R.string.api_key);
        String url = getString(R.string.base_api)
                + "query=" + searchValue
                + "&number=10"
                + "&InstructionsRequired=true"
                + "&apiKey=" + apiKey;
        // TODO: Check for values instead of hard-coded

        // Consume web service to get Recipes
        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("results");

                            Log.d("Result/Request_r", jsonArray.toString());

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);

                                Log.d("Result/Request_r", o.toString());

                                String title = o.getString("title"), sourceURL = o.getString("sourceUrl"), imageURL = o.getString("image");
                                recipeList.add(new Recipe(title, sourceURL, imageURL));
                            }
                            updateView(recipeList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Result/Request_e", "Request error!\n" + error.getMessage());
                    }
                });


        queue.add(searchRequest);
    }

    private void updateView(final List<Recipe> recipeList){
        for(int i = 0; i < recipeList.size(); i++){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            LinearLayout l = (LinearLayout) layoutInflater.inflate(R.layout.list_row_layout, null);
            Recipe recipe = recipeList.get(i);
            final int index = i;

            // Load ImageView
            ImageView image = l.findViewById(R.id.row_image);
            Picasso.get()
                    .load("https://spoonacular.com/recipeImages/" + recipe.imageURL)
                    .resize(125, 125)
                    .centerCrop()
                    .into(image);

            // Load TextView
            TextView text = l.findViewById(R.id.row_text);
            text.setText(recipe.title);

            // Make the TextView clickable, linked to recipe
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String action = Intent.ACTION_VIEW;
                    Uri uri = Uri.parse(recipeList.get(index).sourceURL);

                    Intent intent = new Intent(action, uri);
                    startActivity(intent);
                }
            });
            text.setLinksClickable(true);

            linearLayout.addView(l);
        }
    }
}
