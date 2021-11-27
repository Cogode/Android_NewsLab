package com.example.newslab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.newslab.R;
import com.example.newslab.domain.User;
import com.example.newslab.util.AnimationUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineActivity extends AppCompatActivity {
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        TextView userNameTextView = findViewById(R.id.user_name_textView);
        TextView userIdTextView = findViewById(R.id.user_id_textView);
        userNameTextView.setText(user.getName());
        userIdTextView.setText(user.getId());
        Button newsPageButton = findViewById(R.id.news_page_btn);
        newsPageButton.setOnClickListener(view -> {
            Intent toNewsIntent = new Intent(MineActivity.this, NewsActivity.class);
            toNewsIntent.putExtra("user", user);
            startActivity(toNewsIntent);
            this.finish();
        });
        Button exitButton = findViewById(R.id.exit_btn);
        exitButton.setOnClickListener(view -> {
            Intent toLoginIntent = new Intent(MineActivity.this, LoginActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("remember", false);
            editor.putBoolean("auto", false);
            editor.apply();
            toLoginIntent.putExtra("id", user.getId());
            startActivity(toLoginIntent);
            this.finish();
        });
        CircleImageView headImageView = findViewById(R.id.head_imageView);
        headImageView.setImageResource(R.mipmap.user_head_default);
        View backgroundLayout = findViewById(R.id.background_layout);
        backgroundLayout.setBackgroundResource(R.mipmap.user_background_default);
    }

    @Override
    public void finish() {
        super.finish();
        AnimationUtil.slideInLeft(this);
    }
}