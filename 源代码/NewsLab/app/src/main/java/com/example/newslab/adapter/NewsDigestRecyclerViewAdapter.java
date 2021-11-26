package com.example.newslab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newslab.R;
import com.example.newslab.domain.NewsDigest;

import java.util.ArrayList;

public class NewsDigestRecyclerViewAdapter extends RecyclerView.Adapter<NewsDigestRecyclerViewAdapter.ViewHolder> {
    private ArrayList<NewsDigest> newsDigestList;
    private OnClickListener clickListener;
    private int position;
    private boolean isHavingImage;

    public interface OnClickListener {
        void onClick(int index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View newsDigestItem;
        private final ImageView imageView;
        private final TextView titleTextView;
        private final TextView sourceTextView;
        private final TextView timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsDigestItem = itemView.findViewById(R.id.newsDigest_item);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.title_textView);
            sourceTextView = itemView.findViewById(R.id.source_textView);
            timeTextView = itemView.findViewById(R.id.time_textView);
        }
    }

    public NewsDigestRecyclerViewAdapter(ArrayList<NewsDigest> newsDigestList, OnClickListener clickListener, int position, boolean isHavingImage) {
        this.newsDigestList = newsDigestList;
        this.clickListener = clickListener;
        this.position = position;
        this.isHavingImage = isHavingImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.newsdigest_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        NewsDigest newsDigest = newsDigestList.get(index);
        if(isHavingImage) {
            if(position == 0)
                Glide.with(holder.newsDigestItem.getContext()).load(newsDigest.getImgsrc()).into(holder.imageView);
            else {
                if(! newsDigest.getPicUrl().split(":")[0].equals("http") && ! newsDigest.getPicUrl().split(":")[0].equals("https"))
                    newsDigest.setPicUrl("https:" + newsDigest.getPicUrl());
                Glide.with(holder.newsDigestItem.getContext()).load(newsDigest.getPicUrl()).into(holder.imageView);
            }
        }
        else
            holder.imageView.setVisibility(View.GONE);
        holder.titleTextView.setText(newsDigest.getTitle());
        holder.sourceTextView.setText(newsDigest.getSource());
        if(position == 0)
            holder.timeTextView.setText(newsDigest.getMtime());
        else
            holder.timeTextView.setText(newsDigest.getCtime());
        holder.newsDigestItem.setOnClickListener(view -> clickListener.onClick(index));
    }

    @Override
    public int getItemCount() {
        return newsDigestList.size();
    }
}
