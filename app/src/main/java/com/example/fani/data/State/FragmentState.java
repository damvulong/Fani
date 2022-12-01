/*
 * *
 *  * Created by damvulong on 11/15/22, 8:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/15/22, 8:44 PM
 *
 */

package com.example.fani.data.State;

public enum FragmentState {
    home(0), favorite(1), cart(2),profile(3);

    public final int value;

    FragmentState(final int value) {
        this.value = value;
    }

}
