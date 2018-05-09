package com.example.bmoreira.instagramclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeedItem> {

    private Context context;
    private int resource;
    private List<NewsFeedItem> objects;

    public NewsFeedAdapter(@NonNull Context context, int resource, @NonNull List<NewsFeedItem> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public NewsFeedItem getItem(int position) {
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (view == null) {
            view = layoutInflater.inflate(resource, parent, false);
            holder = new ViewHolder(getTextView(view), getImageView(view), getLikesAndCommentsImageView(view));
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        NewsFeedItem newsFeedItem = getItem(position);
        holder.textView.setText(newsFeedItem.getText());
        holder.imageView.setImageBitmap(newsFeedItem.getImage());

        return view;
    }

    private ImageView getImageView(View view) {
        return view.findViewById(R.id.userImage);
    }

    private ImageView getLikesAndCommentsImageView(View view) {
        return view.findViewById(R.id.likesAndCommentsLayout);
    }

    private TextView getTextView(View view) {
        return view.findViewById(R.id.userName);
    }

    private class ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView likesAndCommentsImage;
        View.OnClickListener likesAndCommentsImageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.getText();
                ((ImageView) v).setImageResource(R.drawable.liked);
            }
        };

        public ViewHolder(TextView textView, ImageView imageView, ImageView likesAndCommentsImage) {
            this.textView = textView;
            this.imageView = imageView;
            this.likesAndCommentsImage = likesAndCommentsImage;
            this.likesAndCommentsImage.setOnClickListener(likesAndCommentsImageListener);
        }

    }


}
