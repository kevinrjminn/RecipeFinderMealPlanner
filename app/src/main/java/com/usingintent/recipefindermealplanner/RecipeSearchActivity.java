package com.usingintent.recipefindermealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class RecipeSearchActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    // ...

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;
    private EditText searchEditText;
    private Button searchButton;
    private final String apiKey = "cd3b6c74030e4bc186045aa4541c039d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        recipes = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(recipes, this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipes(searchEditText.getText().toString());
            }
        });
    }

    private void searchRecipes(String query) {
        String apiKey = "1";
        String url = "https://www.themealdb.com/api/json/v1/" + apiKey + "/search.php?s=" + query;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray meals = response.getJSONArray("meals");

                    for (int i = 0; i < meals.length(); i++) {
                        JSONObject meal = meals.getJSONObject(i);
                        String id = meal.getString("idMeal");
                        String title = meal.getString("strMeal");
                        String imageUrl = meal.getString("strMealThumb");

                        String ingredients = "Ingredients data";
                        String instructions = "Instructions data";

                        Recipe recipe = new Recipe(id, title, imageUrl, ingredients, instructions);
                        recipes.add(recipe);
                    }

                    recipeAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        Recipe selectedRecipe = recipes.get(position);
        intent.putExtra("recipe", selectedRecipe);
        startActivity(intent);
    }





}

