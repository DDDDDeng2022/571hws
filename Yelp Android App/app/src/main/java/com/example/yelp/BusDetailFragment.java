package com.example.yelp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.yelp.adapter.RecyclerAdapter2;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String name="1";
    private static final String address="2";
    private static final String category="3";
    private static final String open="4";
    private static final String price="5";
    private static final String url="6";
    private static final String phone="7";
    private static final String photos="8";
    private static final String id="9";


    private ImageSwitcher imageSwitcher;
    private int  index;
    private  float touchDownX;
    private  float touchUpX;


    // TODO: Rename and change types of parameters
    private String name1;
    private String address1;
    private String category1;
    private String open1;
    private String price1;
    private String url1;
    private String phone1;
    List<String> photo1;
    String id1;
    private String photo1_1;
     EditText email;
     String date;
     String time;
     String email_str;

    public BusDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BusDetailFragment newInstance(String Name,List<DetailJson> detail_list) {
        BusDetailFragment fragment = new BusDetailFragment();
        Bundle args = new Bundle();
        args.putString(address,detail_list.get(0).getAddress());
        args.putString(category,detail_list.get(0).getCategory());
        args.putString(open,detail_list.get(0).getOpen());
        args.putString(phone,detail_list.get(0).getPhone());
        args.putString(price,detail_list.get(0).getPrice());
        args.putString(url,detail_list.get(0).getUrl());

        args.putString(photos,String.join(",",detail_list.get(0).getPhoto()));
        args.putString(id,detail_list.get(0).getId());
        args.putString(name,Name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            address1=getArguments().getString(address);
            category1=getArguments().getString(category);
            open1=getArguments().getString(open);
            phone1=getArguments().getString(phone);
            price1=getArguments().getString(price);
            url1=getArguments().getString(url);
            photo1_1=getArguments().getString(photos);
            photo1= Arrays.asList(photo1_1.split(","));
            name1=getArguments().getString(name);
            id1=getArguments().getString(id);
            Log.e("?","onCreate: "+"     "+address1+"     "+category1+"     "+open1+"     "+phone1+"     "+price1+"     "+url1);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_bus_detail, container, false);
        String none="N/A";
        TextView AddView=view.findViewById(R.id.Address_text);
        AddView.setText(address1);
        TextView PriView=view.findViewById(R.id.Price_text);

        if(price1==""){
            PriView.setText(none);
        }else{
            PriView.setText(price1);
        }

        TextView phoneView=view.findViewById(R.id.Phone_text);

        if(phone1==""){
            phoneView.setText(none);
        }else{
            phoneView.setText(phone1);
        }

        String htmlString_open="<p style=\"color:green;\">Open Now</p>";
        String htmlString_close="<p style=\"color:red;\">Closed</p>";

        Spanned spanned_open= HtmlCompat.fromHtml(htmlString_open,HtmlCompat.FROM_HTML_MODE_COMPACT);
        Spanned spanned_close=HtmlCompat.fromHtml(htmlString_close,HtmlCompat.FROM_HTML_MODE_COMPACT);
        TextView statusView=view.findViewById(R.id.Status_text);
        if (open1=="true"){
            statusView.setText(spanned_open);
        }else{
            statusView.setText(spanned_close);
        }

        TextView cateView=view.findViewById(R.id.category_text);

        if(category1==""){
            cateView.setText(none);
        }else{
            cateView.setText(category1);
        }

        String htmlString_url="<a href=\""+url1+"\">Business Link</a>";
        TextView urlView=view.findViewById(R.id.Url_text);
        urlView.setText(Html.fromHtml(htmlString_url));
        urlView.setMovementMethod(LinkMovementMethod.getInstance());
        RecyclerView recycler_photo=view.findViewById(R.id.recycler_photo);
        recycler_photo.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        RecyclerAdapter2 recyclerAdapter2=new RecyclerAdapter2(getContext(),photo1);
        recycler_photo.setAdapter(recyclerAdapter2);


        return view;

    }
 @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button reserse_button;
        reserse_button=getActivity().findViewById(R.id.reserve_btn);
        reserse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref=getContext().getSharedPreferences("MyReservation",0);
                SharedPreferences.Editor editor=pref.edit();
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.reserver_modal);
                final TextView dialog_title=dialog.findViewById(R.id.modal_title);
                dialog_title.setText(name1);

                EditText select_date=dialog.findViewById(R.id.m_date);
                select_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar=Calendar.getInstance();

                        DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker,int yyyy, int mm, int dd) {
                                select_date.setText(String.format("%d-%d-%d",yyyy,mm+1,dd));
                                date=String.format("%d-%d-%d",yyyy,mm+1,dd);
                                Log.e("当下时间", "onDateSet: "+System.currentTimeMillis());

                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1);
                        datePickerDialog.show();
                    }
                });

                EditText select_time=dialog.findViewById(R.id.m_time);
                select_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar=Calendar.getInstance();
                        TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hh, int mm) {
                                time=String.format("%d:%d",hh,mm);
                                select_time.setText(time);


                            }
                        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
                        timePickerDialog.show();
                    }
                });
                Button dialog_submit=(Button) dialog.findViewById(R.id.modal_submit);
                Button dialog_cancel=(Button) dialog.findViewById(R.id.modal_cancel);
                dialog_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email=dialog.findViewById(R.id.email);
                        email_str=email.getText().toString();
                        if(email_str==null || Patterns.EMAIL_ADDRESS.matcher(email_str).matches()==false){
                            Toast.makeText(getContext(), "InValid Email Address", Toast.LENGTH_SHORT).show();
                        }else if(time.substring(0,2).contains(":")){
                            Toast.makeText(getContext(), "Time should be between 10AM AND 5PM", Toast.LENGTH_SHORT).show();
                        }
                        else if(Integer.parseInt(time.substring(0,2))>17){
                            Toast.makeText(getContext(), "Time should be between 10AM AND 5PM", Toast.LENGTH_SHORT).show();

                        }else{
                            Set<String> set= new HashSet<>();
                            set.add(name1);
                            set.add(date);
                            set.add(time);
                            set.add(email_str);
                            SharedPreferences pref=getContext().getSharedPreferences("MyReservation",0);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putStringSet(id1,set);
                            Toast.makeText(getContext(), "Reservation Booked", Toast.LENGTH_SHORT).show();
                            editor.apply();
                        }

                        dialog.dismiss();

                    }
                });
                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
 }

}