package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {
    private TwitterClient client;
    private User user;
    private ImageView ivProfileImage;
    private TextView tvName;
    private TextView tvAtName;
    private EditText etTweet;
    private TextView tvChar;
    private int totalChar = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_twitter);
        client = TwitterApplication.getRestClient();
        client.getAccountInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                populateComposeView(user);
            }
        });
    }

    private void populateComposeView(User user) {
        ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAtName = (TextView) findViewById(R.id.tvAtName);
        tvChar = (TextView)findViewById(R.id.tvChar);
        tvChar.setText(String.valueOf(totalChar));
        etTweet = (EditText)findViewById(R.id.etTweet);
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvChar.setText(String.valueOf(totalChar - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvName.setText(user.getScreenName());
        tvAtName.setText("@"+user.getScreenName());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//
//    private void sendTweet() {
//        Intent data = new Intent();
//        // Pass relevant data back as a result
//        data.putExtra("tweet", etTweet.getText().toString());
//        Log.d("DEBUG", etTweet.getText().toString());
//        data.putExtra("code", 200); // ints work too
//        // Activity finished ok, return the data
//        setResult(RESULT_OK, data); // set result code and bundle data for response
//        finish();
//    }

    public void onPostTweet(MenuItem item) {
        String tweet = etTweet.getText().toString();
        client.postTweet(tweet, new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                Tweet t = Tweet.fromJSON(json);
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("tweet", t);
                data.putExtra("code", 200); // ints work too
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish();
            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
