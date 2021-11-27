package com.example.newslab.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newslab.R;
import com.example.newslab.domain.User;
import com.example.newslab.util.AnimationUtil;
import com.example.newslab.util.DBUtil;
import com.example.newslab.util.FileUtil;
import com.example.newslab.util.MySQLiteOpenHelper;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineActivity extends AppCompatActivity {
    private User user = new User();
    public static final int TAKE_PHOTO = 1;
    public static final int LOCAL_PHOTO = 2;
    public static final int USER_HEAD = 1;
    public static final int USER_BACKGROUND = 0;
    private int SLIDE_MODE;
    private String[] items = { "相机", "图库" };
    private Uri imageUri;
    private CircleImageView headImageView;
    private ImageView backgroundImageView;
    private MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(MineActivity.this, "newsLab", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        init();
    }

    private void init() {
        headImageView = findViewById(R.id.head_imageView);
        backgroundImageView = findViewById(R.id.background_imageView);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        TextView userNameTextView = findViewById(R.id.user_name_textView);
        TextView userIdTextView = findViewById(R.id.user_id_textView);
        userNameTextView.setText(user.getName());
        userIdTextView.setText(user.getId());

        if(user.getHeadPath() == null)
            headImageView.setImageResource(R.mipmap.user_head_default);
        else {
            try {
                FileInputStream inputStream = new FileInputStream(user.getHeadPath());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                headImageView.setImageBitmap(bitmap);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(user.getBackgroundPath() == null)
            backgroundImageView.setImageResource(R.mipmap.user_background_default);
        else {
            try {
                FileInputStream inputStream = new FileInputStream(user.getBackgroundPath());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                backgroundImageView.setImageBitmap(bitmap);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        Button newsButton = findViewById(R.id.news_page_btn);
        newsButton.setOnClickListener(view -> {
            Intent toNewsIntent = new Intent(MineActivity.this, NewsActivity.class);
            toNewsIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toNewsIntent);
            SLIDE_MODE = 0;
            this.finish();
        });
        Button contactsActivity = findViewById(R.id.contacts_page_btn);
        contactsActivity.setOnClickListener(view -> {
            Intent toContactsIntent = new Intent(MineActivity.this, ContactsActivity.class);
            toContactsIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toContactsIntent);
            SLIDE_MODE = 0;
            this.finish();
        });
        Button messagePageButton = findViewById(R.id.message_page_btn);
        messagePageButton.setOnClickListener(view -> {
            Intent toMessageIntent = new Intent(MineActivity.this, MessageActivity.class);
            toMessageIntent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(toMessageIntent);
            SLIDE_MODE = 0;
            this.finish();
        });

        Button changeUserButton = findViewById(R.id.change_user_btn);
        changeUserButton.setOnClickListener(view -> {
            Intent toLoginIntent = new Intent(MineActivity.this, LoginActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("remember", false);
            editor.putBoolean("auto", false);
            editor.putString("id", null);
            editor.apply();
            startActivity(toLoginIntent);
            SLIDE_MODE = 0;
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
            startActivity(toLoginIntent);
            SLIDE_MODE = 0;
            this.finish();
        });

        headImageView.setOnClickListener(view -> {
            new AlertDialog.Builder(MineActivity.this).setItems(items, (dialog, which) -> {
                switch(which) {
                    case 0:
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        File takePhotoImage = new File(getExternalFilesDir("image"), "take_photo_" + sdf.format(now) + ".jpg");
                        try {
                            if(takePhotoImage.exists())
                                takePhotoImage.delete();
                            takePhotoImage.createNewFile();
                        } catch(Exception e) {
                            Toast.makeText(MineActivity.this, "无法加载此图片", Toast.LENGTH_SHORT).show();
                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(this, "com.example.newslab.activity.MineActivity", takePhotoImage);
                        else
                            imageUri = Uri.fromFile(takePhotoImage);
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePhotoIntent, TAKE_PHOTO * USER_HEAD);
                        break;
                    case 1:
                        Intent localPhotoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        localPhotoIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        localPhotoIntent.setType("image/*");
                        startActivityForResult(localPhotoIntent, LOCAL_PHOTO * USER_HEAD);
                        break;
                }
            }).show();
        });
        backgroundImageView.setOnClickListener(view -> {
            new AlertDialog.Builder(MineActivity.this).setItems(items, (dialog, which) -> {
                switch(which) {
                    case 0:
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        File takePhotoImage = new File(getExternalFilesDir("image"), "take_photo_" + sdf.format(now) + ".jpg");
                        try {
                            if(takePhotoImage.exists())
                                takePhotoImage.delete();
                            takePhotoImage.createNewFile();
                        } catch(Exception e) {
                            Toast.makeText(MineActivity.this, "无法加载此图片", Toast.LENGTH_SHORT).show();
                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(this, "com.example.newslab.activity.MineActivity", takePhotoImage);
                        else
                            imageUri = Uri.fromFile(takePhotoImage);
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePhotoIntent, TAKE_PHOTO * USER_BACKGROUND);
                        break;
                    case 1:
                        Intent localPhotoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        localPhotoIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        localPhotoIntent.setType("image/*");
                        startActivityForResult(localPhotoIntent, LOCAL_PHOTO * USER_BACKGROUND);
                        break;
                }
            }).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = null;
                if(imageUri != null) {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    imageUri = null;
                }
                else if(data != null)
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                String path = FileUtil.saveBitmapByCurrentTime(bitmap, MineActivity.this);
                if(requestCode > 0) {
                    headImageView.setImageBitmap(bitmap);
                    user.setHeadPath(path);
                    DBUtil.updateHeadPath(dbHelper, user);
                }
                else {
                    backgroundImageView.setImageBitmap(bitmap);
                    user.setBackgroundPath(path);
                    DBUtil.updateBackgroundPath(dbHelper, user);
                }
            } catch(Exception e) {
                e.printStackTrace();
                Toast.makeText(MineActivity.this, "无法加载此图片", Toast.LENGTH_SHORT).show();
            }
        }
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