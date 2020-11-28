package com.example.orderapp.Modalclass;

public class OrderList {
    String itemname;
    String price;
    String quantity;
    String seller;
    public OrderList(){}
    public OrderList(String itemname,String price,String quantity,String seller){
        this.itemname = itemname;
        this.price = price;
        this.quantity = quantity;
        this.seller=seller;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
