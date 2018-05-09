package com.example.bmoreira.instagramclone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsFeedFragment extends ListFragment {

    NewsFeedViewModel newsFeedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsFeedViewModel = new NewsFeedViewModel();
        initsNewsFeedList();
    }

    public void initsNewsFeedList() {

        newsFeedViewModel.getNewsFeedItemList(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<NewsFeedItem>>() {
                    @Override
                    public void onNext(List<NewsFeedItem> newsFeedItems) {
                        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(getActivity(), R.layout.item_news_feed, newsFeedItems);
                        setListAdapter(newsFeedAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
