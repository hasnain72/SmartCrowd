package com.example.zafar.sartcrowd.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zafar on 10/20/2017.
 */

public class Category implements Parcelable{
    public String cat_id;
    public String cat_name;
    public String parent_id;
    public String cat_desc;
    public String cat_image;

    public Category(String cat_id , String cat_name , String parent_id , String cat_desc , String cat_image){
        this.cat_id =  cat_id;
        this.cat_desc = cat_desc;
        this.cat_name = cat_name;
        this.parent_id = parent_id;
        this.cat_image = cat_image;
    }

    //    Getters
    public Category(String cat_id , String cat_name){
        this.cat_id =  cat_id;
        this.cat_name = cat_name;
    }

    public Category(Parcel in) {
        cat_id = in.readString();
        cat_name = in.readString();
        parent_id = in.readString();
        cat_desc = in.readString();
        cat_image = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public Category() {}

    public String getParent_id() {
        return parent_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public String getCat_image() {
        return cat_image;
    }


//    Setters

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setCat_desc(String cat_desc) {
        this.cat_desc = cat_desc;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cat_id);
        parcel.writeString(cat_name);
        parcel.writeString(parent_id);
        parcel.writeString(cat_desc);
        parcel.writeString(cat_image);
    }
}
