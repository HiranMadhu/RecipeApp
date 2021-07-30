package com.example.cookingrecipe.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.DBHelper;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Activity context;
    public static final String EXTRA_MESSAGE="Extra.Message";
    List<String> temp_name_list=new ArrayList<String>(){{}};

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onStart() {
        super.onStart();

        DBHelper db;
        db = new DBHelper(context);
        Cursor res = db.getData();
        if (res.getCount() == 0) {
            Toast.makeText(context, "No data.\nPlease reload", Toast.LENGTH_LONG).show();
        }

        List<Integer> temp_image_val;
        while (res.moveToNext()) {
            temp_name_list.add(res.getString(0));
        }
        temp_image_val=setImage(temp_name_list);
        String[] name_array= temp_name_list.toArray(new String[0]);
        int[] image_array= temp_image_val.stream().mapToInt(Integer::intValue).toArray();
        RecycleView(image_array,name_array);
    }

    public List setImage(List nameList){
        List<Integer> resource_val=new ArrayList<Integer>(){{}};
        for (Object temp : nameList) {
            String item=String.valueOf(temp);
            switch (item){
                case "Burger":
                    resource_val.add(R.drawable.burger);
                    break;
                case "Fried Rice":
                    resource_val.add(R.drawable.friedrice);
                    break;
                case "Submarine":
                    resource_val.add(R.drawable.submarine);
                    break;
                case "Pancakes":
                    resource_val.add(R.drawable.pancake);
                    break;
                default:
                    resource_val.add(R.drawable.noimage);
                    break;
            }
        }
        return resource_val;
    }

    public void onNoteClick(int position) {
        Intent intent=new Intent(context, RecipeDetails.class);
        intent.putExtra( EXTRA_MESSAGE,temp_name_list.get(position));
        startActivity(intent);
    }


    public void RecycleView(int[] image_Rvalue_array,String[] name_array){
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        RecyclerViewAdapter recyclerViewAdapter1;
        recyclerView=context.findViewById(R.id.recyclerView);
        layoutManager=new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter1=new RecyclerViewAdapter(image_Rvalue_array,name_array,this::onNoteClick);
        recyclerView.setAdapter(recyclerViewAdapter1);
        recyclerView.setHasFixedSize(true);
    }


    public static int[] addInt(int n, int arr[], int x)
    {
        int i;
        int newarr[] = new int[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
        newarr[n] = x;
        return newarr;
    }

    public static String[] addString(int n, String arr[], String x)
    {
        int i;
        String newarr[] = new String[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
        newarr[n] = x;
        return newarr;
    }
}