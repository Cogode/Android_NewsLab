package com.example.newslab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.newslab.R;
import com.example.newslab.util.AnimationUtil;

public class MessageActivity extends AppCompatActivity {
    private int SLIDE_MODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
    }

    private void init() {
        Button newsPageButton = findViewById(R.id.news_page_btn);
        newsPageButton.setOnClickListener(view -> {
            Intent toNewsIntent = new Intent(MessageActivity.this, NewsActivity.class);
            toNewsIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toNewsIntent);
            SLIDE_MODE = 0;
            this.finish();
        });
        Button contactsActivity = findViewById(R.id.contacts_page_btn);
        contactsActivity.setOnClickListener(view -> {
            Intent toContactsIntent = new Intent(MessageActivity.this, ContactsActivity.class);
            toContactsIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toContactsIntent);
            SLIDE_MODE = 0;
            this.finish();
        });
        Button minePageButton = findViewById(R.id.mine_page_btn);
        minePageButton.setOnClickListener(view -> {
            Intent toMineIntent = new Intent(MessageActivity.this, MineActivity.class);
            toMineIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toMineIntent);
            SLIDE_MODE = 1;
            this.finish();
        });
    }

    @Override
    public void finish() {
        super.finish();
        switch(SLIDE_MODE) {
            case 0:
                AnimationUtil.slideInLeft(this);
                break;
            case 1:
                AnimationUtil.slideInRight(this);
                break;
        }
    }
}