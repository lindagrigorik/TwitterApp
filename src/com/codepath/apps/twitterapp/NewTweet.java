package com.codepath.apps.twitterapp;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewTweet extends Activity {

    private TextView tvCharCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_new_tweet);
	ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
	ImageLoader.getInstance().displayImage(getIntent().getStringExtra("profile_url"), ivProfile);
	TextView tvUser = (TextView) findViewById(R.id.tvUserName);
	tvUser.setText("@"+getIntent().getStringExtra("user_name"));
	EditText etTweet =  (EditText) findViewById(R.id.etTweet);
	tvCharCount = (TextView) findViewById(R.id.tvCharCount);
	etTweet.addTextChangedListener(mTextEditorWatcher);
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
           //This sets a textview to the current length
           if (s.length() > 140){
               tvCharCount.setText(Integer.parseInt(String.valueOf(s.length()))-140 + " characters too long");
           } else {
               tvCharCount.setText(140-Integer.parseInt(String.valueOf(s.length())) + " characters space left"); 
           }
        }

        public void afterTextChanged(Editable s) {
        }
};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.new_tweet, menu);
	return true;
    }
    
    public void onTweet(View v){
	EditText etTweet = (EditText) findViewById(R.id.etTweet);
	String tweet = etTweet.getText().toString();
	Intent i = new Intent();
	i.putExtra("tweet", tweet);
	setResult(RESULT_OK, i);
	finish();
    }

}
