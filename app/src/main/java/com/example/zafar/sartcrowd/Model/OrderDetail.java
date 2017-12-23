package com.example.zafar.sartcrowd.Model;

/**
 * Created by Zafar on 11/16/2017.
 */

public class OrderDetail {
    String id;
    String order_id;
    String product_id;
    String product_quantity;

    public OrderDetail(String id, String order_id, String product_id, String product_quantity) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.product_quantity = product_quantity;
    }

//    Getters

    public String getId() {
        return id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

//    Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }
}
