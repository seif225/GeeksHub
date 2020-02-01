package com.example.geekshub.data;

public class BookModel {

    private String id ,name , picture , describtion;
    private int price;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescribtion() {
        return describtion;
    }

    public String getPicture() {
        return picture;
    }


}
