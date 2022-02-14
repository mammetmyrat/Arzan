package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static afisha.arzan.tm.api.ApiClient.IMAGE_URL;

public class Notification implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        Date date = createdAt;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Turkmenistan/Ashgabat"));
        return sdf.format(date);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
