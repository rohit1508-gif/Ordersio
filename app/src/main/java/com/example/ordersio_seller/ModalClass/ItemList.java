package com.example.ordersio_seller.ModalClass;

public class ItemList {
    String itemName;
    String itemPrice;
    String id;
    public ItemList(){}
    public ItemList(String itemname,String price,String _id){
        this.itemName=itemname;
        this.itemPrice=price;
        this.id=_id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
