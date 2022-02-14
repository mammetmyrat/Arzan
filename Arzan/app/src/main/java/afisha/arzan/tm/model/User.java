package afisha.arzan.tm.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static afisha.arzan.tm.api.ApiClient.IMAGE_URL;


public class User implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("image")
    @Expose
    private String userImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("statusEndTime")
    @Expose
    private Date statusEndTime;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("followingCount")
    @Expose
    private int followingCount;
    @SerializedName("followerCount")
    @Expose
    private int followersCount;
    @SerializedName("postCount")
    @Expose
    private int postCount;

    @SerializedName("viewCount")
    @Expose
    private int viewCount;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getUserImage() {
        return IMAGE_URL + userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusEndTime() {
        return statusEndTime;
    }

    public void setStatusEndTime(Date statusEndTime) {
        this.statusEndTime = statusEndTime;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
