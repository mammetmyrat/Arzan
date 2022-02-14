package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.Banner;

public class MainBanner {
    @SerializedName("banners")
    @Expose
    private List<Banner> banners;

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }
}
