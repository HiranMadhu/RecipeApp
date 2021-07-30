package com.example.cookingrecipe;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeListActivity extends AppCompatActivity {

    private EditText recipeName;
    private TextView name;
    private TextView ingredients;
    private TextView howTo;
    private ProgressBar progressBar;
    private TextView temp1;
    private TextView temp2;
    private ImageView im1;
    private ImageView im2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeName=findViewById(R.id.internet_searchEdit);
        name=findViewById(R.id.internet_name_edit);
        ingredients=findViewById(R.id.internet_ingredients_edit);
        howTo=findViewById(R.id.internet_how_to_edit);
        progressBar=findViewById(R.id.progressBar);
        temp1=findViewById(R.id.internet_ingredients_labal);
        im2=findViewById(R.id.internetImageview2);
        im1=findViewById(R.id.internetImageview1);
        temp2=findViewById(R.id.internet_how_to_lable);
    }

    public void getName(View view){
        String query=recipeName.getText().toString();
        new BackgroundTasks(name,howTo,ingredients,progressBar,temp1,temp2,im1,im2).execute(query);
        recipeName.setText("");
    }


}