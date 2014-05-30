package com.pixel.singletune.app.subClasses;

/**
 * Created by mrsmith on 5/30/14.
 */
public class Comment {
    private String mUsername;
    private String mComment;

    public Comment(String userName, String comment) {
        this.mComment = comment;
        this.mUsername = userName;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }
}