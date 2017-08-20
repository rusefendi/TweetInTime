package com.rusefendi.posting.model;

public class Post {
    private String title;
    private String imageURL;
    private String link;

    public Post(String title, String imageURL, String link){
        this.title = title;
        this.imageURL = imageURL;
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setImageUrl(String imageURL){
        this.imageURL = imageURL;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    @Override
    public String toString(){
        return "Post [title = " + title + ", imageURL = " + imageURL + ", link = " + link + "]";
    }
}
