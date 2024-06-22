package com.example.yelp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yelp.R;
import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.CustomViewHolder> {
    private  List<String> photo;
    private Context context;
    private static final String TAG = "RecyclerAdapter2";
    public RecyclerAdapter2(Context context, List<String> photo){
        this.context=context;
        this.photo=photo;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(context).inflate(R.layout.photos,parent,false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position){
        Glide.with(holder.imageView.getContext()).load(photo.get(position)).into(holder.imageView);

    }


    @Override
    public int getItemCount(){
        return photo.size();
    }
    class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public CustomViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.photos_recycler);
        }


    }

}
