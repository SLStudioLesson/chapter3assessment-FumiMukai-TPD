package com.recipeapp.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredients(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void showIngredients() {
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.print(ingredients.get(i).getName());
            if (i < ingredients.size() - 1) {
                System.out.print(",");
            }
        }
    }
}