package com.example.cookingrecipe.ui.addnew;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipe.DBHelper;
import com.example.cookingrecipe.R;

import static android.app.Activity.RESULT_OK;

public class AddNewFragment extends Fragment {
    Activity context;
    private static final int PICK_IMAGE=100;
    Uri imageURI;



    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_new, container, false);
        context=getActivity();
        return root;
    }


    public void onStart() {
        super.onStart();
        ImageView clickImage=context.findViewById(R.id.imageEditInAdd);
        clickImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openGallery();
                return false;
            }
        });
        Button btn = (Button) context.findViewById(R.id.updatebutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecipeData();
//                Toast.makeText(context,amountFinal,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openGallery(){
        Intent galleryPic=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryPic,PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView setImage=context.findViewById(R.id.imageEditInAdd);
        if (resultCode==RESULT_OK&& requestCode==PICK_IMAGE){
            imageURI=data.getData();
            setImage.setImageURI(imageURI);
        }
    }

    public void getRecipeData(){
        EditText editText0=context.findViewById(R.id.nameEdit);
        String nameFinal=editText0.getText().toString();

        EditText editText1=context.findViewById(R.id.ingredientEditText);
        String ingredientTemp=editText1.getText().toString();
        String ingredientFinal=ingredientTemp.replaceAll(System.lineSeparator(),"\\.");

        EditText editText2=context.findViewById(R.id.amountEditText);
        String amountTemp=editText2.getText().toString();
        String amountFinal=amountTemp.replaceAll(System.lineSeparator(),"\\.");

        EditText editText3=context.findViewById(R.id.howToEdit);
        String instructionTemp=editText3.getText().toString();
        String instructionFinal=instructionTemp.replaceAll(System.lineSeparator()," ");

        EditText editText4=context.findViewById(R.id.noteEdit);
        String noteTemp=editText4.getText().toString();
        String noteFinal=noteTemp.replaceAll(System.lineSeparator()," ");

        EditText editText5=context.findViewById(R.id.prepTimeEdit);
        String prepTime=editText5.getText().toString();

        EditText editText6=context.findViewById(R.id.cookTimeEdit);
        String cookTime=editText6.getText().toString();

        EditText editText7=context.findViewById(R.id.caloriEdit);
        String calories=editText7.getText().toString();
        if(nameFinal.equals("")||ingredientTemp.equals("")||amountTemp.equals("")||instructionTemp.equals("")||noteTemp.equals("")||prepTime.equals("")||cookTime.equals("")||calories.equals("")){
            Toast.makeText(context,"Please fill all the information",Toast.LENGTH_LONG).show();
        }else{
            saveData(nameFinal,ingredientFinal,amountFinal,instructionFinal,noteFinal,prepTime,cookTime,calories);
        }
    }

    public void saveData(String name,String ingredients,String amounts,String direction,String note,String prepTime,String cookTime,String calories){
        DBHelper db;
        db = new DBHelper(context);
        int temp_val1=Integer.parseInt(prepTime.trim());
        int temp_val2=Integer.parseInt(cookTime.trim());
        Boolean checker = db.inertData(name,calories,temp_val1,temp_val2,ingredients,amounts,direction,note);
        if (checker == true) {
            Toast.makeText(context, "Data inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Name already taken.\nPlease change name", Toast.LENGTH_LONG).show();
        }

    }

}