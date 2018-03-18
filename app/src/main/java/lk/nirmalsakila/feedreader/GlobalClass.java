package lk.nirmalsakila.feedreader;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 3/14/2018.
 */

public class GlobalClass extends Application {
    final String TAG = "FEEDREADER";
    final String KEY_SERVICE_NAME = "ServiceName";
    Context APPLICaTION_CONTEXT = null;

    String selectedFeedService;

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
}
