package com.example.zafar.sartcrowd.Model;

/**
 * Created by Tahir on 10/21/2017.
 */

public class Cart {
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Discount;

    public Cart() {

    }
    public Cart(String productId, String productName, String quantity, String price, String discount) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }
    public Cart(String productId) {
        ProductId = productId;
    }

    public String getProductId() {
        return ProductId;
    }
    public String getProductName() {
        return ProductName;
    }
    public String getQuantity() {
        return Quantity;
    }
    public String getPrice() {
        return Price;
    }
    public String getDiscount() {
        return Discount;
    }
}
