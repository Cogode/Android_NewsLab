package com.example.newslab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newslab.R;
import com.example.newslab.domain.User;
import com.example.newslab.util.DBUtil;
import com.example.newslab.util.MySQLiteOpenHelper;

public class LoginActivity extends AppCompatActivity {
    private MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(LoginActivity.this, "newsLab", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        EditText idEditText = findViewById(R.id.user_id);
        EditText passwordEditText = findViewById(R.id.user_password);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        CheckBox remember = findViewById(R.id.remember_password);
        CheckBox auto = findViewById(R.id.auto_login);
        if(sharedPreferences.getBoolean("remember", false)) {
            remember.setChecked(true);
            idEditText.setText(sharedPreferences.getString("id", ""));
            passwordEditText.setText(sharedPreferences.getString("password", ""));
            if(sharedPreferences.getBoolean("auto", false)) {
                auto.setChecked(true);
                String id = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                User user = new User(id, password);
                if(! DBUtil.isUserExisted(dbHelper, user))
                    Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                else {
                    if(! DBUtil.isPasswordTrue(dbHelper, user))
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    else {
                        if(remember.isChecked()) {
                            editor.putBoolean("remember", true);
                            editor.putString("id", user.getId());
                            editor.putString("password", user.getPassword());
                        }
                        else {
                            editor.remove("id");
                            editor.remove("password");
                            editor.putBoolean("remember", false);
                        }
                        if(auto.isChecked())
                            editor.putBoolean("auto", true);
                        else
                            editor.putBoolean("auto", false);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                        intent.putExtra("user", DBUtil.findUserById(dbHelper, user.getId()));
                        startActivity(intent);
                    }
                }
            }
        }
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            String id = idEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(id.equals("") || password.equals(""))
                Toast.makeText(LoginActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
            else {
                User user = new User(id, password);
                if(! DBUtil.isUserExisted(dbHelper, user))
                    Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                else {
                    if(! DBUtil.isPasswordTrue(dbHelper, user))
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    else {
                        if(remember.isChecked()) {
                            editor.putBoolean("remember", true);
                            editor.putString("id", user.getId());
                            editor.putString("password", user.getPassword());
                        }
                        else {
                            editor.remove("id");
                            editor.remove("password");
                            editor.putBoolean("remember", false);
                        }
                        if(auto.isChecked())
                            editor.putBoolean("auto", true);
                        else
                            editor.putBoolean("auto", false);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                        intent.putExtra("user", DBUtil.findUserById(dbHelper, user.getId()));
                        startActivity(intent);
                    }
                }
            }
        });
        Button registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(view -> {
            editor.putBoolean("remember", false);
            editor.putBoolean("auto", false);
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}