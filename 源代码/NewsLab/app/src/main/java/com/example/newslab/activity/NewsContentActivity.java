package com.example.newslab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newslab.R;
import com.example.newslab.domain.NewsDigest;
import com.example.newslab.util.NewsUtil;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        init();
    }

    private void init() {
        Button backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(view -> this.finish());

        Intent intent = getIntent();
        NewsDigest newsDigest = (NewsDigest) intent.getSerializableExtra("newsDigest");
        TextView titleTextView = findViewById(R.id.title_textView);
        TextView sourceTextView = findViewById(R.id.source_textView);
        TextView timeTextView = findViewById(R.id.time_textView);
        ImageView imageView = findViewById(R.id.imageView);
        TextView contentTextView = findViewById(R.id.content_textView);
        NewsUtil.refreshNewsContent(titleTextView, sourceTextView, timeTextView, imageView, contentTextView, newsDigest);
    }
}