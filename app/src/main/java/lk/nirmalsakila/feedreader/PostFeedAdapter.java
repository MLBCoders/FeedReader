package lk.nirmalsakila.feedreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 3/17/2018.
 */

public class PostFeedAdapter extends RecyclerView.Adapter<PostFeedAdapter.PostFeedViewHolder> {

    private static List<Post> mDataSet;

    public static class PostFeedViewHolder extends RecyclerView.ViewHolder{

        private final TextView postTitleText;
        private final TextView postDescriptionText;
        private final TextView postUrlText;
        private final ImageView postImage;

        private View postFeedView;
        public PostFeedViewHolder(final View itemView) {
            super(itemView);
            postFeedView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("PostActivity","Element " + getAdapterPosition() + " clicked : "  + v);
                    goToUrl(itemView.getContext(),mDataSet.get(getAdapterPosition()).url);
                }
            });

            postTitleText = itemView.findViewById(R.id.postTitleText);
            postDescriptionText = itemView.findViewById(R.id.postDescriptionText);
            postUrlText = itemView.findViewById(R.id.postUrlText);
            postImage = itemView.findViewById(R.id.postImage);
        }

        public TextView getPostTitleText() {
            return postTitleText;
        }

        public TextView getPostDescriptionText() {
            return postDescriptionText;
        }

        public TextView getPostUrlText() {
            return postUrlText;
        }

        public ImageView getPostImage() {
            return postImage;
        }

        public View getPostFeedView() {
            return postFeedView;
        }
    }

    public PostFeedAdapter(List<Post> dataSet){
        Log.d("PostActivity","Post Feed Adapter Created . DATA : " + dataSet.size() );
        mDataSet = dataSet;
    }

    @Override
    public PostFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_post,parent,false);

        return new PostFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostFeedViewHolder holder, int position) {
        Context context = holder.postImage.getContext();

        final Post post = mDataSet.get(position);
        Log.d("PostActivity","SET " + position + " TO : " + post.title);

        ((TextView)holder.postFeedView.findViewById(R.id.postTitleText)).setText(post.title);
        holder.getPostDescriptionText().setText(post.description);
        holder.getPostUrlText().setText(post.url);

        Glide.with(context).load(post.urlToImage).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private static void goToUrl (Context context , String url) {
//        Uri uriUrl = Uri.parse(url);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        Intent launchBrowser = new Intent(context,WebActivity.class);
        launchBrowser.putExtra("URL",url);
       context.startActivity(launchBrowser);
    }
}
