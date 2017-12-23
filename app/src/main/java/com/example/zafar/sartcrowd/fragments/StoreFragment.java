package com.example.zafar.sartcrowd.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.PwithC;
import com.example.zafar.sartcrowd.Model.Store;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.activity.ProductDetails;
import com.example.zafar.sartcrowd.other.CustomList;
import com.example.zafar.sartcrowd.other.CustomListStore;
import com.example.zafar.sartcrowd.other.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreFragment extends Fragment {

    String cat_id;
    ListView list;
    ProgressDialog Dialog;
    ArrayList<Store> store = new ArrayList<Store>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cat_id = getArguments().getString("cat_id");
     //   Toast.makeText(getActivity(), txt , Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReadDataBase(new OnGetDataListener() {
            @Override
            public void onStart() {
                Dialog = new ProgressDialog(getActivity());
                Dialog.setCanceledOnTouchOutside(false);
                Dialog.setMessage("Loading Store List");
                Dialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                CustomListStore adapter = new CustomListStore(getActivity(), store);
                list=(ListView) getView().findViewById(R.id.list_store);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Bundle arg = new Bundle();
                        arg.putString("store_id" , store.get(position).getStore_id());
                        Fragment fragment = new ProductFragment();
                        fragment.setArguments(arg);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commitAllowingStateLoss();


//                Set Preference For Store
                        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("StorePref" , 0);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.clear(); editor.commit();
                        new Database(getActivity()).cleanCart();
                        for(int i=0; i<store.size();i++){
                            if(store.get(position).getStore_id().equals(store.get(i).getStore_id())){
                                editor.putString("store_id", store.get(i).getStore_id());
                                editor.putString("business_user_id", store.get(i).getBusiness_user_id());
                                editor.commit();
                            }
                        }
                    }
                });
                Dialog.dismiss();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

    }

    public void ReadDataBase(final OnGetDataListener listener){
        listener.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference StoreWithCatDataReference = databaseReference.child("store_with_cat");
        Query query = StoreWithCatDataReference.orderByChild("cat_id").equalTo(cat_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        PwithC pc = postSnapshot.getValue(PwithC.class);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference StoreTableDataReference = databaseReference.child("store");
                        Query query = StoreTableDataReference.orderByChild("store_id").equalTo(pc.getStore_id());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Store single_store = postSnapshot.getValue(Store.class);
                                    store.add(single_store);
                                }
                                listener.onSuccess(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }else{
                    Dialog.dismiss();
                    Toast.makeText(getActivity(), "No Store Found", Toast.LENGTH_SHORT).show();
                }

            }
            @Override public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

}
