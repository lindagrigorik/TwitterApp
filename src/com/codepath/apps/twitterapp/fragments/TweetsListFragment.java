package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.twitterapp.EndlessScrollListener;
import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.TweetsAdapter;
import com.codepath.apps.twitterapp.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class TweetsListFragment extends Fragment {
    private TweetsAdapter adapter;
    private PullToRefreshListView lvTweets;
    private ArrayList<Tweet> tweets;
    private String maxId;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	adapter = new TweetsAdapter(getActivity(), tweets);
	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	
	View v =  inflater.inflate(R.layout.fragments_tweets_list, parent, false);
	lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
	lvTweets.setAdapter(adapter);
	lvTweets.setOnRefreshListener(new OnRefreshListener() {
	        @Override
	            public void onRefresh() {
	            // Your code to refresh the list contents
	            // Make sure you call listView.onRefreshComplete()
	            // once the loading is done. This can be done from here or any
	            // place such as when the network request has completed successfully.
	        	getAdapter().clear();
	                getTweets(null);
	            }
	});
	lvTweets.setOnScrollListener(new EndlessScrollListener(){
	@Override
	public void loadMore(int page, int totalItemsCount) {
		// TODO Auto-generated method stub
		getTweets(maxId);
	}
	});
	
	return v;
    }
    
    public abstract void getTweets(String maxId);
    
    protected void addToAdapter(JSONArray jsonTweets){
	tweets = Tweet.fromJson(jsonTweets);
	if (tweets.size() > 0) {
	    	Long maxVal = tweets.get(tweets.size()-1).getId()-1;
		maxId = maxVal.toString();
		getAdapter().addAll(tweets);
		//adapter.notifyAll();
		getView().onRefreshComplete();
	}
    }
    
    public TweetsAdapter getAdapter() {
	return adapter;
    }
    
    public PullToRefreshListView  getView() {
	return lvTweets;
    }
    
}
