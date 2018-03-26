package lk.nirmalsakila.feedreader;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity {

    private final String TAG = "PostActivity";
//    private static final String ENDPOINT_HEADLINES = "https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=1c8530ec3214460bbfc19f8db75c28bb";
//    private static final String ENDPOINT_EVERYTHING = "https://newsapi.org/v2/everything?sources=cnn&apiKey=1c8530ec3214460bbfc19f8db75c28bb";
//    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines?sources=espn-cric-info&apiKey=1c8530ec3214460bbfc19f8db75c28bb";

    private RequestQueue requestQueue;
    private Gson gson;

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    List<Post> posts = new ArrayList<>();
    String feedEndpoint;
    String ENDPOINT_HEADLINES;
    String ENDPOINT_EVERYTHING;

    GlobalClass globalClass;
    Switch data_saver_switch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalClass = (GlobalClass) this.getApplication();
        setTheme(globalClass.isDarkThemeEnabled()? R.style.AppThemeDark : R.style.AppThemeLight);
        setContentView(R.layout.activity_news_feed);

        mRecyclerView = findViewById(R.id.newsFeedRecyclerView);
        mSwipeLayout = findViewById(R.id.newsFeedswipeRefreshLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String service = getIntent().getStringExtra("SERVICE");
        Log.d(TAG,"Service : " + service);
        ENDPOINT_HEADLINES = getServiceEndpoint(service,"top-headlines");
        ENDPOINT_EVERYTHING = getServiceEndpoint(service,"everything");
        feedEndpoint = ENDPOINT_EVERYTHING;
//        fetchPosts(feedEndpoint);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPosts(feedEndpoint);
            }
        });

//        Button feedHeadlineButton  = findViewById(R.id.feedHeadlineButton);
//        feedHeadlineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                feedEndpoint = ENDPOINT_HEADLINES;
//                fetchPosts(feedEndpoint);
//            }
//        });
//
//        Button feedEverythingButton  = findViewById(R.id.feedEverythingButton);
//        feedEverythingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                feedEndpoint = ENDPOINT_EVERYTHING;
//                fetchPosts(feedEndpoint);
//            }
//        });
        ToggleButton feedTypeSelectButton = findViewById(R.id.feedTypeSelectButton);
        feedTypeSelectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d(TAG,"Endpoint : headlines ");
                    feedEndpoint = ENDPOINT_HEADLINES;
                }else{
                    Log.d(TAG,"Endpoint : Everything ");
                    feedEndpoint = ENDPOINT_EVERYTHING;
                }
                fetchPosts(feedEndpoint);
            }
        });
        feedTypeSelectButton.setChecked(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPosts(feedEndpoint);
    }

    private void fetchPosts(String ENDPOINT) {
        Log.d(TAG,"Fetching Started");
        mSwipeLayout.setRefreshing(true);
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);

            PostSet postSet = gson.fromJson(response, PostSet.class);
            Log.i("PostActivity","PostSet ==> Status : " + postSet.status);
            posts = postSet.articles;
//            mRecyclerView.setAdapter(new NewsFeedAdapter(getApplicationContext(),posts));
            mRecyclerView.setAdapter(new PostFeedAdapter(posts,NewsFeedActivity.this.getApplicationContext()));
            mSwipeLayout.setRefreshing(false);

            Log.i("PostActivity", posts.size() + " posts loaded.");
            for (Post post : posts) {
                Log.i("PostActivity", post.ID + ": " + post.title);
//                RssFeedModel rssPost = new RssFeedModel(post.title,post.url,post.description,post.urlToImage);
//                rssPosts.add(rssPost);
//                Log.i("PostActivity", "FROM RSS ==> " + rssPost.title + ": " + rssPost.description);
            }

            Log.i("PostActivity", "Recycler View ==> " + mRecyclerView);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
            mSwipeLayout.setRefreshing(false);
        }
    };

    private void goToUrl (String url) {
//        Uri uriUrl = Uri.parse(url);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        Intent launchBrowser = new Intent(getApplicationContext(),WebActivity.class);
        launchBrowser.putExtra("URL",url);
        startActivity(launchBrowser);
    }

    private String getServiceEndpoint(String serviceType,String feedType){
        return "https://newsapi.org/v2/" + feedType + "?sources="+ serviceType + "&apiKey=1c8530ec3214460bbfc19f8db75c28bb";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        Log.d(TAG,"Action : " + menu.findItem(R.id.action_theme_switch).getActionView());
        data_saver_switch = (Switch)menu.findItem(R.id.action_theme_switch)
                .getActionView().findViewById(R.id.switch_theme);

        data_saver_switch.setChecked(globalClass.isDataSaverOn());
        data_saver_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplication(), "ON", Toast.LENGTH_SHORT)
                            .show();
                    globalClass.setDataSaverOn(true);
                }else{
                    Toast.makeText(getApplication(), "OFF", Toast.LENGTH_SHORT)
                            .show();
                    globalClass.setDataSaverOn(false);
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
