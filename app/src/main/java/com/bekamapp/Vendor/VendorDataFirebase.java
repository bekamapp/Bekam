package com.bekamapp.Vendor;

import com.bekamapp.Item;
import com.bekamapp.WorkingHours;

import java.util.ArrayList;
import java.util.List;

public class VendorDataFirebase {
    private String name, phone, location, category;
    private WorkingHours workingHours;

    private List<Item> items;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWorkingHours(List<String> days, String from, String to) {
        this.workingHours = new WorkingHours();
        this.workingHours.setWorkingDays(days);
        this.workingHours.setFrom(from);
        this.workingHours.setTo(to);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }


    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(String itemName, String itemPrice, String itemDescription, String itemImage) {
        if(this.items == null)
            this.items = new ArrayList<>();
        this.items.add(new Item(itemName, itemPrice, itemDescription, itemImage));
    }
}