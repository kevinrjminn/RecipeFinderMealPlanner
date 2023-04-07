package com.usingintent.recipefindermealplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchRecipesButton = findViewById(R.id.searchRecipesButton);
        Button mealPlanButton = findViewById(R.id.mealPlanButton);

        FirebaseAuth.getInstance().signOut();

        searchRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeSearchActivity.class);
                startActivity(intent);
            }
        });

        mealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MealPlanActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);
        bottomNavigationView.getMenu().findItem(R.id.navigation_search).setChecked(true);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    startActivity(new Intent(MainActivity.this, RecipeSearchActivity.class));
                    return true;
                case R.id.navigation_meal_plan:
                    startActivity(new Intent(MainActivity.this, MealPlanActivity.class));
                    return true;
            }
            return false;
        }
    };

    private void addFavoriteRecipe(String userId, Map<String, Object> recipeData) {
        db.collection("users").document(userId).collection("favoriteRecipes").add(recipeData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("MainActivity", "Error adding document", e);
                });
    }

    private void getFavoriteRecipes(String userId) {
        db.collection("users").document(userId).collection("favoriteRecipes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("MainActivity", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w("MainActivity", "Error getting documents.", task.getException());
                    }
                });
    }

    private void updateFavoriteRecipe(String userId, String recipeId, Map<String, Object> updatedRecipeData) {
        db.collection("users").document(userId).collection("favoriteRecipes").document(recipeId)
                .set(updatedRecipeData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("MainActivity", "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w("MainActivity", "Error updating document", e));
    }

    private void deleteFavoriteRecipe(String userId, String recipeId) {
        db.collection("users").document(userId).collection("favoriteRecipes").document(recipeId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("MainActivity", "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e -> Log.w("MainActivity", "Error deleting document", e));
    }

    private void addMealPlan(String userId, Map<String, Object> mealPlanData) {
        db.collection("users").document(userId).collection("mealPlans")
                .add(mealPlanData)
                .addOnSuccessListener(documentReference -> Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w("MainActivity", "Error adding document", e));
    }



    private void getMealPlans(String userId) {
        db.collection("users").document(userId).collection("mealPlans")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("MainActivity", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w("MainActivity", "Error getting documents.", task.getException());
                    }
                });
    }

    private void updateMealPlan(String userId, String mealPlanId, Map<String, Object> updatedMealPlanData) {
        db.collection("users").document(userId).collection("mealPlans").document(mealPlanId)
                .set(updatedMealPlanData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("MainActivity", "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w("MainActivity", "Error updating document", e));
    }
    private void deleteMealPlan(String userId, String mealPlanId) {
        db.collection("users").document(userId).collection("mealPlans").document(mealPlanId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("MainActivity", "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e -> Log.w("MainActivity", "Error deleting document", e));
    }






}
