package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {
    private String filePath;

    public CSVDataHandler() {
        filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMode() {
        return "CSV";
    }

    @Override
    public ArrayList<Recipe> readData() throws IOException {
        // レシピリストを作る ※最後にレシピを戻す
        ArrayList<Recipe> recipes = new ArrayList<>();
        // 読み込み、行ごとに処理
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while (((line = reader.readLine()) != null)) {
                // 名前を分ける
                String[] parts = line.split(",", 2);
                String name = parts[0];
                // 材料を分けて、リストに入れる
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                String[] ingredientParts = parts[1].split(",");
                for (String ingredientName : ingredientParts) {
                    ingredients.add(new Ingredient(ingredientName));
                }
                // レシピを名前と材料で追加
                recipes.add(new Recipe(name, ingredients));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return recipes;
    }

    public void writeData(Recipe recipe) throws IOException {
        // 新しいレシピをrecipes.csvに追加します。レシピ名と材料はカンマ区切りで1行としてファイルに書き込まれます。
        String recipeName = recipe.getName();
        ArrayList<Ingredient> recipeIngredients = recipe.getIngredients();

        // 材料名を取得して文字列にする
        String ingredientsName = "";
        for (int i = 0; i < recipeIngredients.size(); i++) {
            ingredientsName += recipeIngredients.get(i).getName();
            if (i < recipeIngredients.size() - 1) {
                ingredientsName += ", ";
            }
        }

        // 書き込む
        String contentToWrite = recipeName + "," + ingredientsName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(contentToWrite);
            writer.newLine(); // 改行する
            result();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public void result() {
        System.out.println("Recipe added successfully.");
    }

    @Override
    public ArrayList<Recipe> searchData(String keyword) throws IOException {
        return null;
    }
}