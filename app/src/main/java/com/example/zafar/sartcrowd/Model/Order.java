package com.example.zafar.sartcrowd.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zafar on 10/20/2017.
 */

public class Order implements Parcelable{
    public String order_id;
    public String customer_id;
    public String business_id;
    public String order_status;
    public String discount;
    public String total_price;
    public String no_of_products;
    public String resource_pid;
    public String order_time;
    public String delivery_time;
    public String customer_token;
    public String order_location;
    public String store_location;

    public Order(){}
    public Order(String order_id , String customer_id , String business_id , String order_status , String discount, String total_price, String no_of_products, String resource_pid, String order_time, String delivery_time , String customer_token , String order_location, String store_location){
        this.order_id =  order_id;
        this.customer_id = customer_id;
        this.business_id = business_id;
        this.order_status = order_status;
        this.discount = discount;
        this.total_price = total_price;
        this.no_of_products = no_of_products;
        this.resource_pid = resource_pid;
        this.order_time = order_time;
        this.delivery_time = delivery_time;
        this.customer_token= customer_token;
        this.order_location = order_location;
        this.store_location = store_location;
    }

//    Getters

    public String getOrder_id() {
        return order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getDiscount() {
        return discount;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getNo_of_products() {
        return no_of_products;
    }

    public String getResource_pid() {
        return resource_pid;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public String getCustomer_token() {
        return customer_token;
    }

    public String getOrder_location() {return order_location;}

    public String getStore_location() {return store_location;}

//    Setters

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public void setNo_of_products(String no_of_products) {
        this.no_of_products = no_of_products;
    }

    public void setResource_pid(String resource_pid) {
        this.resource_pid = resource_pid;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public void setCustomer_token(String customer_token) {
        this.customer_token = customer_token;
    }

    public void setOrder_location(String order_location) {this.order_location = order_location;}

    public void setStore_location(String store_location) {this.store_location = store_location;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}

