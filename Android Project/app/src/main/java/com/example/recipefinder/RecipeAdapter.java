package com.example.recipefinder;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter implements ListAdapter {

    ArrayList<Recipe> recipes;
    Context context;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = recipes.get(position);

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);

            // Load ImageView
            ImageView image = convertView.findViewById(R.id.row_image);
            Picasso.get()
                    .load("https://spoonacular.com/recipeImages/" + recipe.imageURL)
                    .resize(125, 125)
                    .centerCrop()
                    .into(image);

            // Load TextView
            TextView text = convertView.findViewById(R.id.row_text);
            text.setText(recipe.title);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return recipes.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
