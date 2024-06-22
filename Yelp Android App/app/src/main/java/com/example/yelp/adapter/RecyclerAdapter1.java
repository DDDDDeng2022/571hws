package com.example.yelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yelp.BusinessActivity;
import com.example.yelp.R;
import com.example.yelp.resultJson;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.CustomViewHolder> {
    private List<resultJson> result_list;
    private Context context;
    RequestQueue queue;
    private static final String TAG = "RecyclerAdapter";
    List<resultJson> detail_list=new ArrayList<>();
    public RecyclerAdapter1(Context context,List<resultJson> result_list){
        this.context=context;
        this.result_list=result_list;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(context).inflate(R.layout.results,parent,false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position){
        holder.numText.setText(result_list.get(position).getNum());
        Glide.with(holder.imageView.getContext()).load(result_list.get(position).getImage()).into(holder.imageView);
        holder.nameText.setText(result_list.get(position).getName());
        holder.ratesText.setText(result_list.get(position).getRates());
        holder.disText.setText(result_list.get(position).getDistance());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                queue= Volley.newRequestQueue(view.getContext());
                String url = "https://serious-bliss-368206.wl.r.appspot.com/testAPI?id="+result_list.get(position).getId();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(view.getContext(), BusinessActivity.class);

                        intent.putExtra("detailJson",response);
                        intent.putExtra("name",result_list.get(position).getName());
                        view.getContext().startActivity(intent); // start Intent
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ooo", "onErrorResponse: !!!!!wrong");
                    }
                });
                queue.add(stringRequest);
            }
        });

    }


    @Override
    public int getItemCount(){
        return result_list.size();
    }
    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView numText,nameText,ratesText,disText;
        ImageView imageView;
        public CustomViewHolder(View itemView){
            super(itemView);
            numText=itemView.findViewById(R.id.res_num);
            imageView=itemView.findViewById(R.id.res_image);
            nameText=itemView.findViewById(R.id.res_name);
            ratesText=itemView.findViewById(R.id.res_rate);
            disText=itemView.findViewById(R.id.res_dis);

        }


    }

}
