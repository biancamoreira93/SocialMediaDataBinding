package com.example.bmoreira.instagramclone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsFeedFragment extends Fragment {

    NewsFeedViewModel newsFeedViewModel;
    CompositeDisposable compositeDisposable;
    NewsFeedAdapter newsFeedAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsFeedViewModel = new NewsFeedViewModel();
        recyclerView = getActivity().findViewById(R.id.newsFeedList);
        recyclerView.setVisibility(View.GONE);
        initsNewsFeedList();
    }

    public void initsNewsFeedList() {
        compositeDisposable = new CompositeDisposable();

        Disposable disposable = newsFeedViewModel.getNewsFeedItemList(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadNewsFeedList, Throwable::getStackTrace);

        compositeDisposable.add(disposable);
    }

    private void loadNewsFeedList(List<NewsFeedItem> newsFeedItems) {
        newsFeedAdapter = new NewsFeedAdapter(getActivity(), R.layout.item_news_feed, newsFeedItems);
        recyclerView.setAdapter(newsFeedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsFeedAdapter.notifyDataSetChanged();

        setProgressBarInvisible();
    }

    private void setProgressBarInvisible() {
        ProgressBar progressBar = getActivity().findViewById(R.id.progressDialogNewsFeed);
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
