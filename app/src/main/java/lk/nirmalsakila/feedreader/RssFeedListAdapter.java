package lk.nirmalsakila.feedreader;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ernieyu.feedparser.Item;

import java.util.List;

/**
 * Created by user on 3/12/2018.
 */


public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

    private List<Item> mRssFeedModels1;
    private List<RssFeedModel> mRssFeedModels;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {
        mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        final RssFeedModel rssFeedModel = mRssFeedModels.get(position);
        Log.d("RSS","RSS Model : " + rssFeedModel.toString());
        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText(rssFeedModel.title);
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssContent)).setText(rssFeedModel.description);
        ((TextView)holder.rssFeedView.findViewById(R.id.linkText)).setText(rssFeedModel.link);
        if(rssFeedModel.imageURI!=null){
            Uri imageUri = Uri.parse(rssFeedModel.imageURI);
            ((ImageView)holder.rssFeedView.findViewById(R.id.rssImage)).setImageURI(imageUri);
        }
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }
}

