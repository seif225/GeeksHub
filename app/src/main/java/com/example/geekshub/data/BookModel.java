package com.example.geekshub.data;

import android.net.Uri;

public class BookModel {

       private String describtion ,id,name,picture , category, publisher , author;
       private int price ;
       private Uri ImageUri;

    public void setImageUri(Uri imageUri) {
        ImageUri = imageUri;
    }

    public Uri getImageUri() {
        return ImageUri;
    }

    public BookModel(){}

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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getPublisher() {
        return publisher;
    }
}
