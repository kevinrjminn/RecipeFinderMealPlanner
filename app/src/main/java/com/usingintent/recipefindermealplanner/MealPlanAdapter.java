package com.usingintent.recipefindermealplanner;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealPlanViewHolder> {

    private final List<MealPlan> mealPlanList;

    public MealPlanAdapter(List<MealPlan> mealPlanList) {
        this.mealPlanList = mealPlanList;
    }

    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_plan, parent, false);
        return new MealPlanViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MealPlanViewHolder holder, int position) {
        MealPlan mealPlan = mealPlanList.get(position);
        holder.mealPlanTitle.setText(mealPlan.getTitle());
        holder.mealPlanDescription.setText(mealPlan.getDescription());

        // Add any additional data bindings as needed
    }

    @Override
    public int getItemCount() {
        return mealPlanList.size();
    }

    public static class MealPlanViewHolder extends RecyclerView.ViewHolder {
        public TextView mealPlanTitle;
        public TextView mealPlanDescription;

        public MealPlanViewHolder(@NonNull View itemView) {
            super(itemView);

            mealPlanTitle = itemView.findViewById(R.id.meal_plan_title);
            mealPlanDescription = itemView.findViewById(R.id.meal_plan_description);
        }
    }
}
