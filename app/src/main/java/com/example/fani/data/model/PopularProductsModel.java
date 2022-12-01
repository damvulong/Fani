/*
 * *
 *  * Created by damvulong on 11/9/22, 2:33 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/7/22, 4:10 PM
 *
 */

package com.example.fani.data.model;

import java.io.Serializable;

public class PopularProductsModel implements Serializable {

    String description;
    String img_url;
    String name;
    String rating;
    int price;
    String urlModelAr;

    String isArGoogle;

    public PopularProductsModel() {

    }

    public PopularProductsModel(String description, String img_url, String name, String rating, int price, String urlModelAr, String isArGoogle) {
        this.description = description;
        this.img_url = img_url;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.urlModelAr = urlModelAr;
        this.isArGoogle = isArGoogle;
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

    public String getUrlModelAr() {
        return urlModelAr;
    }

    public void setUrlModelAr(String urlModelAr) {
        this.urlModelAr = urlModelAr;
    }

    public String getIsArGoogle() {
        return isArGoogle;
    }

    public void setIsArGoogle(String isArGoogle) {
        this.isArGoogle = isArGoogle;
    }
}
