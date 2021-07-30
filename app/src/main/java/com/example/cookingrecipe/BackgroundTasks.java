package com.example.cookingrecipe;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static android.graphics.Typeface.BOLD;

public class BackgroundTasks extends AsyncTask<String,Void,String> {

    private WeakReference<TextView> title,direction,ingredients;
    private WeakReference<TextView> temp1,temp2;
    private WeakReference<ImageView> im1,im2;
    private ProgressBar progressBar;

    public BackgroundTasks(TextView text1, TextView text2, TextView text3, ProgressBar pro,TextView text4,TextView text5,ImageView im,ImageView imm){
        this.title=new WeakReference<>(text1);
        this.direction=new WeakReference<>(text2);
        this.ingredients=new WeakReference<>(text3);
        this.progressBar=pro;


        this.temp1=new WeakReference<>(text4);
        this.temp2=new WeakReference<>(text5);
        this.im1=new WeakReference<>(im);
        this.im2=new WeakReference<>(imm);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkRequests.getRecipeInfo(strings[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
            setInvisibility();
            progressBar.setVisibility(View.VISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray itemArray=jsonObject.getJSONArray("meals");

            int i=0;
            int j=1;
            String tempName=null;
            String tempHowTo=null;
            String tempIngredients=null;
            String tempAmount=null;
            String tempFullIngredients=null;
            boolean run=true;

            while (i<itemArray.length()&&(tempName==null && tempHowTo==null&&tempIngredients==null)){
                JSONObject book=itemArray.getJSONObject(i);
                try {
                    tempName = book.getString("strMeal");
                    tempHowTo = book.getString("strInstructions");
                    while(run) {
                        tempIngredients = book.getString("strIngredient"+String.valueOf(j));
                        tempAmount = book.getString("strMeasure"+String.valueOf(j));
                        if (!tempIngredients.equals("")){
                            if (j==1){
                                tempFullIngredients=tempIngredients+" "+tempAmount;
                            }else {
                                tempFullIngredients = tempFullIngredients + "$" + tempIngredients + " " + tempAmount;
                            }
                            j++;
                        }else{
                            j=1;
                            run=false;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                i++;
            }
            if (tempName!=null&&tempHowTo!=null){
                title.get().setText(tempName);

                String[] how_to=tempHowTo.split("\r\n");
                SpannableStringBuilder spannableStringBuilder1=new SpannableStringBuilder("");
                int l=1;
                for(int k=0;k<how_to.length;k++){
                    SpannableString spanText =new SpannableString(String.valueOf(l)+". "+how_to[k]+"\n\n");
                    if (!how_to[k].equals("")){
                        spanText.setSpan(new RelativeSizeSpan(1.2f),0,1,0);
                        spannableStringBuilder1.append(spanText);
                        l++;
                    }
                }
                direction.get().setText(spannableStringBuilder1);


                String[] ingredientsList=tempFullIngredients.split("\\$");
                SpannableStringBuilder spannableStringBuilder2=new SpannableStringBuilder("");
                for(int z=0;z<ingredientsList.length;z++){
                    SpannableString spanText =new SpannableString("+  "+ingredientsList[z]+"\n\n");
                    spanText.setSpan(new ForegroundColorSpan(Color.RED),0,1,0);
                    spanText.setSpan(new RelativeSizeSpan(1.2f),0,1,0);
                    spanText.setSpan(new StyleSpan(BOLD),0,1,0);
                    spannableStringBuilder2.append(spanText);
                }
                ingredients.get().setText(spannableStringBuilder2);
            }else{
                title.get().setText("No result");
                direction.get().setText("");
                ingredients.get().setText("");
            }
        }catch (JSONException e){
            title.get().setText("No result");
            direction.get().setText("");
            ingredients.get().setText("");
            e.printStackTrace();
        }
        progressBar.setVisibility(View.INVISIBLE);
        setVisibility();
    }

    public void setVisibility(){
        ingredients.get().setVisibility(View.VISIBLE);
        title.get().setVisibility(View.VISIBLE);
        direction.get().setVisibility(View.VISIBLE);
        temp1.get().setVisibility(View.VISIBLE);
        temp2.get().setVisibility(View.VISIBLE);
        im1.get().setVisibility(View.VISIBLE);
        im2.get().setVisibility(View.VISIBLE);
    }

    public void setInvisibility(){
        ingredients.get().setVisibility(View.INVISIBLE);
        title.get().setVisibility(View.INVISIBLE);
        direction.get().setVisibility(View.INVISIBLE);
        temp1.get().setVisibility(View.INVISIBLE);
        temp2.get().setVisibility(View.INVISIBLE);
        im1.get().setVisibility(View.INVISIBLE);
        im2.get().setVisibility(View.INVISIBLE);
    }

}
