package com.example.newslab.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.newslab.domain.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DBUtil {
    public static boolean addUser(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("name", user.getName());
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
            digest = Base64.getEncoder().encode(digest);
            values.put("password", new BigInteger(1, digest).toString(16));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        values.put("tel", user.getTel());
        values.put("headPath", user.getHeadPath());
        values.put("backgroundPath", user.getBackgroundPath());
        db.insert("User", null, values);
        if(isUserExisted(dbHelper, user))
            return true;
        else
            return false;
    }

    public static boolean isUserExisted(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where id = ?", new String[] { user.getId() });
        if(cursor.moveToNext())
            return true;
        return false;
    }

    public static boolean isPasswordTrue(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select password from User where id = ?", new String[] { user.getId() });
        if(cursor.moveToNext()) {
            int passwordIndex = cursor.getColumnIndex("password");
            String password = cursor.getString(passwordIndex);
            try {
                MessageDigest md5 = MessageDigest.getInstance("md5");
                byte[] digest = md5.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
                digest = Base64.getEncoder().encode(digest);
                if(new BigInteger(1, digest).toString(16).equals(password))
                    return true;
            } catch(NoSuchAlgorithmException e) {
                Log.i("TAG", e.toString());
            }
        }
        return false;
    }

    public static User findUserById(MySQLiteOpenHelper dbHelper, String id) {
        User user = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where id = ?", new String[] { id });
        if(cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int passwordIndex = cursor.getColumnIndex("password");
            int telIndex = cursor.getColumnIndex("tel");
            int headPathIndex = cursor.getColumnIndex("headPath");
            int backgroundPathIndex = cursor.getColumnIndex("backgroundPath");
            user = new User();
            user.setId(cursor.getString(idIndex));
            user.setName(cursor.getString(nameIndex));
            user.setPassword(cursor.getString(passwordIndex));
            user.setTel(cursor.getString(telIndex));
            user.setHeadPath(cursor.getString(headPathIndex));
            Log.i("TAG", "cursor.head=" + cursor.getString(headPathIndex));
            user.setBackgroundPath(cursor.getString(backgroundPathIndex));
            Log.i("TAG", "cursor.background=" + cursor.getString(backgroundPathIndex));
        }
        return user;
    }

    public static boolean updateHeadPath(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("headPath", user.getHeadPath());
        db.update("User", values, "id = ?", new String[] { user.getId() });
        return true;
    }

    public static boolean updateBackgroundPath(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("backgroundPath", user.getBackgroundPath());
        db.update("User", values, "id = ?", new String[] { user.getId() });
        return true;
    }
}
