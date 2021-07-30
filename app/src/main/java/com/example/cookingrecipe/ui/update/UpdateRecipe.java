package com.example.cookingrecipe.ui.update;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.DBHelper;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.ui.home.HomeFragment;
import com.example.cookingrecipe.ui.home.RecipeDetails;

public class UpdateRecipe extends AppCompatActivity {
    private EditText title,ingredients,amount,howTo,prepT,cookT,calories,note;
    private static String updateName;
    public static String[] separatedTemp;
    public static final String EXTRA_MESSAGE3="Extra.Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);
        if(getIntent().hasExtra(RecipeDetails.EXTRA_MESSAGE2)){
            Intent intent=getIntent();
            String device=intent.getStringExtra(RecipeDetails.EXTRA_MESSAGE2);
            updateName=device;
        }

        DBHelper db;
        db=new DBHelper(this);
        Cursor res = db.getRecipeData(updateName);
        if (res.getCount() == 0) {
            Toast.makeText(UpdateRecipe.this, "No data Available", Toast.LENGTH_LONG).show();
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(2) + "$");
            buffer.append(res.getString(3) + "$");
            buffer.append(res.getString(4) + "$");
            buffer.append(res.getString(5) + "$");
            buffer.append(res.getString(6) + "$");
            buffer.append(res.getString(7) + "$");
            buffer.append(res.getString(8) + "$");
            buffer.append(res.getString(1));
        }
        String allData=buffer.toString();
        separatedTemp = allData.split("\\$");
        setResourcesValue();
        putData();
    }



    public void updateDataButton(View view){
        String nameFinal=title.getText().toString();
        String ingredientTemp=ingredients.getText().toString();
        String ingredientFinal=ingredientTemp.replaceAll(System.lineSeparator(),"\\.");
        String amountTemp=amount.getText().toString();
        String amountFinal=amountTemp.replaceAll(System.lineSeparator(),"\\.");
        String instructionTemp=howTo.getText().toString();
        String instructionFinal=instructionTemp.replaceAll(System.lineSeparator(),"");
        String noteTemp=note.getText().toString();
        String noteFinal=noteTemp.replaceAll(System.lineSeparator(),"");
        String prepTime=prepT.getText().toString();
        String cookTime=cookT.getText().toString();
        String calorie=calories.getText().toString();
        saveData(updateName,nameFinal,ingredientFinal,amountFinal,instructionFinal,noteFinal,prepTime,cookTime,calorie);
    }



    public void saveData(String oldName,String name,String ingredients,String amounts,String direction,String note,String prepTime,String cookTime,String calories){
        DBHelper db2;
        db2 = new DBHelper(this);
        int temp_val1=Integer.parseInt(prepTime.trim());
        int temp_val2=Integer.parseInt(cookTime.trim());
        Boolean checker = db2.updateUserData(oldName,name,calories,temp_val1,temp_val2,ingredients,amounts,direction,note);
        if (checker == true) {
            Toast.makeText(this, "Data Updated", Toast.LENGTH_LONG).show();
            updateName=name;
        } else {
            Toast.makeText(this, "Error occur. \nPlease retry again", Toast.LENGTH_LONG).show();
        }

    }



    public void setResourcesValue(){
        title=findViewById(R.id.update_nameEdit);
        ingredients=findViewById(R.id.update_ingredientEditText);
        amount=findViewById(R.id.update_amountEditText);
        howTo=findViewById(R.id.update_howToEdit);
        prepT=findViewById(R.id.update_prepTimeEdit);
        cookT=findViewById(R.id.update_cookTimeEdit);
        calories=findViewById(R.id.update_caloriEdit);
        note=findViewById(R.id.update_noteEdit);
    }



    public void putData(){
        String ingredientsTemp=separatedTemp[2].replaceAll("\\.","\n");
        String amountTemp=separatedTemp[3].replaceAll("\\.","\n");
        String howToTemp=separatedTemp[5].replaceAll("\\.","\\.\n");
        String noteTemp=separatedTemp[6].replaceAll("\\.","\\.\n");

        title.setText(updateName);
        ingredients.setText(ingredientsTemp);
        amount.setText(amountTemp);
        howTo.setText(howToTemp);
        prepT.setText(separatedTemp[0]);
        cookT.setText(separatedTemp[1]);
        calories.setText(separatedTemp[7]);
        note.setText(noteTemp);
    }


    public void onCancelClick(View view) {
        Intent intent=new Intent(this, RecipeDetails.class);
        intent.putExtra( EXTRA_MESSAGE3,updateName);
        startActivity(intent);
    }

}