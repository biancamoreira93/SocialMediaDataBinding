package com.example.bmoreira.instagramclone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Handler handler;
    NewsFeedController newsFeedController;
    NewsFeedAdapter newsFeedAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.newsFeedList);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = getNewsFeedListHandler();
        newsFeedController = new NewsFeedController(handler, this);
        newsFeedController.loadNewsFeed();
    }


    @SuppressLint("HandlerLeak")
    @NonNull
    private Handler getNewsFeedListHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message message) {
                List<NewsFeedItem> newsFeedItemList = (List<NewsFeedItem>) message.getData().getSerializable("newsFeedList");
                if (null != newsFeedItemList) {
                    loadNewsFeedList(newsFeedItemList);
                }
            }
        };
    }

    private void loadNewsFeedList(List<NewsFeedItem> newsFeedItems) {
        newsFeedAdapter = new NewsFeedAdapter(this, R.layout.item_news_feed, newsFeedItems);
        recyclerView.setAdapter(newsFeedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsFeedAdapter.notifyDataSetChanged();

        setProgressBarInvisible();
    }

    private void setProgressBarInvisible() {
        ProgressBar progressBar = findViewById(R.id.progressDialogNewsFeed);
        recyclerViewFadeOutAnimation();
        progressBar.animate().alpha(0f).setDuration(1000).setListener(getAnimatorEndListener(progressBar));

    }

    private void recyclerViewFadeOutAnimation() {
        recyclerView.setAlpha(0f);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.animate().alpha(1f).setDuration(1000).setListener(null);
    }

    @NonNull
    private AnimatorListenerAdapter getAnimatorEndListener(ProgressBar progressBar) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                progressBar.setVisibility(View.GONE);
            }
        };
    }

}
