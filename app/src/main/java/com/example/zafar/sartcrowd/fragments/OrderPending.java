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
import android.widget.ListView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Model.Order;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.activity.OrderDetails;
import com.example.zafar.sartcrowd.other.CustomListOrder;

import java.util.ArrayList;

public class OrderPending extends Fragment {

    ListView list;
    ArrayList<Order> orders = new ArrayList<Order>();
    ArrayList<Order> new_orders = new ArrayList<Order>();
    int[] orderNo = {
            1213,
            1212,
            1211,
            1210
    } ;
    String[] customerName = {
            "Betty Sheldon",
            "Fransisca Austin",
            "Oscar Clausing",
            "Cynthia Back"
    } ;
    String[] status = {
            "Pending",
            "Pending",
            "Pending",
            "Pending"
    } ;
    int[] amount = {
            250,
            500,
            180,
            750
    } ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_orders_blank, container, false);
        orders = getArguments().getParcelableArrayList("orders");
        new_orders.clear();
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getOrder_status().equals("0")){
                new_orders.add(orders.get(i));
            }
        }
        if(new_orders.isEmpty()){
            return inflater.inflate(R.layout.fragment_orders_blank, container, false);
        }else{
            return inflater.inflate(R.layout.fragment_order_active, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!new_orders.isEmpty()) {
            CustomListOrder adapter = new
                    CustomListOrder(getActivity(), new_orders);
            list = (ListView) getView().findViewById(R.id.list_order);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    switch (position) {
                        case 0: {
//                        Toast.makeText(getActivity(), "You Clicked at Customer" , Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), OrderDetails.class);
                            startActivity(i);
                        }
                    }

                }
            });
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
