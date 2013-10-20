package com.codepath.apps.twitterapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
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
	ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
	
	TextView nameView = (TextView) view.findViewById(R.id.tvName);
	DateFormat df = new SimpleDateFormat("MM/dd/yy, hh:mm:ss");
	
	//FIXME: format date string returned.
	/*Date createdTime = null;
        try {
	    createdTime = df.parse(tweet.getUser().getCreatedAt());
        } catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
        }*/
        
	String formattedName="<b>"+tweet.getUser().getName()+"</b>" + " <small><font color='#777777'>@" + tweet.getUser().getCreatedAt() +"</font></small>";
	nameView.setText(Html.fromHtml(formattedName));
	
	TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
	bodyView.setText(Html.fromHtml(tweet.getBody()));
	
	return view;
	
    }
}
