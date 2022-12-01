/*
 * *
 *  * Created by damvulong on 5/5/22, 12:50 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 5/5/22, 12:50 AM
 *
 */

package com.example.fani.data.model;

public class MyFavoriteModel {

    String img_url;
    String productName;
    String productPrice;

    String documentId;

    public MyFavoriteModel() {
    }

    public MyFavoriteModel(String img_url, String productName, String productPrice) {
        this.img_url = img_url;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
