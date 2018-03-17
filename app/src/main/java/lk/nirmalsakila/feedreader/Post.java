package lk.nirmalsakila.feedreader;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by user on 3/17/2018.
 */

public class Post {
    @SerializedName("id")
    long ID;

    @SerializedName("date")
    Date publishedAt;

    String title;
    String author;
    String description;
    String urlToImage;
    String url;

}
