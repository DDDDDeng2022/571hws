package com.example.yelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yelp.adapter.RecyclerAdapter3;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class reservationActivity extends AppCompatActivity {

    List<Storage_reservation> storage=new ArrayList<>();
    RecyclerAdapter3 recyclerAdapter3;
    private Paint mClearPaint;
    private ColorDrawable mBackground;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView noresult=findViewById(R.id.no_reservation);
        SharedPreferences pref=getSharedPreferences("MyReservation",0);
        SharedPreferences.Editor editor=pref.edit();
        mBackground = new ColorDrawable();
        backgroundColor = Color.parseColor("#b80f0a");
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteDrawable = ContextCompat.getDrawable(reservationActivity.this, R.drawable.ic_delete);
        intrinsicWidth = deleteDrawable.getIntrinsicWidth();
        intrinsicHeight = deleteDrawable.getIntrinsicHeight();
        ItemTouchHelper.SimpleCallback itemTouchHelper=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar snackbar=Snackbar.make(findViewById(R.id.reminder),"Removing Existing Reservation",Snackbar.LENGTH_LONG);
                snackbar.show();
                editor.remove(storage.get(viewHolder.getAdapterPosition()).getId());
                editor.apply();
                storage.remove(viewHolder.getAdapterPosition());
                if(pref.getAll().size()==0){
                    noresult.setVisibility(View.VISIBLE);
                };
                recyclerAdapter3.notifyDataSetChanged();
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;
                int itemHeight = itemView.getHeight();

                boolean isCancelled = dX == 0 && !isCurrentlyActive;

                if (isCancelled) {
                    clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    return;
                }

                mBackground.setColor(backgroundColor);
                mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                mBackground.draw(c);

                int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                int deleteIconBottom = deleteIconTop + intrinsicHeight;


                deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                deleteDrawable.draw(c);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


            }

            private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
                c.drawRect(left, top, right, bottom, mClearPaint);

            }
            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return 0.7f;
            }
        };

        if(pref.getAll().size()>0){
            noresult.setVisibility(View.GONE);
            RecyclerView reservation_recycler=findViewById(R.id.recycler_reservation);

            Map<String,?> data=pref.getAll();
            String[] key= new String[pref.getAll().size()];
            int i=0;
            for(Map.Entry<String,?>entry:data.entrySet()){
                key[i]=entry.getKey();
                i++;
            }
            for(int position=0;position<pref.getAll().size();position++){
                String tempt=pref.getAll().get(key[position]).toString();
                String value[]=tempt.substring(1,tempt.length()-1).split(",");
                List<String> list= Arrays.asList(value);
                String date="mm-dd-yyyy";
                String time="hh:mm";
                String email="xxx@xx.com";
                String name="xxxxx";
                for(int j=0;j<4;j++){
                    if (list.get(j).contains("2022")){
                        date=list.get(j);
                    }else if(list.get(j).contains(":")){
                        time=list.get(j);
                    }else if(list.get(j).contains("@")){
                        email=list.get(j);
                    }else{
                        name=list.get(j);
                    }
                }

                Storage_reservation storage_item=new Storage_reservation(key[position],email,date,time,name);
                storage.add(storage_item);
            }

            reservation_recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            recyclerAdapter3=new RecyclerAdapter3(this,storage);
            new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(reservation_recycler);
            reservation_recycler.setAdapter(recyclerAdapter3);

            }

        }



    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



}