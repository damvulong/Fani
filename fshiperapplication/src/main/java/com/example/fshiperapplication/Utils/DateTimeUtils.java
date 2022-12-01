/*
 * *
 *  * Created by thaituan on 11/23/22, 11:01 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/23/22, 11:01 AM
 *
 */

package com.example.fshiperapplication.Utils;

import static java.text.DateFormat.getDateTimeInstance;

import java.text.DateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static String getDateTime(Long time) {
        String dateTime = null;
        try {
            DateFormat sdf = getDateTimeInstance();
            Date netDate = new Date(time);
            dateTime = sdf.format(netDate);
        } catch (Exception e) {
            e.toString();
        }
        return dateTime;
    }
}
