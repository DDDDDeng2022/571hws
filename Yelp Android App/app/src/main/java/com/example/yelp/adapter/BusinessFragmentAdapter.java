package com.example.yelp.adapter;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.yelp.BusinessActivity;
import java.util.ArrayList;

public class BusinessFragmentAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> myFragmentArrayList= new ArrayList<Fragment>();
    public BusinessFragmentAdapter(@NonNull BusinessActivity businessActivity, ArrayList<Fragment> viewPage2FragmentArrayList) {
        super(businessActivity);
        myFragmentArrayList=viewPage2FragmentArrayList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return myFragmentArrayList.get(position);
    }


    @Override
    public int getItemCount() {
        return myFragmentArrayList.size();
    }
}