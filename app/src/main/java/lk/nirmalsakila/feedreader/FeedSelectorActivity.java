package lk.nirmalsakila.feedreader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeedSelectorActivity extends AppCompatActivity {
    GlobalClass globalClass;
    Switch theme_switch;
    private final String TAG = "FEED_SELECTOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AppSettings settings = AppSettings.getInstance(this);
//        setTheme(settings.getBoolean(AppSettings.Key.USE_DARK_THEME) ? R.style.AppThemeDark : R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass) this.getApplication();
        setTheme(globalClass.isDarkThemeEnabled()? R.style.AppThemeDark : R.style.AppThemeLight);
        setContentView(R.layout.activity_feed_selector);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        try {
//            getSupportActionBar().setTitle(R.string.app_name);
//        }catch (Exception e){
//            Log.d(TAG,"No Action Bar");
//        }

        globalClass.setAPPLICaTION_CONTEXT(getApplicationContext());

        HashMap<Integer, Integer> feedSelectors = new HashMap<>();
        feedSelectors.put(R.string.twitter, R.id.selectorTwitterButton);
//        feedSelectors.put(R.string.reddit,R.id.selectorTwitterButton1);
//        feedSelectors.put(R.string.rss,R.id.selectorTwitterButton2);

        for (Map.Entry<Integer, Integer> entry : feedSelectors.entrySet()) {
            final int feedTypeStringId = entry.getKey();
            int feedTypeButtonId = entry.getValue();

            CardView feedTypeButton = findViewById(feedTypeButtonId);
            feedTypeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalClass.setSelectedFeedService(getResources().getString(feedTypeStringId));

                    Intent intent = new Intent(FeedSelectorActivity.this, FeedServiceActivity.class);
                    FeedSelectorActivity.this.startActivity(intent);
                }
            });
        }

        CardView feedTypeButton = findViewById(R.id.selectorTwitterButton2);
        feedTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, MainActivity.class);
                FeedSelectorActivity.this.startActivity(intent);
            }
        });

        CardView CNNNews = findViewById(R.id.selectorCNNButton);
        CNNNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, NewsFeedActivity.class);
                intent.putExtra("SERVICE","cnn");
                FeedSelectorActivity.this.startActivity(intent);
//                globalClass.setDarkThemeEnabled(true);
//                FeedSelectorActivity.this.recreate();
            }
        });

        CardView BBCNews = findViewById(R.id.selectorBBCNewsButton);
        BBCNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, NewsFeedActivity.class);
                intent.putExtra("SERVICE", "bbc-news");
                FeedSelectorActivity.this.startActivity(intent);
//                globalClass.setDarkThemeEnabled(false);
//                FeedSelectorActivity.this.recreate();
            }
        });


        CardView BBCSports = findViewById(R.id.selectorBBCSportsButton);
        BBCSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this, NewsFeedActivity.class);
                intent.putExtra("SERVICE", "espn-cric-info");
                FeedSelectorActivity.this.startActivity(intent);
            }
        });

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

        Log.d(TAG,"Action : " + menu.findItem(R.id.action_theme_switch).getActionView());
        theme_switch = (Switch)menu.findItem(R.id.action_theme_switch)
                .getActionView().findViewById(R.id.switch_theme);

        theme_switch.setChecked(globalClass.isDarkThemeEnabled());
        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplication(), "ON", Toast.LENGTH_SHORT)
                            .show();
                    globalClass.setDarkThemeEnabled(true);
                    FeedSelectorActivity.this.recreate();
                }else{
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
        if (id == R.id.action_about) {
            Toast.makeText(getApplication(), "About", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
