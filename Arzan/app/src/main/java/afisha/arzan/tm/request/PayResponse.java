package afisha.arzan.tm.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayResponse {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }
}
