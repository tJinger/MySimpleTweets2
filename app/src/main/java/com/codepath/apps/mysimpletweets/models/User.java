package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tjing on 10/21/15.
 */
public class User implements Serializable{
    // list attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followersCount;
    private int followingsCount;

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}
