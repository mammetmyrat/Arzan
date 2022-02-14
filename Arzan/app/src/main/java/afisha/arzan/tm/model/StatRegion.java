package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatRegion {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("week")
    @Expose
    private Week week;
    @SerializedName("day")
    @Expose
    private Day day;
    @SerializedName("month")
    @Expose
    private Month month;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public Week getWeek() {
        return week;
    }

    public Month getMonth() {
        return month;
    }

    public Day getDay() {
        return day;
    }
}
