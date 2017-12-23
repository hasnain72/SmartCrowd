package com.example.zafar.sartcrowd.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.zafar.sartcrowd.fragments.TabFragment1;

import java.util.ArrayList;


public class PagerAdapterProduct extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment fragment = null;
    ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();

    public PagerAdapterProduct(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

//        for (int i = 0; i < mNumOfTabs ; i++) {
//            if (i == position) {
//                fragment = new TabFragment1();
//                break;
//            }
//        }
        Log.d("poso" , "" + position);
        return mFragmentList.get(position);

    }

    public void addFrag(Fragment fragment) {
        Log.d("Frago" , "Added");
        mFragmentList.add(fragment);
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}