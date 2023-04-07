package com.usingintent.recipefindermealplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView recipeDetailImageView;
    private TextView recipeDetailTitleTextView;
    private TextView recipeDetailIngredientsTextView;
    private TextView recipeDetailInstructionsTextView;
    private Recipe recipe;
    private Button addToMealPlanButton;
    private DatabaseReference mealPlanDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeDetailImageView = findViewById(R.id.recipeDetailImageView);
        recipeDetailTitleTextView = findViewById(R.id.recipeDetailTitleTextView);
        recipeDetailIngredientsTextView = findViewById(R.id.recipeDetailIngredientsTextView);
        recipeDetailInstructionsTextView = findViewById(R.id.recipeDetailInstructionsTextView);

        if (getIntent().hasExtra("recipe")) {
            recipe = (Recipe) getIntent().getSerializableExtra("recipe");

            Glide.with(this).load(recipe.getImageUrl()).into(recipeDetailImageView);
            recipeDetailTitleTextView.setText(recipe.getTitle());
            recipeDetailIngredientsTextView.setText(recipe.getIngredients());
            recipeDetailInstructionsTextView.setText(recipe.getInstructions());
        }

        addToMealPlanButton = findViewById(R.id.addToMealPlanButton);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            mealPlanDatabaseReference = FirebaseDatabase.getInstance().getReference().child("MealPlans").child(userId);
        }

        addToMealPlanButton.setOnClickListener(v -> {
            showDatePickerDialog();
        });

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    addToMealPlan(selectedDate);
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void addToMealPlan(Calendar selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        String selectedDateString = sdf.format(selectedDate.getTime());

        mealPlanDatabaseReference.child(selectedDateString).push().setValue(recipe).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(RecipeDetailActivity.this, "Recipe added to meal plan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RecipeDetailActivity.this, "Failed to add recipe to meal plan", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
