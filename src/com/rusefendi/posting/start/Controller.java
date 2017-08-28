package com.rusefendi.posting.start;

import com.rusefendi.posting.model.Post;
import com.rusefendi.posting.rss.RSSFeedParser;
import com.rusefendi.posting.twitter.TwitterPost;

import java.util.List;

public class Controller {
    public static void main(String[] args){
        final int minutesPerPost = 60;
        final int numberOfPosts = 12;

        List<Post> entries;
        int i;

        RSSFeedParser parser = new RSSFeedParser(args[0]);
        System.out.println("RSSFeedParser created");

        TwitterPost twitterPost = new TwitterPost(args[1],
                args[2],
                args[3],
                args[4]);
        System.out.println("TwitterPost created");

        while(true){
            i = 0;
            System.out.println("Parsing feed");
            entries = parser.readFeed();
            for(Post entr : entries){
                System.out.println(entr);
            }

            for(Post entr : entries){
                System.out.println("Tweeting " + (i + 1) + " post from " + numberOfPosts);
                System.out.println(entr);
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
