package com.example.bmoreira.instagramclone;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

public class NewsFeedItem implements Serializable {
    private String userName;
    private Bitmap userImage;

    public NewsFeedItem(String userName, Bitmap userImage) {
        this.userName = userName;
        this.userImage = userImage;
    }

    public String getText() {
        return userName;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    @BindingAdapter({"android:userImage"})
    public static void setUserImage(ImageView imageView, Bitmap userImage) {
        imageView.setImageBitmap(userImage);
    }
}
