package com.example.newslab.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newslab.R;
import com.example.newslab.domain.NewsDigest;
import com.example.newslab.util.NewsUtil;

import java.util.ArrayList;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        NewsDigest newsDigest = (NewsDigest) intent.getSerializableExtra("newsDigest");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleTextView = findViewById(R.id.title_textView);
        TextView sourceTextView = findViewById(R.id.source_textView);
        TextView timeTextView = findViewById(R.id.time_textView);
        ImageView imageView = findViewById(R.id.imageView);
        TextView contentTextView = findViewById(R.id.content_textView);
        NewsUtil.refreshNewsContent(titleTextView, sourceTextView, timeTextView, imageView, contentTextView, newsDigest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}