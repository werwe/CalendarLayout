package com.study.werwe.calendarlayout.kakao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by starmark on 15. 1. 30..
 */
public class KakaoPreference {
    public static boolean putAuthCode(Context context, String code) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.edit().putString("authcode", code).commit();
    }

    public static String getAuthCode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("authcode", null);
    }

    public static boolean putAccessToken(Context context, String authTokenObj) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.edit().putString("access_token", authTokenObj).commit();
    }

    public static KakaoAccessToken getAccessToken(Context context) {
        return new Gson()
                .fromJson(
                        PreferenceManager.getDefaultSharedPreferences(context).getString("access_token", ""),
                        KakaoAccessToken.class);
    }
}
