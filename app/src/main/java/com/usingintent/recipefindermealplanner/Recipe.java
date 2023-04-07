package com.usingintent.recipefindermealplanner;

import java.io.Serializable;

public class Recipe implements Serializable {

    private String id;
    private String title;
    private String imageUrl;
    private String ingredients;
    private String instructions;

    public Recipe() {
    }

    public Recipe(String id, String title, String imageUrl, String ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.instructions = instructions;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
