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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_timeline);
	
	TwitterApp.getRestClient().getHomeTimeLine(new JsonHttpResponseHandler() {
	    @Override
	    public void onSuccess(JSONArray jsonTweets){
		ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
		
		ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
		TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);
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
