package com.google.asli.Class;

import android.app.Application;

import com.google.asli.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class G extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Vazir-Medium-FD-WOL.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
