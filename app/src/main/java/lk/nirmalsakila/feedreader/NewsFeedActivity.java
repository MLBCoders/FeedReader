package lk.nirmalsakila.feedreader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity {

    private final String TAG = "PostActivity";
//    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=1c8530ec3214460bbfc19f8db75c28bb";
//    private static final String ENDPOINT = "https://newsapi.org/v2/everything?sources=cnn&apiKey=1c8530ec3214460bbfc19f8db75c28bb";
    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines?sources=espn-cric-info&apiKey=1c8530ec3214460bbfc19f8db75c28bb";

    private RequestQueue requestQueue;
    private Gson gson;

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    List<Post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        mRecyclerView = findViewById(R.id.newsFeedRecyclerView);
        mSwipeLayout = findViewById(R.id.newsFeedswipeRefreshLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        fetchPosts();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPosts();
            }
        });
    }

    private void fetchPosts() {
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
            mRecyclerView.setAdapter(new PostFeedAdapter(posts));
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
}
