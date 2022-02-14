package afisha.arzan.tm.app;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.model.Notification;
import afisha.arzan.tm.model.PaymentServices;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.model.Region;
import afisha.arzan.tm.model.StatRegion;
import afisha.arzan.tm.model.User;

public class Global {
    public static List<Region> regions = new ArrayList<>();
    public static List<Post> posts = new ArrayList<>();
    public static List<Uri> postImages = new ArrayList<>();
    public static Region region  ;
    public static List<String> images1 = new ArrayList<>();
    public static List<String> selectedRegions = new ArrayList<>();
    public static List<Notification> notifications = new ArrayList<>();

    public static User myProfile;
    public static StatRegion statRegion;
    public static PaymentServices paymentServices;
    public static int money;
    public static Post post;
    public static int NOTIFY_COUNT;
    public static int OFFICIAL_COUNT;


    public static List<Integer> followings = new ArrayList<>();





}
