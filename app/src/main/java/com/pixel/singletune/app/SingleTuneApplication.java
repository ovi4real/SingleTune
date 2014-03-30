package com.pixel.singletune.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by smith on 3/30/14.
 */
public class SingleTuneApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
    //TODO: Remember to change these keys!!!
        Parse.initialize(this, "app_key", "client_key");
    }
}
