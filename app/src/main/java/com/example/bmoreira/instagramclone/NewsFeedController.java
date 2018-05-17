package com.example.bmoreira.instagramclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.BitmapFactory.decodeStream;

public class NewsFeedController {

    Context context;
    Handler handler;

    public NewsFeedController(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    public List<NewsFeedItem> getNewsFeedItems() throws Exception {
        JSONArray jsonArray = getJsonArray(context);
        List<NewsFeedItem> newsFeedItemList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject newsFeedItemJsonObject = (JSONObject) jsonArray.get(i);
            String userName = newsFeedItemJsonObject.getString("userName");
            String userImage = newsFeedItemJsonObject.getString("userImage");

            Bitmap image = getImageBitmap(userImage);
            NewsFeedItem item = new NewsFeedItem(userName, image);
            newsFeedItemList.add(item);
        }
        return newsFeedItemList;
    }

    private Bitmap getImageBitmap(String userImage) throws Exception {
        URL url = new URL(userImage);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        return decodeStream(inputStream);
    }

    private JSONArray getJsonArray(Context context) throws Exception {
        String result = readJsonFile(context);
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getJSONArray("users");
    }

    @NonNull
    private String readJsonFile(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("json_response.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringBuilder result = new StringBuilder();
        int data = inputStreamReader.read();
        while (data != -1) {
            char current = (char) data;
            result.append(current);
            data = inputStreamReader.read();
        }
        return result.toString();
    }

    public void loadNewsFeed() {
        NewsFeedDecoderRunnable runnable = new NewsFeedDecoderRunnable(this, handler);
        runnable.start();
    }
}
