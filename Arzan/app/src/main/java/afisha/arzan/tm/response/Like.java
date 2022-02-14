package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import afisha.arzan.tm.model.Result;

public class Like {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
