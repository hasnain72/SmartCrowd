package com.example.zafar.sartcrowd.other;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zafar.sartcrowd.Model.Order;
import com.example.zafar.sartcrowd.R;

import java.util.ArrayList;

public class CustomListOrder extends ArrayAdapter<Order> {

    private final Activity context;
    ArrayList<Order> orders = new ArrayList<Order>();

    public CustomListOrder(Activity context, ArrayList<Order> orders ) {
        super(context, R.layout.list_single_order, orders);
        this.context = context;
        this.orders = orders;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_order, null, true);

        TextView OrderNo = (TextView) rowView.findViewById(R.id.order_no);
        TextView CustomerName = (TextView) rowView.findViewById(R.id.customer_name);
        TextView Status = (TextView) rowView.findViewById(R.id.status);
        TextView Amount = (TextView) rowView.findViewById(R.id.order_amount);


        OrderNo.setText("#" + orders.get(position).getOrder_time());
        CustomerName.setText(orders.get(position).getOrder_id());
        Amount.setText("Rs." + orders.get(position).getTotal_price() + "/");

        String status = "";
        if(orders.get(position).getOrder_status().equals("0")) {
            status = "Pending";
        }else if(orders.get(position).getOrder_status().equals("1")){
            status = "Active";
        }else{
            status = "Completed";
        }

        Status.setText(status);
        if(orders.get(position).getOrder_status().equals("1")) {
            Status.setTextColor(Color.parseColor("#39b550"));
        }else if(orders.get(position).getOrder_status().equals("0")){
            Status.setTextColor(Color.parseColor("#FF7F00"));
        }else if(orders.get(position).getOrder_status().equals("2")){
            Status.setTextColor(Color.parseColor("#39b550"));
        }
        //    Status.setText(status[position]);

        return rowView;
    }
}