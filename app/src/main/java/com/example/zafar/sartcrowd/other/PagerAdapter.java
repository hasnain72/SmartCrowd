package com.example.zafar.sartcrowd.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.zafar.sartcrowd.Model.Order;
import com.example.zafar.sartcrowd.fragments.OrderActive;
import com.example.zafar.sartcrowd.fragments.OrderCompleted;
import com.example.zafar.sartcrowd.fragments.OrderPending;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment fragment = null;
    ArrayList<Order> orders;
    FragmentManager fm;

    public PagerAdapter(FragmentManager fm, int NumOfTabs , ArrayList<Order> orders) {
        super(fm);
        this.fm = fm;
        this.mNumOfTabs = NumOfTabs;
        this.orders = orders;
    }

    @Override
    public Fragment getItem(int position) {

      switch(position){
          case 0 :{
              Fragment f2 = new OrderActive();
              Bundle args = new Bundle();
              args.putParcelableArrayList("orders" , orders);
              f2.setArguments(args);
              fm.beginTransaction().commit();
              return f2;
          }
          case 1 : {
              Fragment f2 = new OrderPending();
              Bundle args = new Bundle();
              args.putParcelableArrayList("orders" , orders);
              f2.setArguments(args);
              fm.beginTransaction().commit();
              return f2;

          }
          case 2 : {
              Fragment f2 = new OrderCompleted();
              Bundle args = new Bundle();
              args.putParcelableArrayList("orders" , orders);
              f2.setArguments(args);
              fm.beginTransaction().commit();
              return f2;

          }
      }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}