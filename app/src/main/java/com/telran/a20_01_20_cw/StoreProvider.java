package com.telran.a20_01_20_cw;

import android.content.Context;

public class StoreProvider {
    private static StoreProvider instance;

    private Context context;
    public static final String TOKEN = "TOKEN";
    public static final String AUTH = "AUTH";
    private static final Object monitor = new Object();


//    private volatile Object obj;


    private StoreProvider(){

    }

    public static /*synchronized*/ StoreProvider getInstance(){
        ///////
//        synchronized (monitor) {
            if (instance == null) {

                instance = new StoreProvider();

            }
//        }
        /////
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean saveToken(String token){
        return context.getSharedPreferences(AUTH,Context.MODE_PRIVATE)
                .edit()
                .putString(TOKEN,token)
                .commit();
    }

    public String getToken(){
        return context.getSharedPreferences(AUTH,Context.MODE_PRIVATE)
                .getString(TOKEN,null);
    }

    public boolean removeToken(){
        return context.getSharedPreferences(AUTH,Context.MODE_PRIVATE)
                .edit()
                .remove(TOKEN)
                .commit();
    }
}
