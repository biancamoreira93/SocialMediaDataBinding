package com.example.bmoreira.instagramclone;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bmoreira.instagramclone.databinding.ItemNewsFeedBinding;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedItemViewHolder> {

    private Context context;
    private int resource;
    private List<NewsFeedItem> objects;

    public NewsFeedAdapter(Context context, int resource, List<NewsFeedItem> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public NewsFeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemNewsFeedBinding binding = DataBindingUtil.inflate(layoutInflater, resource, parent, false);
        return new NewsFeedItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedItemViewHolder holder, int position) {
        NewsFeedItem newsFeedItem = objects.get(position);
        holder.bind(newsFeedItem);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class NewsFeedItemViewHolder extends RecyclerView.ViewHolder {
        ItemNewsFeedBinding binding;

        NewsFeedItemViewHolder(ItemNewsFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(NewsFeedItem newsFeedItem) {
            binding.setNewsFeedItem(newsFeedItem);
            binding.executePendingBindings();
        }
    }
}
