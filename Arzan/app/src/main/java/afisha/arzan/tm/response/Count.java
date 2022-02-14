package afisha.arzan.tm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Count {
    @SerializedName("officials")
    @Expose
    private int officials;
    @SerializedName("notifications")
    @Expose
    private int notifications;

    public int getOfficials() {
        return officials;
    }

    public void setOfficials(int officials) {
        this.officials = officials;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }
}
