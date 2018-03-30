package lk.nirmalsakila.feedreader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 3/17/2018.
 */

public class PostFeedAdapter extends RecyclerView.Adapter<PostFeedAdapter.PostFeedViewHolder> {

    private static List<Post> mDataSet;
    GlobalClass globalClass;

    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    CustomTabsClient mClient;
    private static CustomTabsSession mCustomTabsSession;
    private static CustomTabsServiceConnection mCustomTabsServiceConnection;
    private static CustomTabsIntent customTabsIntent;

    public static class PostFeedViewHolder extends RecyclerView.ViewHolder{

        private final TextView postTitleText;
        private final TextView postDescriptionText;
//        private final TextView postUrlText;
        private final ImageView postImage;
        private final Button postImageDownloadBtn;

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
//            postUrlText = itemView.findViewById(R.id.postUrlText);
            postImage = itemView.findViewById(R.id.postImage);
            postImageDownloadBtn = itemView.findViewById(R.id.postImageDownloadBtn);
        }

        public TextView getPostTitleText() {
            return postTitleText;
        }

        public TextView getPostDescriptionText() {
            return postDescriptionText;
        }

//        public TextView getPostUrlText() {
//            return postUrlText;
//        }

        public ImageView getPostImage() {
            return postImage;
        }

        public View getPostFeedView() {
            return postFeedView;
        }

        public Button getPostImageDownloadBtn() {
            return postImageDownloadBtn;
        }
    }

    public PostFeedAdapter(List<Post> dataSet,Context context){
        Log.d("PostActivity","Post Feed Adapter Created . DATA : " + dataSet.size() );
        mDataSet = dataSet;

        globalClass = (GlobalClass) context.getApplicationContext();
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mClient = customTabsClient;
                mClient.warmup(0L);
                mCustomTabsSession = mClient.newSession(null);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };
        CustomTabsClient.bindCustomTabsService(context, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(mCustomTabsSession);
        if(globalClass.isDarkThemeEnabled()){
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTextBlack));
            builder.setSecondaryToolbarColor(ContextCompat.getColor(context,R.color.colorWhite));
        }else{
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        builder.setCloseButtonIcon(drawableToBitmap(globalClass.Application_Context.getDrawable(R.drawable.ic_ab_back_material_light)));
        builder.setShowTitle(true);
        builder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right);
        customTabsIntent = builder.build();
    }

    @Override
    public PostFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_post,parent,false);
//
//        globalClass = (GlobalClass) v.getContext().getApplicationContext();
//        Log.d("FEED_ADAPTER","Global Class : " + globalClass);

        return new PostFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PostFeedViewHolder holder, final int position) {
        Context context = holder.postImage.getContext();

        final Post post = mDataSet.get(position);
        Log.d("PostActivity","SET " + position + " TO : " + post.title);

        ((TextView)holder.postFeedView.findViewById(R.id.postTitleText)).setText(post.title);
        holder.getPostDescriptionText().setText(post.description);
//        holder.getPostUrlText().setText(post.url);
        if (globalClass.isDataSaverOn()){
            holder.getPostImageDownloadBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadAndLoadImageToImageView(holder,post.urlToImage);
                    holder.getPostImageDownloadBtn().setVisibility(View.GONE);
                }
            });
        }else{
            downloadAndLoadImageToImageView(holder,post.urlToImage);
            holder.getPostImageDownloadBtn().setVisibility(View.GONE);
        }

//        Glide.with(context)
//                .load(post.urlToImage)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.postImage);
    }

    public void downloadAndLoadImageToImageView(PostFeedViewHolder holder,String url){
        Context context = holder.postImage.getContext();
        holder.getPostImage().setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private static void goToUrl (Context context , String url) {
//        Uri uriUrl = Uri.parse(url);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//        Intent launchBrowser = new Intent(context,WebActivity.class);
//        launchBrowser.putExtra("URL",url);
//       context.startActivity(launchBrowser);
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(context, Uri.parse(url));

        customTabsIntent.launchUrl(context, Uri.parse(url));
    }


//    http://corochann.com/convert-between-bitmap-and-drawable-313.html
    public Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
