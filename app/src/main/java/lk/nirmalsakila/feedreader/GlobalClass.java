package lk.nirmalsakila.feedreader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 3/14/2018.
 */

public class GlobalClass extends Application {
//    KEYs and TAGs
    final String TAG = "FEEDREADER";
    final String KEY_SERVICE_NAME = "ServiceName";
    final String KEY_PREFERENCE_FILE = "FeedReaderPreference";
    final String KEY_DARK_THEME_ENABLED = "DarkThemeEnabled";
    final String KEY_DATA_SAVER_ON = "DataSaverOn";

    Context APPLICaTION_CONTEXT = null;

    String selectedFeedService;
    boolean darkThemeEnabled;
    boolean dataSaverOn;

    SharedPreferences sharedPreferences;

    public String getSelectedFeedService() {
        return selectedFeedService;
    }

    public void setSelectedFeedService(String selectedFeedService) {
        this.selectedFeedService = selectedFeedService;
    }

    public Context getAPPLICaTION_CONTEXT() {
        return APPLICaTION_CONTEXT;
    }

    public void setAPPLICaTION_CONTEXT(Context APPLICaTION_CONTEXT) {
        this.APPLICaTION_CONTEXT = APPLICaTION_CONTEXT;
    }

    public boolean isDarkThemeEnabled() {
        return darkThemeEnabled;
    }

    public void setDarkThemeEnabled(boolean darkThemeEnabled) {
        this.darkThemeEnabled = darkThemeEnabled;
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(KEY_DARK_THEME_ENABLED,darkThemeEnabled).apply();
    }

    public boolean isDataSaverOn() {
        return dataSaverOn;
    }

    public void setDataSaverOn(boolean dataSaverOn) {
        this.dataSaverOn = dataSaverOn;
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(KEY_DATA_SAVER_ON,dataSaverOn).apply();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        darkThemeEnabled = sharedPreferences.getBoolean(KEY_DARK_THEME_ENABLED,false);
        dataSaverOn = sharedPreferences.getBoolean(KEY_DATA_SAVER_ON,false);
    }
}
