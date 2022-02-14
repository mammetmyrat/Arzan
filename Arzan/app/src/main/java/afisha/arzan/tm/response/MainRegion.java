package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.Region;

public class MainRegion {
    @SerializedName("regions")
    @Expose
    private List<Region> regions;

    public List<Region> getRegions() {
        return regions;
    }

}
