package com.example.yelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yelp.adapter.RecyclerAdapter1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "form";
    private Button submit, clear;
    CheckBox checked;
    EditText distance, location;
    AutoCompleteTextView keyword;
    TextView no_results;
    Spinner spinner;
    RequestQueue queue;
    String address;
    RecyclerView recycler;
    ArrayAdapter<String> adapter_auto;
    List<String> tempt;
    String[] autocomplete={};
    List<resultJson> result_list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.cate);
        String[] category = getResources().getStringArray(R.array.cate);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        submit = findViewById(R.id.submit);
        clear = findViewById(R.id.clear);
        checked = findViewById(R.id.checkBox);
        keyword =(AutoCompleteTextView) findViewById(R.id.keyword);
        distance = findViewById(R.id.distance);
        location = findViewById(R.id.location);
        no_results=findViewById(R.id.no_results);
        no_results.setVisibility(View.INVISIBLE);
        recycler = findViewById(R.id.recycler);

        if(keyword==null){return;}
        keyword.setThreshold(1);

        submit.setOnClickListener(this);
        clear.setOnClickListener(this);
        queue=Volley.newRequestQueue(this);
        recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String url = "https://serious-bliss-368206.wl.r.appspot.com/getComplete?text="+keyword.getText();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "自动 " + response);
                        try{
                            JSONArray jsonArray=new JSONArray(response);
                            List<String> tempt=new ArrayList<String>();
                            for(int i=0;i<jsonArray.length();i++){
                                    tempt.add(jsonArray.getString(i));
                            }
                            autocomplete=tempt.toArray(new String[tempt.size()]);
                            ArrayAdapter adapter_auto=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,autocomplete);
                            keyword.setAdapter(adapter_auto);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "no ip");
                    }
                });
                queue.add(stringRequest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (checked.isChecked() == true) {
                    location.setVisibility(View.INVISIBLE);
                    String url = "https://ipinfo.io/?token=5d1e1011c371ca";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            analyzeJsonAutoDetect(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "no ip");
                        }
                    });
                    queue.add(stringRequest);
                } else {
                    location.setVisibility((View.VISIBLE));

                }
            }
        });




    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.submit) {

            if(TextUtils.isEmpty(keyword.getText())){
                    keyword.setError("This field is required");
            }
            else if( TextUtils.isEmpty(distance.getText())) {
                distance.setError("This field is required");
            }
            else if(TextUtils.isEmpty(location.getText())&&checked.isChecked()==false){
                    location.setError("This field is required");
            }
            else{
                Log.e(TAG, "onClick: 输出检查keyword"+keyword.getText());
                if(checked.isChecked()==false){
                    address=location.getText().toString();
                }
                Log.e(TAG, "onClick: " + "https://serious-bliss-368206.wl.r.appspot.com/getYelp?keyword=" + keyword.getText() + "&distance=" + distance.getText() + "&category=" + spinner.getSelectedItem() + "&location=" + location.getText().toString() + "&autoChecked=" + checked.isChecked());
                String url = "https://serious-bliss-368206.wl.r.appspot.com/getYelp?keyword=" + keyword.getText() + "&distance=" + distance.getText() + "&category=" + spinner.getSelectedItem() + "&location=" + address + "&autoChecked=" + checked.isChecked();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length()==2){
                            no_results.setVisibility(View.VISIBLE);
                        }else{
                            result_list=new ArrayList<>();
                            RecyclerAdapter1 recyclerAdapter1=new RecyclerAdapter1(MainActivity.this,result_list);
                            recycler.setAdapter(recyclerAdapter1);
                            parseResults(response);
                            recyclerAdapter1=new RecyclerAdapter1(MainActivity.this,result_list);
                            recycler.setAdapter(recyclerAdapter1);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: !!!!!wrong");
                    }
                });
                queue.add(stringRequest);
            }


            }
            else if (id == R.id.clear) {
                spinner.setSelection(0);
                checked.setChecked(false);
                keyword.setText(null);
                distance.setText(null);
                location.setText(null);
                result_list=new ArrayList<>();
                no_results.setVisibility(View.INVISIBLE);
                RecyclerAdapter1 recyclerAdapter1=new RecyclerAdapter1(MainActivity.this,result_list);
                recycler.setAdapter(recyclerAdapter1);


            }


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reserve_icon) {
            Intent intent = new Intent(MainActivity.this, reservationActivity.class);
            startActivity(intent);
            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void analyzeJsonAutoDetect(String json){
        try{
            JSONObject jsonObject=new JSONObject(json);
            address=jsonObject.getString("loc");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void parseResults(String json){
        try{
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<10;i++){
                String image=jsonArray.getJSONObject(i).getString("image_url");

                String name=jsonArray.getJSONObject(i).getString("name");
                String rates=jsonArray.getJSONObject(i).getString("rating");
                String distance=jsonArray.getJSONObject(i).getString("distance");
                int dis_int=Double.valueOf(distance).intValue()/1600;
                distance=String.valueOf(dis_int);
                String id=jsonArray.getJSONObject(i).getString("id");
                resultJson resultjson=new resultJson(String.valueOf(i+1),image,name,rates,distance,id);
                result_list.add(resultjson);
                Log.e(TAG, "parseResults: 检查results_list"+result_list );
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}