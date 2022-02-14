package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Month {
    @SerializedName("postCount")
    @Expose
    private int postCount;
    @SerializedName("viewCount")
    @Expose
    private int viewCount;
    @SerializedName("shareCount")
    @Expose
    private int shareCount;
    @SerializedName("likeCount")
    @Expose
    private int likeCount;

    public int getPostCount() {
        return postCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
