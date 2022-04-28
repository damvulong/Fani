/*
 * *
 *  * Created by damvulong on 4/20/22, 6:12 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 6:12 AM
 *
 */

package com.example.fani.model;

public class AddressModel {

    String userAddress;
    boolean isSelected;

    public AddressModel() {
    }

    public AddressModel(String userAddress, boolean isSelected) {
        this.userAddress = userAddress;
        this.isSelected = isSelected;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
