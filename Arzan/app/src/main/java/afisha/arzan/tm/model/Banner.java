package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import static afisha.arzan.tm.api.ApiClient.IMAGE_URL;

public class Banner {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("payload")
    @Expose
    private String payload;

    public String getPayload() {
        return payload;
    }

    public String linkType() throws JSONException {
        JSONObject jsonObject =new JSONObject(payload);
        return jsonObject.getString("linkType");
    }

    public String linkId() throws JSONException {
        JSONObject jsonObject =new JSONObject(payload);
        return jsonObject.getString("link");
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return IMAGE_URL+image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
