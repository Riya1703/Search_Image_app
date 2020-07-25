package com.example.searchimageapp;

import android.app.Application;
import android.content.Context;

public class ImageSearchApplication extends Application {
    private static ImageSearchApplication sINSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        sINSTANCE = this;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Context getInstance() {
        return sINSTANCE;
    }

}
