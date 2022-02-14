package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostLike {
    @SerializedName("all")
    @Expose
    private int all;

    public int getAll() {
        return all;
    }
}
