package com.listgrid.demo;

import android.app.Application;

import com.listgrid.demo.utils.Density;
import com.listgrid.demo.utils.logger.Config;
import com.listgrid.demo.utils.logger.Logger;

public class App extends Application {

    private static final float WIDTH = 1280;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init().setLevel(Config.LEVEL_FULL);
//        Density.setDensity(this, WIDTH);
    }
}
