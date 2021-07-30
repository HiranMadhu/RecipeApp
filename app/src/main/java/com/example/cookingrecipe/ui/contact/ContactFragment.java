package com.example.cookingrecipe.ui.contact;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.DBHelper;
import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.ui.home.HomeFragment;
import com.example.cookingrecipe.ui.home.RecipeDetails;

import java.util.Locale;

public class ContactFragment extends Fragment {
    Activity context;
    public static final String[] languages={"Default","English","සිංහල"};

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View root = inflater.inflate(R.layout.fragment_more, container, false);
        return root;

    }


    public void onStart() {
        super.onStart();
        Spinner spinner=context.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectLang=parent.getItemAtPosition(position).toString();
                Intent intent=new Intent(context, MainActivity.class);
                if(selectLang.equals("English")){
                    setLocal(context,"en");
                    startActivity(intent);
                }
                else if(selectLang.equals("සිංහල")){
                    setLocal(context,"si");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(context,"Select Language Please",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setLocal(Activity activity,String langCode){
        Locale locale=new Locale(langCode);
        locale.setDefault(locale);

        Resources resources=activity.getResources();
        Configuration config=resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}