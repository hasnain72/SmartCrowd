package com.example.zafar.sartcrowd.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zafar on 10/21/2017.
 */

public class Product implements Parcelable {
    String product_id;
    String store_id;
    String cat_id;
    String product_name;
    String description;
    String price;
    String image;
    String status;

    public Product(){}

    public Product(String product_id ,  String store_id,String cat_id,String product_name,String description,String price,String image,String status){
        this.product_id = product_id;
        this.price = price;
        this.store_id = store_id;
        this.cat_id = cat_id;
        this.product_name = product_name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.status = status;
    }

//    Getters

    public String getProduct_id() {
        return product_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

//    Setters

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
