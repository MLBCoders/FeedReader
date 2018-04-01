package lk.nirmalsakila.feedreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class FeedSelectorActivity extends AppCompatActivity {
    GlobalClass globalClass;
    Switch theme_switch;
    private final String TAG = "FEED_SELECTOR";

    boolean darkThemeEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AppSettings settings = AppSettings.getInstance(this);
//        setTheme(settings.getBoolean(AppSettings.Key.USE_DARK_THEME) ? R.style.AppThemeDark : R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass) this.getApplication();

//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(globalClass.KEY_PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        globalClass.setSharedPreferences(sharedPreferences);

        darkThemeEnabled = globalClass.isDarkThemeEnabled();
        setTheme(darkThemeEnabled ? R.style.AppThemeDark_ActionBar : R.style.AppThemeLight_ActionBar);
        setContentView(R.layout.activity_feed_selector);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.general_toolbar);
//        setSupportActionBar(toolbar);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        try {
//            getSupportActionBar().setTitle(R.string.app_name);
//        }catch (Exception e){
//            Log.d(TAG,"No Action Bar");
//        }

        globalClass.setApplication_Context(getApplicationContext());

//        testing settings preference
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            Log.d("SHARED","Key : " + entry.getKey() + "  Val : " + entry.getValue());
        }

        HashMap<Integer, Integer> feedSelectors = new HashMap<>();
//        feedSelectors.put(R.string.twitter, R.id.selectorTwitterButton);
//        feedSelectors.put(R.string.reddit,R.id.selectorTwitterButton1);
//        feedSelectors.put(R.string.rss,R.id.selectorTwitterButton2);

//        for (Map.Entry<Integer, Integer> entry : feedSelectors.entrySet()) {
//            final int feedTypeStringId = entry.getKey();
//            int feedTypeButtonId = entry.getValue();
//
//            CardView feedTypeButton = findViewById(feedTypeButtonId);
//            feedTypeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    globalClass.setSelectedFeedService(getResources().getString(feedTypeStringId));
//
//                    Intent intent = new Intent(FeedSelectorActivity.this, FeedServiceActivity.class);
//                    FeedSelectorActivity.this.startActivity(intent);
//                }
//            });
//        }

        CardView feedTypeButton = findViewById(R.id.selectorTwitterButton2);
        feedTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, MainActivity.class);
                FeedSelectorActivity.this.startActivity(intent);
            }
        });

        HashMap<Integer, String> feedServices = new HashMap<>();
        feedServices.put(R.id.selectorBBCNewsButton, "bbc-news");
        feedServices.put(R.id.selectorBBCSportsButton, "espn-cric-info");
//        feedServices.put(R.id.selectorCNNButton, "cnn");

        for (Map.Entry<Integer, String> entry : feedServices.entrySet()) {
            final int feedButtonId = entry.getKey();
            final String feedService = entry.getValue();

            CardView feedButton = findViewById(feedButtonId);
            feedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalClass.setSelectedFeedService(feedService);

                    Intent intent = new Intent(FeedSelectorActivity.this, NewsFeedActivity.class);
                    intent.putExtra(globalClass.KEY_SERVICE_NAME, feedService);
                    FeedSelectorActivity.this.startActivity(intent);
                }
            });
        }
        CardView CNNNews = findViewById(R.id.selectorCNNButton);
        CNNNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, RssFeedActivity.class);
                intent.putExtra("SERVICE","http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml");
                FeedSelectorActivity.this.startActivity(intent);
//                globalClass.setDarkThemeEnabled(true);
//                FeedSelectorActivity.this.recreate();
            }
        });
//
//        CardView BBCNews = findViewById(R.id.selectorBBCNewsButton);
//        BBCNews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(FeedSelectorActivity.this, NewsFeedActivity.class);
//                intent.putExtra("SERVICE", "bbc-news");
//                FeedSelectorActivity.this.startActivity(intent);
////                globalClass.setDarkThemeEnabled(false);
////                FeedSelectorActivity.this.recreate();
//            }
//        });
//
//
//        CardView BBCSports = findViewById(R.id.selectorBBCSportsButton);
//        BBCSports.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(FeedSelectorActivity.this, NewsFeedActivity.class);
//                intent.putExtra("SERVICE", "espn-cric-info");
//                FeedSelectorActivity.this.startActivity(intent);
//            }
//        });

        CardView CNNSports = findViewById(R.id.selectorCNNSportsButton);
        CNNSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, FeedListSelectorActivity.class);
//                intent.putExtra("SERVICE","cnn");
                FeedSelectorActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        Log.d(TAG, "Action : " + menu.findItem(R.id.action_theme_switch).getActionView());
        theme_switch = (Switch) menu.findItem(R.id.action_theme_switch)
                .getActionView().findViewById(R.id.switch_theme);

        theme_switch.setChecked(globalClass.isDarkThemeEnabled());
        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplication(), "ON", Toast.LENGTH_SHORT)
                            .show();
                    globalClass.setDarkThemeEnabled(true);
                    FeedSelectorActivity.this.recreate();
                } else {
                    Toast.makeText(getApplication(), "OFF", Toast.LENGTH_SHORT)
                            .show();
                    globalClass.setDarkThemeEnabled(false);
                    FeedSelectorActivity.this.recreate();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about:
                Toast.makeText(getApplication(), "About", Toast.LENGTH_SHORT)
                        .show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplication(), getString(R.string.action_settings), Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(FeedSelectorActivity.this, SettingsActivity.class);
                intent.putExtra(SettingsActivity.EXTRA_SHOW_FRAGMENT,SettingsActivity.GeneralPreferenceFragment.class.getName());
//                intent.putExtra(SettingsActivity.EXTRA_NO_HEADERS,true);
                startActivity(intent);
//                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("FEED","ON POST RESUME");
        boolean nowDarkThemeEnabled = globalClass.isDarkThemeEnabled();
        if(darkThemeEnabled!= nowDarkThemeEnabled){
            FeedSelectorActivity.this.recreate();
        }
    }



}
