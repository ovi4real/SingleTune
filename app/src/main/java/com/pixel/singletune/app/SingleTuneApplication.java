package com.pixel.singletune.app;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by smith on 3/30/14.
 */
public class SingleTuneApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
    //TODO: Remember to change these keys!!!
        Parse.initialize(this, "rvPIzES8Mg0ChzNaZtrJ6udORV3ggajrjUlZMZ8e", "4WtUhM0JDyW1zmXlbjrW7HDbFthNtqBI2F44bvj2");
    }
}
