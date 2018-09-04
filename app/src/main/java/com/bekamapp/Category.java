package com.bekamapp;

import java.io.Serializable;
import java.math.BigDecimal;

public class Category implements Serializable {
    private String id;
    private String name;
    private int photo;
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getPhoto() {
        return photo;
    }

    public Category(String id, String name, int photo, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category() {
    }
}
