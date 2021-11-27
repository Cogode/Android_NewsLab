package com.example.newslab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newslab.R;
import com.example.newslab.activity.NewsActivity;
import com.example.newslab.activity.NewsContentActivity;
import com.example.newslab.adapter.NewsDigestRecyclerViewAdapter;
import com.example.newslab.domain.NewsDigest;
import com.example.newslab.util.NewsUtil;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private int layout;
    private int position;
    private boolean isHavingImage;
    private RecyclerView newsDigestRecyclerView;
    private NewsDigestRecyclerViewAdapter newsDigestRecyclerViewAdapter;
    private ArrayList<NewsDigest> newsDigestsList = new ArrayList<>();
    private String pageTitle;

    public NewsFragment(int layout, int position, boolean isHavingImage) {
        this.layout = layout;
        this.position = position;
        this.isHavingImage = isHavingImage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newsDigestRecyclerView = view.findViewById(R.id.newsDigest_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsDigestRecyclerView.setLayoutManager(layoutManager);
        newsDigestRecyclerViewAdapter = new NewsDigestRecyclerViewAdapter(newsDigestsList, index -> {
            Intent intent = new Intent(getActivity(), NewsContentActivity.class);
            intent.putExtra("newsDigest", newsDigestsList.get(index));
            startActivity(intent);
        }, position, isHavingImage);
        newsDigestRecyclerView.setAdapter(newsDigestRecyclerViewAdapter);
        newsDigestRecyclerViewAdapter.notifyDataSetChanged();
        newsDigestRecyclerView.scrollToPosition(0);
        pageTitle = ((NewsActivity) getActivity()).getPageTitle(position);
        NewsUtil.refreshNewsDigest(pageTitle, newsDigestsList, newsDigestRecyclerView, newsDigestRecyclerViewAdapter);
    }
}
