package afisha.arzan.tm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import afisha.arzan.tm.api.ApiClient;

import static afisha.arzan.tm.api.ApiClient.IMAGE_URL;


public class Post implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("contacts")
    @Expose
    private String contacts;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("activatedAt")
    @Expose
    private String activatedAt;
    @SerializedName("isCancelled")
    @Expose
    private String isCancelled;
    @SerializedName("cancelNote")
    @Expose
    private String cancelNote;
    @SerializedName("commentCount")
    @Expose
    private int commentCount;
    @SerializedName("recommendedAt")
    @Expose
    private String recommendedAt;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("isFavorite")
    @Expose
    private int isFavorite;
    @SerializedName("isLike")
    @Expose
    private int isLike;
    @SerializedName("likeCount")
    @Expose
    private int likeCount;
    @SerializedName("viewCount")
    @Expose
    private int viewCount;
    @SerializedName("shareCount")
    @Expose
    private int shareCount;
    @SerializedName("favoriteCount")
    @Expose
    private int favoriteCount;
    @SerializedName("regions")
    @Expose
    private List<Region> regions;

    protected Post(Parcel in) {
        id = in.readInt();
        images = in.readString();
        title = in.readString();
        content = in.readString();
        contacts = in.readString();
        keywords = in.readString();
        activatedAt = in.readString();
        isCancelled = in.readString();
        cancelNote = in.readString();
        commentCount = in.readInt();
        recommendedAt = in.readString();
        userId = in.readInt();
        isFavorite = in.readInt();
        isLike = in.readInt();
        likeCount = in.readInt();
        viewCount = in.readInt();
        shareCount = in.readInt();
        favoriteCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(images);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(contacts);
        dest.writeString(keywords);
        dest.writeString(activatedAt);
        dest.writeString(isCancelled);
        dest.writeString(cancelNote);
        dest.writeInt(commentCount);
        dest.writeString(recommendedAt);
        dest.writeInt(userId);
        dest.writeInt(isFavorite);
        dest.writeInt(isLike);
        dest.writeInt(likeCount);
        dest.writeInt(viewCount);
        dest.writeInt(shareCount);
        dest.writeInt(favoriteCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstImage() {
        try {
            JSONArray arr = new JSONArray(images);
            String image = arr.get(0).toString();
            return IMAGE_URL+image;
        } catch (JSONException e) {
            return e.getLocalizedMessage();
        }
    }
    public List<String> getImages() throws JSONException {
        JSONArray arr = new JSONArray(images);
        List<String> image = new ArrayList<>();
        for (int i=0;i<arr.length();i++){
            image.add(IMAGE_URL+arr.getString(i));
        }
        return image;
    }

    public String getCreatedAt() {
        Calendar calendar = Calendar.getInstance();
        if (createdAt != null) calendar.setTimeInMillis(createdAt.getTime());
        return ApiClient.dateConvert(calendar);
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

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(String activatedAt) {
        this.activatedAt = activatedAt;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getCancelNote() {
        return cancelNote;
    }

    public void setCancelNote(String cancelNote) {
        this.cancelNote = cancelNote;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getRecommendedAt() {
        return recommendedAt;
    }

    public void setRecommendedAt(String recommendedAt) {
        this.recommendedAt = recommendedAt;
    }

    public String getDateObject() {
        Date date = createdAt;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Turkmenistan/Ashgabat"));
        return sdf.format(date);
    }
    public String getClock(){
        Date date = createdAt;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Turkmenistan/Ashgabat"));
        return sdf.format(date);
    }
    public ArrayList<String> getContacts() {
        ArrayList<String> list = new ArrayList<String>();

        try {
            JSONArray jsonArray = new JSONArray(contacts);

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }

            return list;
        } catch (Exception err) {
            return list;
        }
    }
    public ArrayList<String> getLinks() {
        Pattern urlPattern = Pattern.compile("^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}\\.([a-z]+)?$");
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        ArrayList<String> list = new ArrayList<String>(), contacts = this.getContacts();



        for (String s : contacts) {
            boolean b1 = urlPattern.matcher(s).matches();
            boolean b2 = emailPattern.matcher(s).matches();

            if (b1 || b2) list.add(s);
        }

        return list;
    }

    public ArrayList<String> getPhones() {
        Pattern urlPattern = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

        ArrayList<String> list = new ArrayList<String>(), contacts = this.getContacts();
        for (String s : contacts) {
            boolean b1 = urlPattern.matcher(s).matches();

            if (b1) list.add(s);
        }

        return list;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount+= likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount+=viewCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount += shareCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }


    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
