package com.example.yelp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Id = "1";


    // TODO: Rename and change types of parameters
    private String Id1;
    List<reviewJson> review_list=new ArrayList<>();




    public ReviewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static ReviewFragment newInstance(List<reviewJson> review_list) {
    public static ReviewFragment newInstance(String id) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString(Id,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Id1 = getArguments().getString(Id);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_review, container, false);
        TextView review_name1=view.findViewById(R.id.review_name1);
        TextView review_rate1=view.findViewById(R.id.review_rate1);
        TextView review_text1=view.findViewById(R.id.review_text1);
        TextView review_time1=view.findViewById(R.id.review_teim1);
        TextView review_name2=view.findViewById(R.id.review_name2);
        TextView review_rate2=view.findViewById(R.id.review_rate2);
        TextView review_text2=view.findViewById(R.id.review_text2);
        TextView review_time2=view.findViewById(R.id.review_teim2);
        TextView review_name3=view.findViewById(R.id.review_name3);
        TextView review_rate3=view.findViewById(R.id.review_rate3);
        TextView review_text3=view.findViewById(R.id.review_text3);
        TextView review_time3=view.findViewById(R.id.review_teim3);



        RequestQueue queue= Volley.newRequestQueue(view.getContext());
        String url = "https://serious-bliss-368206.wl.r.appspot.com/getReview?id="+Id1;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseReview(response);
                review_name1.setText(review_list.get(0).getName());
                review_rate1.setText("Rating:"+review_list.get(0).getRating());
                review_text1.setText(review_list.get(0).getText());
                review_time1.setText(review_list.get(0).getTime());

                review_name2.setText(review_list.get(1).getName());
                review_rate2.setText("Rating:"+review_list.get(1).getRating());
                review_text2.setText(review_list.get(1).getText());
                review_time2.setText(review_list.get(1).getTime());

                review_name3.setText(review_list.get(2).getName());
                review_rate3.setText("Rating:"+review_list.get(2).getRating());
                review_text3.setText(review_list.get(2).getText());
                review_time3.setText(review_list.get(2).getTime());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ooo", "onErrorResponse: !!!!!wrong");
            }
        });
        queue.add(stringRequest);
        return view;
    }
    public void parseReview(String json){
        try{
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<3;i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String text=jsonObject.getString("text");

                String rating=jsonObject.getString("rating");
                rating+="/5";

                String time=jsonObject.getString("time_created").substring(0,10);

                JSONObject user=jsonObject.getJSONObject("user");
                String name=user.getString("name");
                reviewJson reviewjson=new reviewJson(name,rating,text,time);
                review_list.add(reviewjson);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}