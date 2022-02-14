package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import afisha.arzan.tm.model.StatRegion;

public class Statistic {
    @SerializedName("regions")
    @Expose
    private List<StatRegion> statRegions;

    public List<StatRegion> getStatRegions() {
        return statRegions;
    }
}
