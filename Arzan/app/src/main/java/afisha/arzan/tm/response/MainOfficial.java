package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.User;

public class MainOfficial {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("users")
    @Expose
    private List<User> official;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<User> getOfficial() {
        return official;
    }

    public void setOfficial(List<User> official) {
        this.official = official;
    }
}
