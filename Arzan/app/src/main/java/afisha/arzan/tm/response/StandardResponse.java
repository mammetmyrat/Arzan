package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardResponse {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("success")
    @Expose
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
