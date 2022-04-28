/*
 * *
 *  * Created by damvulong on 4/20/22, 2:47 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 2:47 AM
 *
 */

package com.example.fani.model;

import java.io.Serializable;

public class ShowAllModel implements Serializable {

    String description;
    String img_url;
    String name;
    String rating;
    int price;
    String type;
    String color;

    public ShowAllModel() {
    }

    public ShowAllModel(String description, String img_url, String name, String rating, int price, String type, String color) {
        this.description = description;
        this.img_url = img_url;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.type = type;
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
