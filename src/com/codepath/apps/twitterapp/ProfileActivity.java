package com.codepath.apps.twitterapp;

import org.json.JSONObject;

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
	getUser();
    }
    
    private void getUser() {
	TwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
	    @Override
	    public void onSuccess(JSONObject jsonTweets){
		User user = User.fromJson(jsonTweets);
		getActionBar().setTitle("@"+user.getScreenName());
		populateProfileHeader(user);
	    }
	});
    }

    private void populateProfileHeader(User u){
	ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
	TextView tvName = (TextView) findViewById(R.id.tvName);
	TextView tvFollowers = (TextView) findViewById(R.id.tvFollower);
	TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
	TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
	
	tvName.setText(u.getName());
	tvFollowers.setText(u.getFollowersCount()+" followers");
	tvFollowing.setText(u.getFriendsCount() + " following");
	ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.profile, menu);
	return true;
    }

}
