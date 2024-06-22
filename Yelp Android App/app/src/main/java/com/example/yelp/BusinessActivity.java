package com.example.yelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yelp.adapter.BusinessFragmentAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BusinessActivity extends AppCompatActivity{
    private static final String TAG = "BusinessActivity";
    List<DetailJson> detail_list = new ArrayList<>();
    List<reviewJson> review_list=new ArrayList<>();
    private ArrayList<Fragment> viewPage2FragmentArrayList = new ArrayList<Fragment>();
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    String[] title={"BUSINESS DETAILS","MAP LOCATION","REVIEWS"};
    String store_name;


//    RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        parseDetail(intent.getStringExtra("detailJson"));
//        parseReview(intent.getStringExtra("reviewJson"));
        setTitle(intent.getStringExtra("name"));
        store_name=intent.getStringExtra("name");
        tabLayout=findViewById(R.id.tab_layout);
        viewPager2=findViewById(R.id.pager);
        viewPage2FragmentArrayList.add(BusDetailFragment.newInstance(intent.getStringExtra("name"),detail_list));
        MapsFragment mapsFragment=new MapsFragment(detail_list.get(0).getLatitude(),detail_list.get(0).getLongitude());
        viewPage2FragmentArrayList.add(mapsFragment);
//        viewPage2FragmentArrayList.add(ReviewFragment.newInstance(review_list));
        viewPage2FragmentArrayList.add(ReviewFragment.newInstance(detail_list.get(0).getId()));
        BusinessFragmentAdapter businessFragmentAdapter=new BusinessFragmentAdapter(this,viewPage2FragmentArrayList);
        viewPager2.setAdapter(businessFragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(title[position]);
            }
        }).attach();

//        tv.setText(intent.getStringExtra("name")+"     "+detail_list.get(0).getAddress()+"          "+detail_list.get(0).getCategory()+"          "+detail_list.get(0).getOpen()+"          "+detail_list.get(0).getPhone()+"          "+detail_list.get(0).getPrice()+"          "+detail_list.get(0).getUrl());

    }


@Override
public boolean onSupportNavigateUp(){
        finish();
        return true;
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.face_icon) {
            Intent facebook=new Intent();
            facebook.setAction(Intent.ACTION_VIEW);
            facebook.addCategory(Intent.CATEGORY_BROWSABLE);
            facebook.setData(Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+detail_list.get(0).getUrl()+"&quote=20%"));
            startActivity(facebook);
        return true;
        }else if(id==R.id.twitter_icon){
            Intent twitter=new Intent();
            twitter.setAction(Intent.ACTION_VIEW);
            twitter.addCategory(Intent.CATEGORY_BROWSABLE);
            twitter.setData(Uri.parse("https://twitter.com/intent/tweet?text=Check%20"+detail_list.get(0).getName()+"%20on%20Yelp.&url="+detail_list.get(0).getUrl()));
            startActivity(twitter);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void parseDetail(String json){
        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONObject location=new JSONObject(jsonObject.getString("location"));
            String id=jsonObject.getString("id");
            String temp_address=(location.optString("display_address")).replace("\"","");
            String address=temp_address.substring(1,temp_address.length()-1);
            Log.e(TAG, "parseDetail: "+address );
            String category="";
            JSONArray categories=new JSONArray(jsonObject.optString("categories"));
            for (int i=0;i<categories.length();i++){
                if (i<categories.length()-1){
                    category+=categories.getJSONObject(i).optString("title")+" | ";
                }else{
                    category+=categories.getJSONObject(i).optString("title");
                }
            }
            String phone=jsonObject.optString("display_phone");
            String price=jsonObject.optString("price");;
            JSONArray hours=new JSONArray(jsonObject.optString("hours"));
            String open=hours.getJSONObject(0).optString("is_open_now");
            String url=jsonObject.getString("url");

            JSONArray jsonArray_photo=new JSONArray(jsonObject.optString("photos"));
            Log.e(TAG, "parseDetail:jsonArray_photo "+jsonArray_photo );
            Log.e(TAG, "onResponse:jsonArray_photo "+jsonArray_photo.length() );
            List<String> tempt_photo=new ArrayList<String>();
            for(int i=0;i<jsonArray_photo.length();i++){
                tempt_photo.add(jsonArray_photo.getString(i));
//                                autocomplete+={jsonArray.getString(i)}

                    Log.e(TAG, "onResponse: "+tempt_photo);
                }
//            String photo[]=tempt_photo.toArray(new String[tempt_photo.size()]);



//            String temp_photo=jsonObject.optString("photos");
//            Log.e(TAG, "parseDetail: "+temp_photo.substring(1,temp_photo.length()-1));
//            temp_photo=temp_photo.replace("\"","");
//            temp_photo=temp_photo.replace("\\","");
//            String photo[]=(temp_photo.substring(1,temp_photo.length()-1)).split(",");
            double longitude;
            double latitude;
            JSONObject coordinates=new JSONObject(jsonObject.optString("coordinates"));
            longitude=coordinates.getDouble("longitude");

            latitude=coordinates.getDouble("latitude");
            Log.e(TAG, "parseDetail:经纬度："+latitude+longitude );
            DetailJson detailJson=new DetailJson(id,address,category, phone, price,open, url,tempt_photo,latitude,longitude,store_name);
            detail_list.add(detailJson);
    }catch (
    JSONException e){
        e.printStackTrace();
    }
}

    public void parseReview(String json){
        try{
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<3;i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String text=jsonObject.getString("text");
                Log.e("REVIEW", "text "+text);

                String rating=jsonObject.getString("rating");
                rating+="/5";
                Log.e("REVIEW", "rating "+rating);

                String time=jsonObject.getString("time_created");
                Log.e("REVIEW", "time "+time);

                JSONObject user=jsonObject.getJSONObject("user");
                String name=user.getString("name");
                Log.e("REVIEW", "name"+name);
                reviewJson reviewjson=new reviewJson(name,rating,text,time);
                review_list.add(reviewjson);
                Log.e("review", "parseResults: 检查review_list"+review_list);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
