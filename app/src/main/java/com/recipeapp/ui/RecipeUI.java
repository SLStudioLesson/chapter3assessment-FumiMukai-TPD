package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        displayRecipes();
                    case "2":
                        addNewRecipe();
                    case "3":
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    private void displayRecipes() {
        try {
            ArrayList<Recipe> recipes = dataHandler.readData();
            if (recipes.isEmpty()) {
                System.out.println("No recipes available.");
            } else {
                // リストの範囲で繰り返し
                for (Recipe recipe : recipes) {
                    System.out.println("-----------------------------------");
                    System.out.println("Recipe Name: " + recipe.getName());
                    System.out.print("Main Ingredients: ");
                    recipe.showIngredients();
                    System.out.println();
                }

                // 閉じるためのハイフン
                System.out.println("-----------------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading output from recipes: " + e.getMessage());
        }

    }

    private void addNewRecipe() {
        /*
         * - ユーザーからレシピ名と主な材料を入力させ、DataHandlerを使用してrecipes.csvに新しいレシピを追加します。
         * - IOExceptionを受け取った場合はFailed to add new recipe: 例外のメッセージとコンソールに表示してください。
         * - 材料の入力はdoneと入力するまで入力を受け付けます。
         * - 表示形式は以下の出力例を再現してください。
         */
        try {
            // レシピの入力
            System.out.println("Enter recipe name: ");
            String newRecipe = reader.readLine();
            // 材料の入力
            System.out.println("Enter ingredients (type 'done' when finished):");
            ArrayList<Ingredient> newIngredients = new ArrayList<>();

            String ingredientInput;

            while (!(ingredientInput = reader.readLine()).equals("done")) {
                System.out.println("Ingredient: " + ingredientInput);
                newIngredients.add(new Ingredient(ingredientInput));
            }

            // レシピを追加
            Recipe recipe = new Recipe(newRecipe, newIngredients);

            // DataHandlerで書き出し処理
            dataHandler.writeData(recipe);

        } catch (IOException e) {
            System.out.println("Failed to add new recipe: " + e.getMessage());
        }

    }
}
