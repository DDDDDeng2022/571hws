package com.example.yelp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yelp.R;
import com.example.yelp.Storage_reservation;

import java.util.List;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.CustomViewHolder> {
    private List<Storage_reservation> storage;
    private Context context;

    private static final String TAG = "RecyclerAdapter3";
    public RecyclerAdapter3(Context context, List<Storage_reservation> storage){
        this.context=context;
        this.storage=storage;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(context).inflate(R.layout.reserve_recycler,parent,false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position){
        int index=position+1;
        holder.num.setText(Integer.toString(index));
        holder.date.setText(storage.get(position).getDate());
        holder.time.setText(storage.get(position).getTime());
        holder.email.setText(storage.get(position).getEmail());
        holder.name.setText(storage.get(position).getName());



    }


    @Override
    public int getItemCount(){
        return storage.size();
    }
    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView num,name,date,time,email;
        public CustomViewHolder(View itemView){
            super(itemView);
            num=itemView.findViewById(R.id.storage_num);
            name=itemView.findViewById(R.id.storage_name);
            date=itemView.findViewById(R.id.storage_date);
            time=itemView.findViewById(R.id.storage_time);
            email=itemView.findViewById(R.id.storage_email);
        }


    }
}
