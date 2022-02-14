package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.Post;

public class MainOnePost {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("post")
    @Expose
    private Post posts;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Post getPosts() {
        return posts;
    }

    public void setPosts(Post posts) {
        this.posts = posts;
    }
}
