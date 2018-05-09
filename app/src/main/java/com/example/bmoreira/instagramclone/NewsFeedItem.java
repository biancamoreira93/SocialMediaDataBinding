package com.example.bmoreira.instagramclone;

import android.graphics.Bitmap;

class NewsFeedItem {
    private String userName;
    private Bitmap userImage;

    public NewsFeedItem(String userName, Bitmap userImage) {
        this.userName = userName;
        this.userImage = userImage;
    }

    public Bitmap getImage() {
        return userImage;
    }

    public String getText() {
        return userName;
    }
}
