package com.bekamapp;

public class CategoryItemsDetails {
    private String ItemName;
    private int itemPrice;
    private int itemImgURl;

    public CategoryItemsDetails(String itemName, int price, int imgURl) {
        ItemName = itemName;
        this.itemPrice = price;
        itemImgURl = imgURl;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemImgURl() {
        return itemImgURl;
    }

    public void setItemImgURl(int itemImgURl) {
        this.itemImgURl = itemImgURl;
    }
}
