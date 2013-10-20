package com.codepath.apps.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class TimelineActivity extends Activity {

    private ListView lvTweets;
    private ArrayList<Tweet> tweets;
    private TweetsAdapter adapter;
    private String maxId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_timeline);
	lvTweets = (ListView) findViewById(R.id.lvTweets);
	tweets = new ArrayList<Tweet>();
	adapter = new TweetsAdapter(getBaseContext(), tweets);
	lvTweets.setAdapter(adapter);
	getTweets(null);
	lvTweets.setOnScrollListener(new EndlessScrollListener(){
	    @Override
            public void loadMore(int page, int totalItemsCount) {
	        // TODO Auto-generated method stub
	        getTweets(maxId);
            }
	});
	
    }
    
    private void getTweets(String max){
	TwitterApp.getRestClient().getHomeTimeLine(max, new JsonHttpResponseHandler() {
	    @Override
	    public void onSuccess(JSONArray jsonTweets){
		tweets = Tweet.fromJson(jsonTweets);
		//get the maximum id to retrieve the next set of tweets.
		maxId = Double.toString(tweets.get(tweets.size()-1).getId());
		adapter.addAll(tweets);
		/*TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);*/
		Log.d("DEBUG", jsonTweets.toString());
	    }
	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.timeline, menu);
	return true;
    }

}
