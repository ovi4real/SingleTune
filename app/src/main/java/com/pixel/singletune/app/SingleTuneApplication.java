package com.pixel.singletune.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.pixel.singletune.app.ui.NotificationsActivity;

import com.pixel.singletune.app.utils.LinkUserToInstallationHelper;

/**
 * Created by smith on 3/30/14.
 */
public class SingleTuneApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
    //TODO: Remember to change these keys!!!
        ParseObject.registerSubclass(Song.class);
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
