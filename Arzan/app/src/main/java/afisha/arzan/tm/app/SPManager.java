package afisha.arzan.tm.app;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.model.Region;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SPManager {

    public static final String
            TOKEN = "token",
            AUTH = "auth",
            MY_ID = "myid",
            ID = "id",
            NOTIFY_COUNT = "notifyCount",
            POST_COUNT = "postCount",
            OFFICIAL_COUNT = "officialCount",
            STATUS = "status",
            NAME = "username",
            LANG = "lang",
            REGION = "regionName",
            REGION_ID = "regionId",
            LAST_REGION_ID = "lastRegionId",
            isFirst = "isFirst",
            FIRE_TOKEN = "fireToken",
            POS = "postition";


    private static SPManager manager;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    public static String POST_LINK = "http://arzan.info:5152/posts/";
    public static String POSTS_LINK = "http://arzan.info/posts/";
    public static String USER_LINK = "https://arzan.info/users/";
    public static final String METRICA_API_KEY = "daecd567-e51a-4b6d-bed0-de49447d8ba7";


    public static final MediaType TEXT_TYPE = MediaType.parse("text/plain");
    public static final MediaType IMAGE_TYPE = MediaType.parse("image/*");


    private SPManager(Context context) {
        sharedPreferences = context.getSharedPreferences("tmafisha_data", Context.MODE_PRIVATE);
    }
    public static SPManager getInstance(Context context) {
        if (manager == null) manager = new SPManager(context);
        return manager;
    }

    public String getData(String key , String defValue){
        return sharedPreferences.getString(key , defValue);
    }

    public Integer getIntData(String key , int defValue){
        return sharedPreferences.getInt(key , defValue);
    }
    public boolean getBoolean(String key , boolean defValue){
        return sharedPreferences.getBoolean(key , defValue);
    }
    public void setData(String key , String newData){
        editor = sharedPreferences.edit();
        editor.putString(key,newData);
        editor.apply();
    }
    public void setIntData(String key , int newData){
        editor = sharedPreferences.edit();
        editor.putInt(key,newData);
        editor.apply();
    }

    public void setBoolean(String key , boolean newData){
        editor = sharedPreferences.edit();
        editor.putBoolean(key,newData);
        editor.apply();
    }
    public static MultipartBody.Part partGenerate(RequestBody body , String type){
        if (body == null) return MultipartBody.Part.createFormData(type,"0");
        return MultipartBody.Part.createFormData(type ,type+".jpg" , body);
    }
    public static void doneDialog(Context context,String string,Dialog dialog){
        dialog.setContentView(R.layout.done_dialog);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView text = dialog.findViewById(R.id.text);
        text.setText(string);
        dialog.show();
        dialog.setCancelable(true);
    }
    public static void loading(Dialog dialog){
        dialog.setContentView(R.layout.loaging);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);
    }

    public static List<Region> getRegions(){
        List<Region> regions = new ArrayList<>();
        Region ag = new Region();
        ag.setId(6);
        ag.setName("Aşgabat");
        regions.add(ag);
        Region ah = new Region();
        ah.setId(2);
        ah.setName("Ahal");
        regions.add(ah);
        Region mr = new Region();
        mr.setId(3);
        mr.setName("Mary");
        regions.add(mr);
        Region lb = new Region();
        lb.setId(4);
        lb.setName("Lebap");
        regions.add(lb);
        Region dz = new Region();
        dz.setId(5);
        dz.setName("Daşoguz");
        regions.add(dz);
        Region bn = new Region();
        bn.setId(1);
        bn.setName("Balkan");
        regions.add(bn);


        return regions;
    }

}
