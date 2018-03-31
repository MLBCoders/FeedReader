package lk.nirmalsakila.feedreader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by user on 3/14/2018.
 */

public class GlobalClass extends Application {
//    KEYs and TAGs
    final String TAG = "FEEDREADER";
    final String KEY_SERVICE_NAME = "ServiceName";
    final String KEY_PREFERENCE_FILE = "FeedReaderPreference";
    String KEY_DARK_THEME_ENABLED = "NOT_SET";// = "DarkThemeEnabled";
    final String KEY_DATA_SAVER_ON = "DataSaverOn";
    final String KEY_LINK_OPEN_INTERNAL_BROWSER = "LinkOpenInInternalBrowser ";

    Context Application_Context = null;

    String selectedFeedService;
    boolean darkThemeEnabled;
    boolean dataSaverOn;
    boolean linkOpenInInternalBrowserEnabled;

    SharedPreferences sharedPreferences;

    public String getSelectedFeedService() {
        return selectedFeedService;
    }

    public void setSelectedFeedService(String selectedFeedService) {
        this.selectedFeedService = selectedFeedService;
    }

    public Context getApplication_Context() {
        return Application_Context;
    }

    public void setApplication_Context(Context application_Context) {
        this.Application_Context = application_Context;
        KEY_DARK_THEME_ENABLED = this.Application_Context.getResources().getString(R.string.settings_key_dark_theme);
        Log.d(TAG,"KEY Dark : " + KEY_DARK_THEME_ENABLED );
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

    public boolean isLinkOpenInInternalBrowserEnabled() {
        return linkOpenInInternalBrowserEnabled;
    }

    public void setLinkOpenInInternalBrowserEnabled(boolean linkOpenInInternalBrowserEnabled) {
        this.linkOpenInInternalBrowserEnabled = linkOpenInInternalBrowserEnabled;
    }
}
