package lk.nirmalsakila.feedreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ernieyu.feedparser.Feed;
import com.ernieyu.feedparser.FeedParser;
import com.ernieyu.feedparser.FeedParserFactory;
import com.ernieyu.feedparser.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssFeedActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mFetchFeedButton;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView mFeedTitleTextView;
    private TextView mFeedLinkTextView;
    private TextView mFeedDescriptionTextView;

    private List<Item> mFeedModelList;
    private String mFeedTitle;
    private String mFeedLink;
    private String mFeedDescription;
    private String rssUrl;

    private final String TAG = "RSSFEEDREADER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mEditText = (EditText) findViewById(R.id.rssFeedEditTxt);
        mFetchFeedButton = (Button) findViewById(R.id.fetchFeedBtn);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mFeedTitleTextView = (TextView) findViewById(R.id.feedTitle);
        mFeedDescriptionTextView = (TextView) findViewById(R.id.feedDescription);
        mFeedLinkTextView = (TextView) findViewById(R.id.feedLink);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFetchFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchFeedTask().execute((Void) null);
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });

        Intent intent = getIntent();

        rssUrl = intent.getStringExtra("RSS_URL");
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            urlLink = mEditText.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
//                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
//                    urlLink = "http://" + urlLink;
                urlLink = "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();

                FeedParser parser = FeedParserFactory.newParser();
                Feed feed = parser.parse(inputStream);
                mFeedModelList = feed.getItemList();

                Log.d("RSSF","FEED : " + feed);
                for (Item item : feed.getItemList()){

                    Log.d("RSSF","ITEM : " + item );
                    Log.d("RSSF","ITEM title: " + item.getTitle() );
                    Log.d("RSSF","ITEM Desc: " + item.getDescription() );
                }
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
//            } catch (XmlPullParserException e) {
//                Log.e(TAG, "Error", e);
            }catch (Exception e){
                Log.e(TAG,"Error Parser ",e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);

            if (success) {
                // Fill RecyclerView
               mRecyclerView.setAdapter(new RssFeedListItemAdapter(mFeedModelList));
            } else {
                Toast.makeText(RssFeedActivity.this,
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}


