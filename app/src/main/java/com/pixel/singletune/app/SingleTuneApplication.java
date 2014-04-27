package com.pixel.singletune.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import utils.LinkUserToInstallationHelper;

/**
 * Created by smith on 3/30/14.
 */
public class SingleTuneApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
    //TODO: Remember to change these keys!!!
        Parse.initialize(this, "rvPIzES8Mg0ChzNaZtrJ6udORV3ggajrjUlZMZ8e", "4WtUhM0JDyW1zmXlbjrW7HDbFthNtqBI2F44bvj2");
//        ParseFacebookUtils.initialize("297125393789149");
        PushService.setDefaultPushCallback(this, NotificationsActivity.class);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            // Associate the device with a user
            LinkUserToInstallationHelper.LinkUserToInstallation.invoke();
        }
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
