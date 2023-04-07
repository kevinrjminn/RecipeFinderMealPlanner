package com.usingintent.recipefindermealplanner;

import java.util.ArrayList;

public class MealPlan {
    private String id;
    private String title;
    private String description;
    private ArrayList<Recipe> recipes;

    public MealPlan() {
    }

    public MealPlan(String id, String title, String description, ArrayList<Recipe> recipes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recipes = recipes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
