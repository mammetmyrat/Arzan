package afisha.arzan.tm.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import afisha.arzan.tm.model.Notification;

public class MainNotification implements Serializable {
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
