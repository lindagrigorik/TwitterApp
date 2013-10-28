package com.codepath.apps.twitterapp.fragments;

import java.util.ArrayList;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.TweetsAdapter;
import com.codepath.apps.twitterapp.models.Tweet;

import eu.erikw.PullToRefreshListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TweetsListFragment extends Fragment {
    private TweetsAdapter adapter;
    private PullToRefreshListView lvTweets;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	adapter = new TweetsAdapter(getActivity(), tweets);
	lvTweets.setAdapter(adapter);
	super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	
	return inflater.inflate(R.layout.fragments_tweets_list, parent, false);
    }
    public TweetsAdapter getAdapter() {
	return adapter;
    }
}
