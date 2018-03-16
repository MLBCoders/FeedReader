package lk.nirmalsakila.feedreader;

/**
 * Created by user on 3/12/2018.
 */

class RssFeedModel {

    public String title;
    public String link;
    public String description;
    public String imageURI;

    public RssFeedModel(String title, String link, String description, String imageURI) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.imageURI = imageURI;
    }

}
