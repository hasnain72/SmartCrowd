package com.example.zafar.sartcrowd.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zafar.sartcrowd.Model.Category;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.other.MySingleton;
import com.example.zafar.sartcrowd.other.OnGetDataListener;
import com.example.zafar.sartcrowd.other.PagerAdapterProduct;
import com.example.zafar.sartcrowd.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFragment extends Fragment {

    String store_id;
    ArrayList<Product> products = new ArrayList<Product>();
    TabLayout tabLayout;
    ArrayList<Category> category_list = new ArrayList<Category>();
    DatabaseReference databaseReference;
    static int count=0;
    ProgressDialog Dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        store_id = getArguments().getString("store_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.checkout, menu);  // Use filter.xml from step 1
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = (TabLayout) getView().findViewById(R.id.tab_layout_product);

        Dialog = new ProgressDialog(getActivity());
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.setMessage("Loading Products");
        Dialog.show();

        ReadDatabase(new OnGetDataListener() {

            @Override
            public void onStart() {
                Toast.makeText(getActivity(), "Start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                //            Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();

                tabLayout.removeAllTabs();
                for(int i=0 ; i<category_list.size() ; i++){
                    tabLayout.addTab(tabLayout.newTab().setText(category_list.get(i).getCat_name()));
                }
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                final ViewPager viewPager = (ViewPager) getView().findViewById(R.id.pager);
                final PagerAdapterProduct adapter = new PagerAdapterProduct(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());


//                    Setting Content for each Tab
                for(int i=0;i<category_list.size();i++){
                    final Category category = category_list.get(i);
                    TabFragment1 fragment = new TabFragment1();
                    Bundle args = new Bundle();
                    args.putString("cat_id", category.getCat_id());
                    args.putParcelableArrayList("products" , products);
                    Log.d("kuku" , " "+i + " Products" + products.size());
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().commit();
                    adapter.addFrag(fragment);
                    adapter.notifyDataSetChanged();

                }

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
            public void onFailed(DatabaseError databaseError) {

            }

        });

    }

    public void ReadDatabase(final OnGetDataListener listener){
        listener.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ProductTableDataReference = databaseReference.child("product");
        Query query = ProductTableDataReference.orderByChild("store_id").equalTo(store_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // DataSnapshot is only for data read from Firebase DB
                if (dataSnapshot.exists()) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Product product = postSnapshot.getValue(Product.class);
                    products.add(product);

                    DatabaseReference CategoryTableDataReference = databaseReference.child("Categories");
                    Query query = CategoryTableDataReference.orderByChild("cat_id").equalTo(product.getCat_id());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Category category = postSnapshot.getValue(Category.class);
                                if (count == 0) {
                                    category_list.add(0, category);
                                    //        Toast.makeText(getActivity(), "Count == 0", Toast.LENGTH_LONG).show();
                                } else {
                                    //          Toast.makeText(getActivity(), "Count != 0", Toast.LENGTH_LONG).show();
                                    int check = 0;
                                    for (int i = 0; i < category_list.size(); i++) {
                                        if (category_list.get(i).getCat_id().equals(category.getCat_id())) {
                                            //    Toast.makeText(getActivity(), "Do Nothing", Toast.LENGTH_LONG).show();
                                            check = 1;
                                        }
                                    }
                                    if (check == 0) {
                                        category_list.add(category);
//                                    Toast.makeText(getActivity(), "Adding"+category.getCat_name(), Toast.LENGTH_LONG).show();
                                    }
                                }
                                count++;
                            }
                            listener.onSuccess(dataSnapshot);
                            //                      Toast.makeText(getActivity(), "BeforeSize !: " + category_list2.size(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }
            }else{
                    Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                    Dialog.dismiss();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_checkout) {
            Toast.makeText(getActivity() , "Clicked" , Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Product");

//        Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.cast_ic_notification_1);
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOverflowIcon(drawable);

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
