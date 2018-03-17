package lk.nirmalsakila.feedreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

/**
 * Created by user on 3/17/2018.
 */

public class NewsFeedAdapter extends BaseAdapter {

    Context context;
    List<Post> data;
    private static LayoutInflater inflater = null;

    public NewsFeedAdapter(Context context, List<Post> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).ID;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.item_news_post,null);
        TextView postTitleText = vi.findViewById(R.id.postTitleText);
        TextView postDescriptionText = vi.findViewById(R.id.postDescriptionText);
        TextView postUrlText = vi.findViewById(R.id.postUrlText);



        postTitleText.setText(data.get(position).title);
        postDescriptionText.setText(data.get(position).description);
        postUrlText.setText(data.get(position).url);

        return vi;
    }
}
