package lk.nirmalsakila.feedreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=1c8530ec3214460bbfc19f8db75c28bb";

    private RequestQueue requestQueue;
    private Gson gson;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        mRecyclerView = findViewById(R.id.newsFeedRecyclerView);

        requestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        fetchPosts();
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);

            PostSet postSet = gson.fromJson(response, PostSet.class);
            Log.i("PostActivity","PostSet ==> Status : " + postSet.status);
            List<Post> posts = postSet.articles;
            List<RssFeedModel> rssPosts = new ArrayList<>();

            Log.i("PostActivity", posts.size() + " posts loaded.");
            for (Post post : posts) {
                Log.i("PostActivity", post.ID + ": " + post.title);
                RssFeedModel rssPost = new RssFeedModel(post.title,post.url,post.description,post.urlToImage);
                rssPosts.add(rssPost);
                Log.i("PostActivity", "FROM RSS ==> " + rssPost.title + ": " + rssPost.description);
            }

            mRecyclerView.setAdapter(new RssFeedListAdapter(rssPosts));
            Log.i("PostActivity", "Recycler View ==> " + mRecyclerView);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };


}
