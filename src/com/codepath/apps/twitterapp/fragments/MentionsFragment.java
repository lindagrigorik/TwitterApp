package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.twitterapp.EndlessScrollListener;
import com.codepath.apps.twitterapp.TwitterApp;
import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MentionsFragment extends TweetsListFragment {

    private ArrayList<Tweet> tweets;
    private String maxId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
    }
    
    @Override
    public void getTweets(String max){
	TwitterApp.getRestClient().getMentions(maxId, new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray jsonTweets){
		    tweets = Tweet.fromJson(jsonTweets);
		    if (tweets.size() > 0) {
			Long maxVal = Long.parseLong(tweets.get(tweets.size()-1).getIdStr()) -1;
			maxId = maxVal.toString();
			getAdapter().addAll(tweets);
			getView().onRefreshComplete();
		    
		    }
		}
	    
		public void onFailure(Throwable e){
		    Log.d("DEBUG", e.toString());
		}
	});
    }
}
