package com.example.recipefinder;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class RecipeAdapterTest {

    ArrayList<Recipe> recipes;
    private RecipeAdapter adapter;

    @Mock
    Context context;

    @Before
    public void setUp(){
        recipes = new ArrayList<>();

        recipes.add(new Recipe("Title1", "sourceURL1", "imageURL1"));
        recipes.add(new Recipe("Title2", "sourceURL2", "imageURL2"));

        adapter = new RecipeAdapter(recipes, new RecipeAdapter.OnListItemClickListener() {
            @Override
            public void onListItemClick(Recipe clickedRecipe) {
                // Do nothing
            }
        });


    }

    @Test
    public void getItemCountTest(){

    }

}