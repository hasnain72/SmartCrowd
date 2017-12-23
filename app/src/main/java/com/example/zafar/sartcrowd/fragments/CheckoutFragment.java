package com.example.zafar.sartcrowd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.Cart;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.activity.MainActivity;
import com.example.zafar.sartcrowd.activity.OrderConfirm;
import com.example.zafar.sartcrowd.activity.OrderDetails;
import com.example.zafar.sartcrowd.other.CustomListCheckout;

import java.util.ArrayList;
import java.util.List;


public class CheckoutFragment extends Fragment {

    ListView list;
    List<Cart> cartValues = new ArrayList<>();
    ArrayList<Cart> carts;

    private OnFragmentInteractionListener mListener;

    public CheckoutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity) getActivity()).initializeCountDrawer();
        carts = new ArrayList<Cart>();

        cartValues = new Database(getActivity()).getCarts();
        int total=0 ,count = 0;
        for(Cart cart:cartValues) {
            count ++;
            total += (Integer.parseInt(cart.getPrice())) * (Integer.parseInt(cart.getQuantity()));
            carts.add(cart);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button proceed_btn =  (Button) getView().findViewById(R.id.Proceed);
        proceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(getActivity() , OrderConfirm.class);
                    startActivity(i);
            }
        });
        CustomListCheckout adapter = new
                CustomListCheckout(getActivity(), carts);
        list=(ListView) getView().findViewById(R.id.list_checkout);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " +carts.get(position).getProductName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
