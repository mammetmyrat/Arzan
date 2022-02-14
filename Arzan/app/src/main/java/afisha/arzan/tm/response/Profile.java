package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.Banner;
import afisha.arzan.tm.model.User;

public class Profile {
    @SerializedName("banners")
    @Expose
    private List<Banner> banners;
    @SerializedName("user")
    @Expose
    private User user;

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
