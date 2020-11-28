package com.example.ordersio_seller.ModalClass;

import android.telephony.mbms.MbmsErrors;

public class OrderList {
    String name;
    String number;
    String itemName;
    String quantity;
    String id;
    public OrderList(){}
    public OrderList(String purchaserName,String purchaserNumber,String itemname,String quantity,String _id){
        this.name=purchaserName;
        this.number=purchaserNumber;
        this.itemName=itemname;
        this.quantity=quantity;
        this.id=_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
