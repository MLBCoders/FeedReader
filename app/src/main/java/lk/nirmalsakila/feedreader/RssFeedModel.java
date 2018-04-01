package lk.nirmalsakila.feedreader;

/**
 * Created by user on 3/12/2018.
 */

public class RssFeedModel {

    public String title;
    public String link;
    public String description;
    public String imageURI;
    public String creator;
    public String pubDate;

    public RssFeedModel(String title, String link, String description, String imageURI, String creator, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.imageURI = imageURI;
        this.creator = creator;
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "Title : " + title + " Link : " + link + " Desc : " + description + " Creator : " + creator + " pubDAte : " + pubDate + " image : " + imageURI;
     }
}
