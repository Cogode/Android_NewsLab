package com.example.newslab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.newslab.R;
import com.example.newslab.adapter.NewsPagerAdapter;
import com.example.newslab.fragment.NewsFragment;
import com.example.newslab.util.AnimationUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int SLIDE_MODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        for(int i = 0; i < 12; i ++) {
            boolean isHavingImage = true;
            if(i == 0 || i == 4)
                isHavingImage = false;
            fragments.add(new NewsFragment(R.layout.fragment_news_digest, i, isHavingImage));
        }
        pagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        GridLayout keyboard = findViewById(R.id.nav_bottom);
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int columnCount = keyboard.getColumnCount();
        for(int i = 0; i < keyboard.getChildCount(); i ++) {
            Button button = (Button) keyboard.getChildAt(i);
            button.setWidth(screenWidth / columnCount);
        }

        Button contactsPageButton = findViewById(R.id.contacts_page_btn);
        contactsPageButton.setOnClickListener(view -> {
            Intent toContactsIntent = new Intent(NewsActivity.this, ContactsActivity.class);
            toContactsIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toContactsIntent);
            SLIDE_MODE = 1;
            this.finish();
        });
        Button messagePageButton = findViewById(R.id.message_page_btn);
        messagePageButton.setOnClickListener(view -> {
            Intent toMessageIntent = new Intent(NewsActivity.this, MessageActivity.class);
            toMessageIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toMessageIntent);
            SLIDE_MODE = 1;
            this.finish();
        });
        Button minePageButton = findViewById(R.id.mine_page_btn);
        minePageButton.setOnClickListener(view -> {
            Intent toMineIntent = new Intent(NewsActivity.this, MineActivity.class);
            toMineIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toMineIntent);
            SLIDE_MODE = 1;
            this.finish();
        });
    }

    public String getPageTitle(int position) {
        return String.valueOf(pagerAdapter.getPageTitle(position));
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