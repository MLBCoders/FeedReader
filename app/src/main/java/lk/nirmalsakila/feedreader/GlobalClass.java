package lk.nirmalsakila.feedreader;

import android.app.Application;

/**
 * Created by user on 3/14/2018.
 */

public class GlobalClass extends Application {
    final String TAG = "FEEDREADER";
    final String KEY_SERVICE_NAME = "ServiceName";

    String selectedFeedService;

    public String getSelectedFeedService() {
        return selectedFeedService;
    }

    public void setSelectedFeedService(String selectedFeedService) {
        this.selectedFeedService = selectedFeedService;
    }
}
