package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Following {
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("followerId")
    @Expose
    private int followerId;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("follower")
    @Expose
    private User follower;
    @SerializedName("user")
    @Expose
    private User user;
    public int getUserId() {
        return userId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User getFollower() {
        return follower;
    }

    public User getUser() {
        return user;
    }
}
