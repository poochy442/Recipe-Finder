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

    List<Recipe> recipeList;
    LinearLayout linearLayout;
    String searchValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);

        // Set up variables
        linearLayout = rootView.findViewById(R.id.search_scroll_linear);
        recipeList = new ArrayList<>();

        Bundle args = getArguments();
        if(args != null)
            searchValue = args.getString("searchValue");
        Log.d("Result/populate", "Received value: " + searchValue);

        populateView(rootView);

        return rootView;
    }

    public void populateView(View root){
        // Set up Volley to send HttpRequests
        RequestQueue queue = Volley.newRequestQueue(getContext());
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

    private void updateView(List<Recipe> recipeList){
        for(int i = 0; i < recipeList.size(); i++){
            // Create a ViewGroup
            LinearLayout l = new LinearLayout(getContext());
            l.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            l.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            TextView t = new TextView(getContext());
            t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final Recipe r = recipeList.get(i);
            t.setText(r.title);

            // Make the TextView clickable, linked to recipe
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String action = Intent.ACTION_VIEW;
                    Uri uri = Uri.parse(r.sourceURL);

                    Intent intent = new Intent(action, uri);
                    startActivity(intent);
                }
            });
            t.setLinksClickable(true);

            // Create ImageView
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // Load image into ImageView with Picasso
            Picasso.get()
                    .load(getString(R.string.image_api) + r.imageURL)
                    .resize(125, 125)
                    .centerCrop()
                    .into(image);

            // Add view to LinearLayout and that to the ScrollView
            l.addView(t);
            l.addView(image);

            linearLayout.addView(l);
        }
    }

}
