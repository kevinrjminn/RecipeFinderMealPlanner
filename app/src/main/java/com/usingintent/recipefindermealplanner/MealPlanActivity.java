package com.usingintent.recipefindermealplanner;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MealPlanActivity extends AppCompatActivity {
    private RecyclerView mealPlanRecyclerView;
    private MealPlanAdapter mealPlanAdapter;
    private List<MealPlan> mealPlanList;
    private DatabaseReference mealPlanDatabaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        mealPlanRecyclerView = findViewById(R.id.mealPlanRecyclerView);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            mealPlanDatabaseReference = FirebaseDatabase.getInstance().getReference().child("MealPlans").child(userId);
        }

        mealPlanList = new ArrayList<>();
        mealPlanAdapter = new MealPlanAdapter(mealPlanList);

        mealPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealPlanRecyclerView.setAdapter(mealPlanAdapter);

        loadMealPlan();
    }

    private void loadMealPlan() {
        mealPlanDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealPlanList.clear();
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot mealPlanSnapshot : dateSnapshot.getChildren()) {
                        MealPlan mealPlan = mealPlanSnapshot.getValue(MealPlan.class);
                        mealPlanList.add(mealPlan);
                    }
                }
                mealPlanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
