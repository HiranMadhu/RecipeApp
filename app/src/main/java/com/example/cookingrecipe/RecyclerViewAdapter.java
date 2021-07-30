package com.example.cookingrecipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    int []arr;
    String []arr2;
    private OnNoteListener monNoteListener;

    public RecyclerViewAdapter(int arr[], String arr2[],OnNoteListener onNoteListener) {
        this.arr = arr;
        this.arr2 =arr2;
        this.monNoteListener=onNoteListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,monNoteListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.gifView.setImageResource(arr[position]);
        holder.textView.setText(arr2[position]);
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView gifView;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            gifView=itemView.findViewById(R.id.gifView);
            this.onNoteListener=onNoteListener;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
