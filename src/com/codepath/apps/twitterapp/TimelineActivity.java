package com.codepath.apps.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.activeandroid.query.Select;
import com.codepath.apps.twitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterapp.fragments.MentionsFragment;
import com.codepath.apps.twitterapp.fragments.TweetsListFragment;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

//import eu.erikw.PullToRefreshListView;
//import eu.erikw.PullToRefreshListView.OnRefreshListener;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TimelineActivity extends FragmentActivity implements TabListener {
    
    private static final int REQUEST_CODE = 1;
    private User currUser;
    private TweetsListFragment fragmentTweets;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_timeline);
	//fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
	this.setUpNavigationTabs();
	//getActionBar().setSelectedNavigationItem(0)
	getUser();
    }
    
    private void setUpNavigationTabs() {
	ActionBar actionBar = getActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	actionBar.setDisplayShowTitleEnabled(true);
	Tab tabHome = actionBar.newTab().setText("Home")
		.setTag("HomeTimelineFragment")
		.setIcon(R.drawable.ic_action_home)
		.setTabListener(this);
	Tab tabMentions = actionBar.newTab().setText("mentions")
		.setTag("MentionsFragment")
		.setIcon(R.drawable.ic_action_at)
		.setTabListener(this);
	
	actionBar.addTab(tabHome);
	actionBar.addTab(tabMentions);
	actionBar.selectTab(tabHome);
    }

    private void getUser() {
	TwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
	    @Override
	    public void onSuccess(JSONObject jsonTweets){
		User user = User.fromJson(jsonTweets);
		currUser = user;
	    }
	});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.timeline, menu);
	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
	ConnectivityManager cm =
	        (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	boolean isConnected = activeNetwork != null &&
	                      activeNetwork.isConnectedOrConnecting();
	Intent i;
	if (isConnected) {
	    switch (menu.getItemId()) {
	    case R.id.action_tweet:
    		i = new Intent(getApplicationContext(), NewTweet.class);
    		i.putExtra("user", currUser);
    		startActivityForResult(i, REQUEST_CODE);
    		return true;
	    case R.id.action_profile:
		i = new Intent(this, ProfileActivity.class);
		i.putExtra("userId", currUser.getIdStr());
		startActivity(i);
		return true;
	    }
	} else {
	    Toast.makeText(getBaseContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
	}
    		return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent i){
	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	    TwitterApp.getRestClient().addTweet(i.getStringExtra("tweet"), new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject jsonResult) {
		    ActionBar actionBar = getActionBar();
		    FragmentManager manager = getSupportFragmentManager();
			android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
			if (actionBar.getSelectedTab().getTag().equals("HomeTimelineFragment")){
			    fts.replace(R.id.frame_container, new HomeTimelineFragment());
			} else {
			    fts.replace(R.id.frame_container, new MentionsFragment());
			}
			fts.commit();
		}
		
		@Override
		public void onFailure(Throwable e, JSONObject jsonResult) {
		    Log.d("DEBUG", e.toString());
		}
	    });
	}
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	FragmentManager manager = getSupportFragmentManager();
	android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
	if (tab.getTag().equals("HomeTimelineFragment")){
	    fts.replace(R.id.frame_container, new HomeTimelineFragment());
	} else {
	    fts.replace(R.id.frame_container, new MentionsFragment());
	}
	fts.commit();
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	// TODO Auto-generated method stub
	
    }

}
