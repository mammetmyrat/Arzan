package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import afisha.arzan.tm.model.User;


public class LoginResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
