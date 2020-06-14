package com.example.recipefinder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    ArrayList<Recipe> recipes;
    final private RecipeAdapter.OnListItemClickListener onListItemClickListener;

    RecipeAdapter(ArrayList<Recipe> recipes, OnListItemClickListener onListItemClickListener){
        this.recipes = recipes;
        this.onListItemClickListener = onListItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe r = recipes.get(position);

        Picasso.get()
                .load("https://spoonacular.com/recipeImages/" + r.imageURL)
                .resize(125, 125)
                .centerCrop()
                .into(holder.imageView);

        holder.textView.setText(r.title);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        ViewHolder(View viewItem){
            super(viewItem);

            // Load Views
            imageView = viewItem.findViewById(R.id.row_image);
            textView = viewItem.findViewById(R.id.row_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            onListItemClickListener.onListItemClick(recipes.get(getAdapterPosition()));
        }

    }

    public interface OnListItemClickListener{
        public void onListItemClick(Recipe clickedRecipe);
    }
}

