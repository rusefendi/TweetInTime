package com.rusefendi.posting.start;

import com.rusefendi.posting.model.Post;
import com.rusefendi.posting.rss.RSSFeedParser;
import com.rusefendi.posting.twitter.TwitterPost;

import java.util.List;

public class Controller {
    public static void main(String[] args){
        final int minutesPerPost = 45;
        final int numberOfPosts = 24;

        List<Post> entries;
        int i;

        RSSFeedParser parser = new RSSFeedParser("https://pikabu.ru/xmlfeeds.php?cmd=popular");
        System.out.println("RSSFeedParser created");

        TwitterPost twitterPost = new TwitterPost("33F6LugqzzM6NAbAJAqOTBR0F",
                "b0CkKt5v6J3vT9gTziS7w6jGBXNX0tGrQWU37PXPtz8cMSO3RS",
                "915101400-gMY1SYhbu0dbyZQoy986uFbSmm8xykyafwxg1qLk",
                "JhudrgJMZboFl4yWjWInw5OE3F4M2hekjtebcS7CeuiFU");
        System.out.println("TwitterPost created");

        while(true){
            i = 0;
            System.out.println("Parsing feed");
            entries = parser.readFeed();
            for(Post enti : entries){
                System.out.println(enti);
            }
            for(Post entr : entries){
                System.out.println("Tweeting " + (i + 1) + " post from " + numberOfPosts);
                twitterPost.post(entr);
                i++;
                try{
                    System.out.println("Sleeping for " + minutesPerPost + " minutes");
                    Thread.sleep(1000*60*minutesPerPost);
                } catch(Exception e){
                    e.printStackTrace();
                }
                if(i >= numberOfPosts) break;
            }
        }
    }
}
