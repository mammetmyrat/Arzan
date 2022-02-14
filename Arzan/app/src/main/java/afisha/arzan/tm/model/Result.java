package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("post")
    @Expose
    private PostLike post;

    public PostLike getPost() {
        return post;
    }
}
