/*
 * *
 *  * Created by thaituan on 11/22/22, 11:22 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/22/22, 11:22 AM
 *
 */

package com.example.fshiperapplication;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fshiperapplication", appContext.getPackageName());
    }
}