package com.example.cookingrecipe.ui.shopping;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipe.R;

public class ShoppingListFragment extends Fragment {

    Activity context;
    public String listString=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        context=getActivity();
        return root;
    }

    public void onStart() {
        super.onStart();
        Button button1=context.findViewById(R.id.saveButton);
        Button button2=context.findViewById(R.id.canselButton);
        TextView buttonAdd=context.findViewById(R.id.addButton);
        TextView buttonClear=context.findViewById(R.id.clearButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=context.findViewById(R.id.shoppingListEdit);
                String newItem=textView.getText().toString();
                if(listString==null){
                    listString=newItem;
                }else {
                    listString = listString+ "." + newItem ;
                }
                String[] separatedItems = listString.split("\\.");
                textView.setText("");
                setList(separatedItems);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listString=null;
                String[] separatedItems ={};
                setList(separatedItems);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button okButton=context.findViewById(R.id.saveButton);
                Button notOkButton=context.findViewById(R.id.canselButton);
                EditText editText=context.findViewById(R.id.shoppingListEdit);
                editText.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                notOkButton.setVisibility(View.VISIBLE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button okButton=context.findViewById(R.id.saveButton);
                Button notOkButton=context.findViewById(R.id.canselButton);
                EditText editText=context.findViewById(R.id.shoppingListEdit);
                editText.setVisibility(View.GONE);
                okButton.setVisibility(View.GONE);
                notOkButton.setVisibility(View.GONE);
            }
        });


    }


    public void setList(String[] items){
        TextView noOFItem=context.findViewById(R.id.itemNoOnList);
        if(items.length==0){
            noOFItem.setVisibility(View.INVISIBLE);
        }
        else{
            noOFItem.setVisibility(View.VISIBLE);
            noOFItem.setText(String.valueOf(items.length)+"  items");
        }
        ArrayAdapter<String> adapter;
        ListView listView;
        listView=context.findViewById(R.id.listView_data);
        adapter =new ArrayAdapter<String>(context, android.R.layout.simple_list_item_multiple_choice,items);
        listView.setAdapter(adapter);
    }


}