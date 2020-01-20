package com.telran.a20_01_20_cw;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        StoreProvider.getInstance().setContext(this);
        super.onCreate();
    }
}
