package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.twitterapp.EndlessScrollListener;
import com.codepath.apps.twitterapp.TwitterApp;
import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class HomeTimelineFragment extends TweetsListFragment {

    private String maxId;
    private ArrayList<Tweet> tweets;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	getTweets(null);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	
    }
    
    @Override
    public void getTweets(String max){
	/*ConnectivityManager cm =
	        (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	boolean isConnected = activeNetwork != null &&
	                      activeNetwork.isConnectedOrConnecting();

	if (!isConnected){
	    ArrayList<Tweet> tweets = new Select().from(Tweet.class).orderBy("IdStr DESC").limit("20").execute();
	    adapter.clear();
	    adapter.addAll(tweets);
	    //Toast.makeText(getBaseContext(), "TIME TO LOAD LOCAL DATA", Toast.LENGTH_SHORT).show();
	} else {*/
	    TwitterApp.getRestClient().getHomeTimeLine(max, new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray jsonTweets){
		    tweets = Tweet.fromJson(jsonTweets);
		    //get the maximum id to retrieve the next set of tweets.
		    Long maxVal = Long.parseLong(tweets.get(tweets.size()-1).getIdStr()) -1;
		    maxId = maxVal.toString();
		    getAdapter().addAll(tweets);
		    getView().onRefreshComplete();
		    Log.d("DEBUG", jsonTweets.toString());
		  
		}
	    
		public void onFailure(Throwable e){
		    Log.d("DEBUG", e.toString());
		}
		
		@Override
		protected void sendFailureMessage(Throwable e, String body) {
		    super.sendFailureMessage(e, body);
		    Log.d("DEBUG", e.toString());
		}
	    });
    }
	  
}
