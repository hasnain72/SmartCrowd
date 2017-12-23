package com.example.zafar.sartcrowd.Model;

/**
 * Created by Zafar on 10/25/2017.
 */

public class Store {
    public String store_id;
    public String business_user_id;
    public String cat_id;
    public String title;
    public String location;

    public Store(){}
    public Store(String store_id , String business_user_id , String cat_id , String title , String location){
        this.cat_id =  cat_id;
        this.location = location;
        this.store_id = store_id;
        this.title = title;
        this.business_user_id = business_user_id;
    }

//    Getters
    public String getStore_id() {
        return store_id;
    }

    public String getBusiness_user_id() {
        return business_user_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }


//    Setters

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setBusiness_user_id(String business_user_id) {
        this.business_user_id = business_user_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
