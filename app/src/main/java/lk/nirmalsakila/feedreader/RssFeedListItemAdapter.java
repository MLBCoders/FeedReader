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

public class RssFeedListItemAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {
    private List<Item> mRssFeedModels1;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }

    }

    public RssFeedListItemAdapter(List<Item> mRssFeedModels1) {
        this.mRssFeedModels1 = mRssFeedModels1;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {

        final mRssFeedModels1 rssFeedModel = mRssFeedModels1.get(position);
        Log.d("RSS","RSS Model : " + rssFeedModel.toString());
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssTitle)).setText(rssFeedModel.title);
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssPubDate)).setText(rssFeedModel.pubDate);
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssContent)).setText(rssFeedModel.description);
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssLink)).setText(rssFeedModel.link);
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels1.size();
    }
}
