package lk.nirmalsakila.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeedSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_selector);

        final GlobalClass globalClass = (GlobalClass) this.getApplication();

        HashMap<Integer,Integer> feedSelectors = new HashMap<>();
        feedSelectors.put(R.string.twitter,R.id.selectorTwitterButton);
//        feedSelectors.put(R.string.reddit,R.id.selectorTwitterButton1);
//        feedSelectors.put(R.string.rss,R.id.selectorTwitterButton2);

        for(Map.Entry<Integer,Integer> entry : feedSelectors.entrySet()){
            final int feedTypeStringId = entry.getKey();
            int feedTypeButtonId = entry.getValue();

            CardView feedTypeButton = findViewById(feedTypeButtonId);
            feedTypeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalClass.setSelectedFeedService(getResources().getString(feedTypeStringId));

                    Intent intent = new Intent(FeedSelectorActivity.this,FeedServiceActivity.class);
                    FeedSelectorActivity.this.startActivity(intent);
                }
            });
        }

        CardView feedTypeButton = findViewById(R.id.selectorTwitterButton2);
        feedTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this,MainActivity.class);
                FeedSelectorActivity.this.startActivity(intent);
            }
        });

        CardView jsonNews = findViewById(R.id.selectorTwitterButton1);
        jsonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedSelectorActivity.this,NewsFeedActivity.class);
                FeedSelectorActivity.this.startActivity(intent);
            }
        });

    }
}
