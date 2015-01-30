package com.study.werwe.calendarlayout.kakao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by starmark on 15. 1. 30..
 */
public class KakaoPreference {
    public static  boolean putAuthCode(Context context, String code) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.edit().putString("authcode", code).commit();
    }

    public static String getAuthCode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("authcode", null);
    }
}
