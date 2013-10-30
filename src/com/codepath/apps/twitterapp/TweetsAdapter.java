package com.codepath.apps.twitterapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet>{

    public TweetsAdapter(Context context, List<Tweet> tweets) {
	super(context, 0, tweets);
    }
    
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
	View view = convertView;
	if (view == null) {
	    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view = inflater.inflate(R.layout.tweet_item, null);
	}
	
	Tweet tweet = getItem(pos);
	
	ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
	imageView.setClickable(true);
	imageView.setTag(tweet.getUser().getIdStr());
	imageView.setOnClickListener(new OnClickListener(){
	    @Override
	        public void onClick(View v) {
		    Intent i = new Intent(getContext(), ProfileActivity.class);
		    i.putExtra("userId", v.getTag().toString());
		    getContext().startActivity(i);
	    }
	});
	ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
	
	
	TextView nameView = (TextView) view.findViewById(R.id.tvName);
	SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        String d = tweet.getCreatedAt();
        Date createdDate = new Date();
        try {
            createdDate = format.parse(d);
        } catch (ParseException e) {
            Log.d("DEBUG", e.getMessage());
        }
        
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(createdDate.getTime(), new Date().getTime(), DateUtils.MINUTE_IN_MILLIS);
	String formattedName="<b>"+tweet.getUser().getName()+"</b>" + " <small><font color='#777777'>@ " + timeAgo +"</font></small>";
	nameView.setText(Html.fromHtml(formattedName));
	
	TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
	bodyView.setText(Html.fromHtml(tweet.getBody()));
	
	return view;
	
    }
}
