package com.example.newslab.util;

import android.app.Activity;

import com.example.newslab.R;

public class AnimationUtil {
    public static void slideInLeft(Activity activity) {
        activity.overridePendingTransition(0, R.xml.slide_in_left);
    }

    public static void slideInRight(Activity activity) {
        activity.overridePendingTransition(R.xml.slide_in_right, R.xml.animation_no);
    }
}
