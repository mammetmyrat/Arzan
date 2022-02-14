package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.Following;

public class MainFollowing {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("followers")
    @Expose
    private List<Following> followers;

    @SerializedName("followings")
    @Expose
    private List<Following> followings;

    public List<Following> getFollowings() {
        return followings;
    }


    public String getSuccess() {
        return success;
    }

    public String getCount() {
        return count;
    }

    public List<Following> getFollowers() {
        return followers;
    }
}
