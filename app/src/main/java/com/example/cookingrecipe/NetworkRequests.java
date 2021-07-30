package com.example.cookingrecipe;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkRequests {
    private static final String LOG_TAG=NetworkRequests.class.getSimpleName();

    private static final String RECIPE_BASE_URL="https://www.themealdb.com/api/json/v1/1/search.php?";
    private static final String QUERY_PARAM="s";

    static String getRecipeInfo(String queryString){
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String recipeJASONString=null;


        try {
            Uri buildURI=Uri.parse(RECIPE_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,queryString).build();
            URL requestURL=new URL(buildURI.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();

            reader=new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder=new StringBuilder();

            String line;
            while ((line=reader.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length()==0){
                return null;
            }
            recipeJASONString=builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,recipeJASONString);
        return recipeJASONString;
    }

}
