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

import java.text.DateFormat;
import java.util.List;

public class RssFeedListItemAdapter extends RecyclerView.Adapter<RssFeedListItemAdapter.FeedModelViewHolder> {
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

        final Item rssFeedModel = mRssFeedModels1.get(position);
        Log.d("RSS","RSS Model : " + rssFeedModel.toString());
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssTitle)).setText(rssFeedModel.getTitle());
        String date = DateFormat.getDateTimeInstance().format(rssFeedModel.getPubDate());
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssPubDate)).setText(date);
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssContent)).setText(rssFeedModel.getDescription());
        ((TextView)holder.rssFeedView.findViewById(R.id.txtRssLink)).setText(rssFeedModel.getLink());
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels1.size();
    }
}
