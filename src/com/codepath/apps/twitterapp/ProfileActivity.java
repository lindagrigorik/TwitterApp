package com.codepath.apps.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile);
	String userId = getIntent().getStringExtra("userId");
	getUser(userId);
    }
    
    private void getUser(String userId) {
	TwitterApp.getRestClient().getUserTimeline(userId, null, new JsonHttpResponseHandler() {
	    @Override
	    public void onSuccess(JSONArray jsonTweets){
		ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
		if (tweets.size()>0){
		    User u = tweets.get(0).getUser();
		    populateProfileHeader(u);
		}
	    }
	});
    }

    private void populateProfileHeader(User u){
	ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
	TextView tvName = (TextView) findViewById(R.id.tvName);
	TextView tvFollowers = (TextView) findViewById(R.id.tvFollower);
	TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
	TextView tvNumTweets = (TextView) findViewById(R.id.tvNumTweets);
	TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
	
	tvName.setText(u.getName());
	tvFollowers.setText(u.getFollowersCount()+" followers");
	tvFollowing.setText(u.getFriendsCount() + " following");
	tvNumTweets.setText(u.getNumTweets() + " tweets");
	tvTagline.setText(u.getTagline());
	ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.profile, menu);
	return true;
    }

}
