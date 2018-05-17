package com.example.bmoreira.instagramclone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.Serializable;
import java.util.List;

public class NewsFeedDecoderRunnable extends Thread {


    Handler handler;
    NewsFeedController newsFeedController;

    public NewsFeedDecoderRunnable(NewsFeedController newsFeedController, Handler handler) {
        this.newsFeedController = newsFeedController;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Looper.prepare();

            List<NewsFeedItem> newsFeedItemList = newsFeedController.getNewsFeedItems();
            Bundle bundle = new Bundle();
            Message message = handler.obtainMessage();

            bundle.putSerializable("newsFeedList", (Serializable) newsFeedItemList);
            message.setData(bundle);
            handler.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
