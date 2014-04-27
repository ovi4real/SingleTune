package com.pixel.singletune.app;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by mrsmith on 4/27/14.
 */
@ParseClassName("Song")
public class Song extends ParseObject {
    public Song(){

    }
    public String getTitle(){
        return getString("title");
    }

    public void setTitle(String title){
        put("title", title);
    }

    public ParseUser getArtist(){
        return getParseUser("user");
    }

    public void setArtist(ParseUser user){
        put("user", user);
    }

    public ParseFile getSongFile(){
        return getParseFile("songFile");
    }

    public void setSongFile(ParseFile file){
        put("songFile", file);
    }

    public ParseFile getCoverArt(){
        return getParseFile("coverArt");
    }

    public void setCoverArt(ParseFile file){
        put("coverArt", file);
    }
}
