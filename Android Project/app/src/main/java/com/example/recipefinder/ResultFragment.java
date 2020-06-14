package com.example.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultFragment extends Fragment implements RecipeAdapter.OnListItemClickListener {

    public static final int recipeAmount = 10;
    RecipeAdapter.OnListItemClickListener listener = this;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;
    RequestQueue queue;
    ArrayList<Recipe> recipeList;
    LinearLayout linearLayout;
    String searchValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);

        // Set up variables
        linearLayout = rootView.findViewById(R.id.result_layout);

        // Create RecyclerView
        recyclerView = rootView.findViewById(R.id.result_recycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        Drawable divider = ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null);
        itemDecoration.setDrawable(divider);
        recyclerView.addItemDecoration(itemDecoration);

        // Populate RecipeList
        recipeList = new ArrayList<>();
        Bundle args = getArguments();
        if(args != null)
            searchValue = args.getString("searchValue");
        Log.d("Result/create", "Received value: " + searchValue);
        populateRecipes(rootView);

        recipeAdapter = new RecipeAdapter(recipeList, listener);
        recyclerView.setAdapter(recipeAdapter);

        return rootView;
    }

    public void populateRecipes(View rootView){
        // Set up Volley to send HttpRequests
        queue = Volley.newRequestQueue(getContext());
        String apiKey = getString(R.string.api_key);
        SharedPreferences sp = rootView.getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int number = sp.getInt("number", 10);
        String url = getString(R.string.base_api)
                + "query=" + searchValue
                + "&number=" + number
                + "&InstructionsRequired=true"
                + "&apiKey=" + apiKey;

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

                            // Set adapter
                            recipeAdapter.notifyDataSetChanged();
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

    @Override
    public void onListItemClick(Recipe clickedRecipe) {
        String action = Intent.ACTION_VIEW;
        Uri uri = Uri.parse(clickedRecipe.sourceURL);

        Intent intent = new Intent(action, uri);
        startActivity(intent);
    }
}
