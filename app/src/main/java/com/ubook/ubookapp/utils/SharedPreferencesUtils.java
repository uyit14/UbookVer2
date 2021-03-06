package com.ubook.ubookapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    private int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "Pref_Ubook";

    //key
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String LANGUAGE_CODE = "languageCode";

    //
    public SharedPreferencesUtils(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //token
    public String getAccessToken() {
        return pref.getString(ACCESS_TOKEN, "");
    }

    public void setAccessToken(String accessToken) {
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    //language
    public String getLanguageCode() {
        return pref.getString(LANGUAGE_CODE, "");
    }

    public void setLanguageCode(String languageCode) {
        editor.putString(LANGUAGE_CODE, languageCode);
        editor.commit();
    }

    public void signOut() {
        editor.putString(ACCESS_TOKEN, "");
        editor.commit();
    }
}
