package com.example.cookingrecipe.ui.home;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.DBHelper;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.ui.update.UpdateRecipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Typeface.BOLD;

public class RecipeDetails extends AppCompatActivity {

    public static String[] separatedTemp;
    public static final String EXTRA_MESSAGE2="Extra.Message";
    public static String updateName;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if(getIntent().hasExtra(HomeFragment.EXTRA_MESSAGE)){
            Intent intent=getIntent();
            String device=intent.getStringExtra(HomeFragment.EXTRA_MESSAGE);
            updateName=device;
            updateClick(device);
        }
        else if (getIntent().hasExtra(UpdateRecipe.EXTRA_MESSAGE3)){
            Intent intent=getIntent();
            String device=intent.getStringExtra(UpdateRecipe.EXTRA_MESSAGE3);
            updateName=device;
            updateClick(device);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void updateClick(String device){
        DBHelper db;
        db=new DBHelper(this);
        setPic(device);
        TextView textView=findViewById(R.id.internet_name_edit);
        textView.setText(device);
        Cursor res = db.getRecipeData(device);
        if (res.getCount() == 0) {
            Toast.makeText(RecipeDetails.this, "No data Available", Toast.LENGTH_LONG).show();
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(2) + "$");
            buffer.append(res.getString(3) + "$");
            buffer.append(res.getString(4) + "$");
            buffer.append(res.getString(5) + "$");
            buffer.append(res.getString(6) + "$");
            buffer.append(res.getString(7) + "$");
            buffer.append(res.getString(8));
        }
        String allData=buffer.toString();
        separatedTemp = allData.split("\\$");
        setTimes();
        setIngredients();
        setDirection();
        setNote();
    }

    public void showDevice(View view){
        TextView temp1=findViewById(R.id.internet_name_edit);
        String nameOfRecipe=temp1.getText().toString();
        DBHelper db;
        db=new DBHelper(this);
        Cursor neut=db.getRecipeData(nameOfRecipe);

        if (neut.getCount() == 0) {
            Toast.makeText(RecipeDetails.this, "No data Available", Toast.LENGTH_LONG).show();
        }
        StringBuffer buffer = new StringBuffer();
        while (neut.moveToNext()) {
            buffer.append(neut.getString(1));
        }
        String nutrition = buffer.toString();
        Snackbar.make(view, "Calories per Serving : "+nutrition+" kcal",Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void setIngredients(){
        TextView inge=findViewById(R.id.ingredients_edit);

        String[] scales=separatedTemp[3].split("\\.");
        String[] ingredients=separatedTemp[2].split("\\.");
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder("");
        for(int i=0;i<ingredients.length;i++){
            SpannableString spanText =new SpannableString("+  "+scales[i]+"  "+ingredients[i]+"\n\n");
            spanText.setSpan(new ForegroundColorSpan(Color.RED),0,1,0);
            spanText.setSpan(new RelativeSizeSpan(1.2f),0,1,0);
            spanText.setSpan(new StyleSpan(BOLD),0,1,0);
            spannableStringBuilder.append(spanText);
        }
        inge.setText(spannableStringBuilder);
    }

    public void setDirection(){
        TextView ingredientView=findViewById(R.id.how_to_edit);

        String[] how_to=separatedTemp[5].split("\\.");
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder("");
        for(int i=0;i<how_to.length;i++){
            SpannableString spanText =new SpannableString(String.valueOf(i+1)+". "+how_to[i]+"\n\n");
            spanText.setSpan(new RelativeSizeSpan(1.2f),0,1,0);
            spannableStringBuilder.append(spanText);
        }
        ingredientView.setText(spannableStringBuilder);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void setNote(){
        TextView noteView=findViewById(R.id.note_edit);

        String[] note=separatedTemp[6].split("\\.");
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder("");
        for(int i=0;i<note.length;i++){
            SpannableString spanText =new SpannableString(note[i]+"\n\n");
            spanText.setSpan(new BulletSpan(20,Color.BLUE,10),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(spanText);
        }
        noteView.setText(spannableStringBuilder);
    }


    public void setTimes(){
        TextView prepTime=findViewById(R.id.prep_time_edit);
        TextView cookTime=findViewById(R.id.cook_time_edit);
        TextView totalTime=findViewById(R.id.total_time_edit);

        int temp_val1=Integer.parseInt(separatedTemp[0].trim());
        int temp_val2=Integer.parseInt(separatedTemp[1].trim());

        SpannableString spanText1 =new SpannableString(separatedTemp[0]+" m\n"+"Prep");
        spanText1.setSpan(new RelativeSizeSpan(1.3f),0,2,0);
        SpannableString spanText2 =new SpannableString(separatedTemp[1]+" m\n"+"Cook");
        spanText2.setSpan(new RelativeSizeSpan(1.3f),0,2,0);
        SpannableString spanText3 =new SpannableString(String.valueOf(temp_val1+temp_val2)+" m\n"+"Ready In");
        spanText3.setSpan(new RelativeSizeSpan(1.3f),0,2,0);
        prepTime.setText(spanText3, TextView.BufferType.SPANNABLE);

        prepTime.setText(spanText1, TextView.BufferType.SPANNABLE);
        cookTime.setText(spanText2, TextView.BufferType.SPANNABLE);
        totalTime.setText(spanText3, TextView.BufferType.SPANNABLE);
    }

    public void setPic(String device_name) {
        ImageView imageView = findViewById(R.id.recipe_image_change);
        int set_val;
        switch (device_name) {
            case "Burger":
                set_val = (R.drawable.burger);
                break;
            case "Fried Rice":
                set_val = (R.drawable.friedrice);
                break;
            case "Submarine":
                set_val = (R.drawable.submarine);
                break;
            case "Pancakes":
                set_val = (R.drawable.pancake);
                break;
            default:
                set_val = (R.drawable.noimage);
                break;
        }
        imageView.setImageResource(set_val);
    }

    public void updateClick(View view){
        Intent intent=new Intent(this, UpdateRecipe.class);
        intent.putExtra( EXTRA_MESSAGE2,updateName);
        startActivity(intent);
    }
}