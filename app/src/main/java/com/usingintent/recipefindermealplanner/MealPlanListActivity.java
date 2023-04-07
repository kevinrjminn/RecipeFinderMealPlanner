package com.usingintent.recipefindermealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MealPlanListActivity extends AppCompatActivity {

    private RecyclerView mealPlanRecyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_list);

        firestore = FirebaseFirestore.getInstance();
        mealPlanRecyclerView = findViewById(R.id.mealPlanRecyclerView);
        mealPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchMealPlans();
    }

    private void fetchMealPlans() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        firestore.collection("mealPlans").whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<MealPlan> mealPlanList = new ArrayList<>();
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                MealPlan mealPlan = document.toObject(MealPlan.class);
                                mealPlanList.add(mealPlan);
                            }
                            MealPlanAdapter mealPlanAdapter = new MealPlanAdapter(mealPlanList);
                            mealPlanRecyclerView.setAdapter(mealPlanAdapter);
                        }
                    } else {
                        // Handle the error
                    }
                });
    }
}
