package afisha.arzan.tm.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentServices {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JSONArray getRegions() throws JSONException {
        JSONObject jsonObject = new JSONObject(value);
        JSONArray region = jsonObject.getJSONArray("arr1");
        return region;
    }
    public JSONArray getServices() throws JSONException {
        JSONObject jsonObject = new JSONObject(value);
        JSONArray services = jsonObject.getJSONArray("arr2");
        return services;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
