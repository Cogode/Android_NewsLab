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
        Button minePageButton = findViewById(R.id.mine_page_btn);
        minePageButton.setOnClickListener(view -> {
            Intent toMineIntent = new Intent(NewsActivity.this, MineActivity.class);
            toMineIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toMineIntent);
            AnimationUtil.slideInRight(this);
            this.finish();
        });
    }

    public String getPageTitle(int position) {
        return String.valueOf(pagerAdapter.getPageTitle(position));
    }
}