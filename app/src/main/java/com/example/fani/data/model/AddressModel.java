/*
 * *
 *  * Created by thaituan on 11/9/22, 2:33 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/7/22, 4:10 PM
 *
 */

package com.example.fani.data.model;

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
