package com.example.newslab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newslab.R;
import com.example.newslab.domain.User;
import com.example.newslab.util.DBUtil;
import com.example.newslab.util.MySQLiteOpenHelper;

public class RegisterActivity extends AppCompatActivity {
    private MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(RegisterActivity.this, "newsLab", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        EditText idEditText = findViewById(R.id.user_id);
        EditText nameEditText = findViewById(R.id.user_name);
        EditText passwordEditText = findViewById(R.id.user_password);
        EditText confirmPasswordEditText = findViewById(R.id.user_confirmPassword);
        EditText telEditText = findViewById(R.id.user_tel);
        Button registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(view -> {
            String id = idEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String tel = telEditText.getText().toString();
            if(id.equals("") || name.equals("") || password.equals("") || confirmPassword.equals(""))
                Toast.makeText(RegisterActivity.this, "带“*”字段不能为空", Toast.LENGTH_SHORT).show();
            else {
                if(! confirmPassword.equals(password))
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                else {
                    User user = new User(id, name, password, tel);
                    if(DBUtil.isUserExisted(dbHelper, user))
                        Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    else {
                        if(DBUtil.addUser(dbHelper, user))
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(RegisterActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}