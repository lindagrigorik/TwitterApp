package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterapp.TwitterApp;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

// DYNAMIC FRAGMENT
public class UserTimelineFragment extends TweetsListFragment {

    private ArrayList<Tweet> tweets;
    
    // UserTimelineFragment.newInstance(userId)
    public static UserTimelineFragment newInstance(String userId) {
	UserTimelineFragment fragmentDemo = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//userId = getActivity().getIntent().getStringExtra("userId");
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
    }
    
    @Override
    public void getTweets(String max) {
	// TODO Auto-generated method stub
	if (max== null) clearAdapter();
	String userId = getArguments().getString("userId");
	 TwitterApp.getRestClient().getUserTimeline(userId, max, new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray jsonTweets){
		    addToAdapter(jsonTweets);
		}
	    
		public void onFailure(Throwable e){
		    Log.d("DEBUG", e.toString());
		}
	    });
    }
}
