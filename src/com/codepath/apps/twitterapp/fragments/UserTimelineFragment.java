package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterapp.TwitterApp;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

    private ArrayList<Tweet> tweets;
    private String userId;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	userId = getActivity().getIntent().getStringExtra("userId");
	getTweets(null);
	
    }
    
    
    @Override
    public void getTweets(String max) {
	// TODO Auto-generated method stub
	 TwitterApp.getRestClient().getUserTimeline(userId, max, new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray jsonTweets){
		    /*tweets = Tweet.fromJson(jsonTweets);
		    Long maxVal = Long.parseLong(tweets.get(tweets.size()-1).getIdStr()) -1;
		    String maxId = maxVal.toString();
		    setMax(maxId);
		    getAdapter().addAll(tweets);
		    getView().onRefreshComplete();*/
		    addToAdapter(jsonTweets);
		}
	    
		public void onFailure(Throwable e){
		    Log.d("DEBUG", e.toString());
		}
	    });
    }

}
