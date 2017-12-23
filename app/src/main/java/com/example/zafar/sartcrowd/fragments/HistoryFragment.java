package com.example.zafar.sartcrowd.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zafar.sartcrowd.Model.Order;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.SharedPreference.SessionManager;
import com.example.zafar.sartcrowd.other.OnGetDataListener;
import com.example.zafar.sartcrowd.other.PagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    String customer_id;
    ProgressDialog Dialog;
    SessionManager session;
    ArrayList<Order> orders = new ArrayList<Order>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getActivity());
        customer_id = session.getUserDetails().get("user_id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ReadDatabase(new OnGetDataListener() {
            @Override
            public void onStart() {
                Dialog = new ProgressDialog(getActivity());
                Dialog.setCanceledOnTouchOutside(false);
                Dialog.setMessage("Loading Order Details");
                Dialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {

                TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tab_layout_product);
                tabLayout.addTab(tabLayout.newTab().setText("Active"));
                tabLayout.addTab(tabLayout.newTab().setText("Pending"));
                tabLayout.addTab(tabLayout.newTab().setText("History"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                final ViewPager viewPager = (ViewPager) getView().findViewById(R.id.pager);
                final PagerAdapter adapter = new PagerAdapter
                        (getActivity().getSupportFragmentManager(), tabLayout.getTabCount() , orders);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                Dialog.dismiss();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {}
        });
    }

    public void ReadDatabase(final OnGetDataListener listener){
        listener.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ProductTableDataReference = databaseReference.child("order");
        Query query = ProductTableDataReference.orderByChild("customer_id").equalTo(customer_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order single_order = postSnapshot.getValue(Order.class);
                    orders.add(single_order);
                }
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }

        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
