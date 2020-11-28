package com.example.orderapp.Modalclass;

public class ItemList {
    String itemname;
    String price;
    String sellerID;
    public ItemList(){}
    public ItemList(String itemname,String price,String owner){
        this.itemname = itemname;
        this.price = price;
        this.sellerID=owner;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
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
}
