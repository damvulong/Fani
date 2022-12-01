package com.example.fshiperapplication.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class Utilities {

    /**
     * Show toast length short
     * @param context Context
     * @param message String
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show snack bar
     * @param view View
     * @param message String
     */
    public static void showSnackBar(View view, String message) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setAction("DISMISS", v -> snackbar.dismiss());
        snackbar.show();
    }

}
