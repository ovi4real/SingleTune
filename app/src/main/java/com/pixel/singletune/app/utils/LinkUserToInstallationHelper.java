package com.pixel.singletune.app.utils;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by mrsmith on 4/27/14.
 */
public final class LinkUserToInstallationHelper {
    public static class LinkUserToInstallation {
        public static void invoke() {
            // Associate the device with a user
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("user", ParseUser.getCurrentUser());
            installation.saveInBackground();
        }
    }
}
