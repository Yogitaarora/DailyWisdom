package dailywisdom.techindustan.com.dailywisdom;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by android on 9/2/18.
 */

class Utilities {
    public static Dialog dialog;

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;

    }






    public static void clearAllPrefs(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static void updateIntegerPref(Context context, String key, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void updateStringPref(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void updateBooleanPref(Context context, String key, boolean value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getIntegerPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, 0);
    }

    public static int getIntegerPref(Context context, String key, int defVal) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, defVal);
    }

    public static String getStringPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, "");
    }

    public static String getStringPref(Context context, String key, String def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, def);
    }


    public static void updateLongPref(Context context, String key, long value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLongPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong(key, 0);
    }

    public static boolean getBooleanPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, false);
    }

    public static String convertNotificationTime(String cDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = sdf.parse(cDate);
            SimpleDateFormat newsdf = new SimpleDateFormat("hh:mm aaa", Locale.ENGLISH);
            String convertedDate = newsdf.format(date);
            Log.e("convertedTime", convertedDate + "");
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String convertDateTime(String cDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = sdf.parse(cDate);
            SimpleDateFormat newsdf = new SimpleDateFormat("E, dd MMM yyyy | hh:mm aaa", Locale.ENGLISH);
            String convertedDate = newsdf.format(date);
            Log.e("converteddate", convertedDate + "");
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String convertDate(String cDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = sdf.parse(cDate);
            SimpleDateFormat newsdf = new SimpleDateFormat("E, dd MMM yyyy", Locale.ENGLISH);
            String convertedDate = newsdf.format(date);
            Log.e("converteddate", convertedDate + "");
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getDate(String cDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = sdf.parse(cDate);
            SimpleDateFormat newsdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            String convertedDate = newsdf.format(date);
            Log.e("converteddate", convertedDate + "");
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
