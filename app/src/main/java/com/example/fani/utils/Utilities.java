package com.example.fani.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import mehdi.sakout.aboutpage.Element;

public class Utilities {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, String message) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setAction("DISMISS", v -> snackbar.dismiss());
        snackbar.show();
    }

    /**
     * @param context
     * @return
     */
    public static Element createCopyright(Context context) {
        Element copyright = new Element();
        @SuppressLint("DefaultLocate") final String copyrightString = String.format("long", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.showToast(context,copyrightString);
            }
        });
        return copyright;
    }

    public static String currencyUnit(Integer value) {
        return (Constants.DOLLAR + String.valueOf(value)) ;
    }
}
