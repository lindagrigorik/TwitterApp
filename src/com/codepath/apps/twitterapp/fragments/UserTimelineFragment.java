package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterapp.TwitterApp;
import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

    private ArrayList<Tweet> tweets;
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	getTweets(null);
	
    }
    @Override
    public void getTweets(String maxId) {
	// TODO Auto-generated method stub
	 TwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray jsonTweets){
		    tweets = Tweet.fromJson(jsonTweets);
		    getAdapter().addAll(tweets);
		   // lvTweets.onRefreshComplete();
		}
	    
		public void onFailure(Throwable e){
		    Log.d("DEBUG", e.toString());
		}
	    });
    }

}
