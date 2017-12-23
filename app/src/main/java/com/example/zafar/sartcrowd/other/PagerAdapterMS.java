package com.example.zafar.sartcrowd.other;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.example.zafar.sartcrowd.Model.Category;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.fragments.MapFragment;
import com.example.zafar.sartcrowd.fragments.StoreFragment;
import com.example.zafar.sartcrowd.fragments.TabFragment1;

public class PagerAdapterMS extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment fragment = null;
    Category single_category;
    FragmentManager fm;

    public PagerAdapterMS(FragmentManager fm, int NumOfTabs , Category single_category) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.single_category = single_category;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :{
                Fragment mapFragment = new MapFragment();
                Bundle args = new Bundle();
                args.putString("cat_id" , single_category.getCat_id());
                mapFragment.setArguments(args);
             //   fm.beginTransaction().commit();

                return mapFragment;
            }
            case 1 :{
                Fragment storeFragment = new StoreFragment();
                Bundle args = new Bundle();
                args.putString("cat_id" , single_category.getCat_id());
                storeFragment.setArguments(args);
                return storeFragment;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}