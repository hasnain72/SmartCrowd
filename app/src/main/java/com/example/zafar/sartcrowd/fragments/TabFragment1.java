package com.example.zafar.sartcrowd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.Cart;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.activity.ProductDetails;
import com.example.zafar.sartcrowd.other.CustomList;
import com.example.zafar.sartcrowd.Model.Product;

import java.util.ArrayList;
import java.util.List;


public class TabFragment1 extends Fragment {
    ArrayList<Product> products = new ArrayList<Product>();
    ArrayList<Product> new_products = new ArrayList<Product>();
    ListView list;
    String strtext;


    private OnFragmentInteractionListener mListener;

    public TabFragment1() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        strtext = getArguments().getString("cat_id");
        if(strtext != null){
            Log.d("STRTXT" , strtext);
            products = getArguments().getParcelableArrayList("products");
            Log.d("STRTXT SIZE" ,""+ products.size());

            new_products.clear();
            for(int i=0; i<products.size(); i++){
                if(products.get(i).getCat_id().equals(strtext) && products.get(i).getStatus().equals("1")){
                    new_products.add(products.get(i));
                }
            }
        }

        return inflater.inflate(R.layout.tab_fragment1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list=(ListView) getView().findViewById(R.id.list);

        CustomList adapter = new CustomList(getActivity(), new_products);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getActivity(), "You Clicked at " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), ProductDetails.class );
                startActivity(i);
            }
        });

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
