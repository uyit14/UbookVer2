package com.ubook.ubookapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.ubook.ubookapp.utils.Constants;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class UbookHelper {
    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }
    //
    public static void changeLanguage(String languageCode, Context mContext) {
        switch (languageCode) {
            case "en":{
                Locale.setDefault(Locale.ENGLISH);
                Configuration configEng = new Configuration();
                configEng.locale = Locale.ENGLISH;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    configEng.setLayoutDirection(Locale.getDefault());
                }
                if (mContext != null) {
                    mContext.getResources().updateConfiguration(configEng, mContext.getResources().getDisplayMetrics());
                }
                break;
            }
            case "vi":{
                Locale locale = new Locale("vi", "VN");
                Locale.setDefault(locale);
                Configuration configZho = new Configuration();
                configZho.locale = locale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    configZho.setLayoutDirection(Locale.getDefault());
                }
                if (mContext != null) {
                    mContext.getResources().updateConfiguration(configZho, mContext.getResources().getDisplayMetrics());
                }
                break;
            }
            default:
                Locale locale = new Locale("vi", "VN");
                Locale.setDefault(locale);
                Configuration configZho = new Configuration();
                configZho.locale = locale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    configZho.setLayoutDirection(Locale.getDefault());
                }
                if (mContext != null) {
                    mContext.getResources().updateConfiguration(configZho, mContext.getResources().getDisplayMetrics());
                }
                break;
        }
    }

    //restart activity
    public static void restartActivity(Activity activity){
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    public static String createTokenHeader(String accessToken) {
        if (TextUtils.isEmpty(accessToken)) {
            return "";
        }
        return String.format("%s %s", Constants.JUCETK, accessToken);
    }

    public static String getTimezone() {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone timeZone = calendar.getTimeZone();
            return timeZone.getID();
        } catch (Exception e) {
            return "";
        }
    }
}
