package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowingResponse {
    @SerializedName("followings")
    @Expose
    List<Integer> followings ;

    public List<Integer> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }
}
