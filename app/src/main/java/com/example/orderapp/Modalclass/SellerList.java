package com.example.orderapp.Modalclass;

import com.example.orderapp.Adapter.SellerAdapter;

public class SellerList {
    String sellerName;
    String sellerAddress;
    String id;
    public SellerList(){}
    public SellerList(String name,String address,String _id){
        this.sellerName=name;
        this.sellerAddress=address;
        this.id=_id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
