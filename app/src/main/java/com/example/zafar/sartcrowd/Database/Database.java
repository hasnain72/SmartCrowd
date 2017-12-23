package com.example.zafar.sartcrowd.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.zafar.sartcrowd.Model.Cart;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="Cart.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    public List<Cart> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName","ProductId","Quantity","Price","Discount"};
        String sqlTable = "CartDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

         final List<Cart> result = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                result.add(new Cart(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }
    //Adding and Updating Items
    public void addToCart(Cart cart) {

        SQLiteDatabase db = getReadableDatabase();
        String sqlTable = "CartDetail";
        String Query = "Select * from CartDetail where ProductId = '" + cart.getProductId() +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            String query = String.format("INSERT INTO CartDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                    cart.getProductId(),
                    cart.getProductName(),
                    cart.getQuantity(),
                    cart.getPrice(),
                    cart.getDiscount()
            );
            db.execSQL(query);
        }
        else {
            cursor.moveToFirst();
            ContentValues data=new ContentValues();
            String quantity = cursor.getString(cursor.getColumnIndex("Quantity"));
            int q = Integer.parseInt(quantity);
            q = q + 1;
            data.put("Quantity",String.valueOf(q));
            db.update(sqlTable, data, "ProductId= '" + cart.getProductId() + "'", null);
        }
    }
    //Update removing items
    public void removeFromCart(Cart cart) {

        SQLiteDatabase db = getReadableDatabase();
        String sqlTable = "CartDetail";
        String Query = "Select * from CartDetail where ProductId = '" + cart.getProductId() + "'";
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();
        String q = cursor.getString(cursor.getColumnIndex("Quantity"));
        int q1 = Integer.parseInt(q);
        if(q1 <= 1){
            String query = "DELETE FROM CartDetail where ProductId = '" + cart.getProductId() + "'";
            db.execSQL(query);
        }
        else {
            cursor.moveToFirst();
            ContentValues data=new ContentValues();
            String quantity = cursor.getString(cursor.getColumnIndex("Quantity"));
            int q2 = Integer.parseInt(quantity);
            if(q2>0) {
                q2 = q2 - 1;
            }
            data.put("Quantity",String.valueOf(q2));
            db.update(sqlTable, data, "ProductId= '" + cart.getProductId() + "'" , null);
        }
    }
    // Delete Cart
    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM CartDetail");
        db.execSQL(query);
    }
}