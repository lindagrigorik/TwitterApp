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

    
    private ArrayList<Tweet> tweets;
    
    private String maxId;
    private static final int REQUEST_CODE = 1;
    private User currUser;
    private TweetsListFragment fragmentTweets;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_timeline);
	//fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
	this.setUpNavigationTabs();
	
	//tweets = new ArrayList<Tweet>();
	
	
	//getTweets(null);
	/*lvTweets.setOnRefreshListener(new OnRefreshListener() {
        @Override
            public void onRefresh() {
            // Your code to refresh the list contents
            // Make sure you call listView.onRefreshComplete()
            // once the loading is done. This can be done from here or any
            // place such as when the network request has completed successfully.
        	adapter.clear();
                getTweets(null);
            }
	    });
	lvTweets.setOnScrollListener(new EndlessScrollListener(){
	@Override
        public void loadMore(int page, int totalItemsCount) {
	    // TODO Auto-generated method stub
	    getTweets(maxId);
        }
	});*/
	getUser();

	//database test.
	//User u = getRandom();
	//Toast.makeText(getBaseContext(), u.getName(), Toast.LENGTH_SHORT).show();
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
    
    public static User getRandom() {
	return new Select().from(User.class).orderBy("RANDOM()").executeSingle();
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
	if (isConnected) {
    		Intent i = new Intent(getApplicationContext(), NewTweet.class);
    		i.putExtra("profile_url", currUser.getProfileImageUrl());
    		i.putExtra("user_name", currUser.getScreenName());
    		startActivityForResult(i, REQUEST_CODE);
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
		    Tweet tweet = Tweet.fromJson(jsonResult);
		    fragmentTweets.getAdapter().clear();
	            //getTweets(null);
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
    
    public void onProfileView(MenuItem mi) {
	Intent i = new Intent(this, ProfileActivity.class);
	startActivity(i);
    }
}
