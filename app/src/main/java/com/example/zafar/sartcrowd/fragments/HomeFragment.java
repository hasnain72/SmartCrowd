package com.example.zafar.sartcrowd.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zafar.sartcrowd.Model.Category;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.activity.OrderDetails;
import com.example.zafar.sartcrowd.other.CustomGridHome;
import com.example.zafar.sartcrowd.other.MySingleton;
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


public class HomeFragment extends Fragment {

    ListView list;
    ArrayAdapter<String> arrayAdapter = null;
    ArrayList<Category> category = new ArrayList<Category>();
    ProgressDialog Dialog;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog = new ProgressDialog(getActivity());
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.setMessage("Loading Category");
        Dialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ProductTableDataReference = databaseReference.child("Categories");
        Query query = ProductTableDataReference.orderByChild("parent_id").equalTo("0");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> arrayList = new  ArrayList<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Category cat = postSnapshot.getValue(Category.class);
                    category.add(cat);
                    arrayList.add(cat.getCat_name());
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayList);
                arrayAdapter.notifyDataSetChanged();

                Dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = (ListView) getView().findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

    //            Toast.makeText(getActivity(), "you selected at" + category.get(position).getCat_id() + " " + category.get(position).getCat_name(), Toast.LENGTH_SHORT).show();
            //    list.startAnimation(inFromLeftAnimation());

                Dialog = new ProgressDialog(getActivity());
                Dialog.setCanceledOnTouchOutside(false);
                Dialog.setMessage("Loading Category");
                Dialog.show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ProductTableDataReference = databaseReference.child("Categories");
                Query query = ProductTableDataReference.orderByChild("parent_id").equalTo(category.get(position).getCat_id().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            searching_cat(category.get(position).getCat_id());
                        }else{
                            Fragment fragment = new MapPlusStore();
                            Bundle args = new Bundle();
                            args.putParcelable("cat_id" , category.get(position));                            fragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                            fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("my_fragment");
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                        Dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });
            }
        });
    }

//    private Animation inFromLeftAnimation() {
//        Animation inFromLeft = new TranslateAnimation(
//                Animation.RELATIVE_TO_PARENT, -1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f);
//        inFromLeft.setDuration(900);
//        inFromLeft.setInterpolator(new AccelerateInterpolator());
//        return inFromLeft;
//    }

    public void searching_cat(final String cat_id){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ProductTableDataReference = databaseReference.child("Categories");
        Query query = ProductTableDataReference.orderByChild("parent_id").equalTo(cat_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                category.clear();

                ArrayList<String> arrayList = new  ArrayList<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Category cat = postSnapshot.getValue(Category.class);
                    category.add(cat);
                    arrayList.add(cat.getCat_name());
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayList);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}