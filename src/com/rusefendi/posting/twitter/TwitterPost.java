package com.rusefendi.posting.twitter;

import com.rusefendi.posting.model.Post;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TwitterPost {
    String consumerKey;
    String consumerSecret;
    String accessToken;
    String accessSecret;

    public TwitterPost(String consumerKey, String consumerSecret, String accessToken, String accessSecret){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessSecret = accessSecret;
    }


    public void post(Post post){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try{
            StatusUpdate statusUpdate = new StatusUpdate(post.getTitle() + " " + post.getLink());
            String imageURL = post.getImageURL();
            URL url = new URL(imageURL);
            URLConnection urlConnection = url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            statusUpdate.setMedia("", in);
            twitter4j.Status status = twitter.updateStatus(statusUpdate);

            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
