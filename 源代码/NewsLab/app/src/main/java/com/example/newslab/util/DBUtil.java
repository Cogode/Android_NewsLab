package com.example.newslab.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.newslab.domain.User;

public class DBUtil {
    public static boolean addUser(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("name", user.getName());
        values.put("password", user.getPassword());
        values.put("tel", user.getTel());
        db.insert("User", null, values);
        if(isUserExisted(dbHelper, user))
            return true;
        else
            return false;
    }

    public static boolean isUserExisted(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where id = ?", new String[] { user.getId() });
        if(cursor.moveToNext())
            return true;
        return false;
    }

    public static boolean isPasswordTrue(MySQLiteOpenHelper dbHelper, User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select password from user where id = ?", new String[] { user.getId() });
        if(cursor.moveToNext()) {
            int passwordIndex = cursor.getColumnIndex("password");
            String password = cursor.getString(passwordIndex);
            if(user.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
