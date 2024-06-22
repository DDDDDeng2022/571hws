package com.example.yelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Yelp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread myThread=new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000);
                    Intent it=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}