package afisha.arzan.tm.api;

import android.content.SharedPreferences;
import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public  static final String BASE_URl="http://arzan.info:3020";

    public  static final String PREFS_NAME="My prefs file";

    public  static final String IMAGE_URL="http://arzan.info:3020/api";

    private static ApiClient apiClient;
    private static Retrofit retrofit;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;


    private ApiClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized ApiClient getInstance(){
        if (apiClient ==null){
            apiClient = new ApiClient();
        }
        return apiClient;
    }
    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }



    public static String dateConvert(Calendar date){
        if (date == null) return null;
        Date current = new Date();
        current.setTime(System.currentTimeMillis());
        return (String) DateUtils.getRelativeTimeSpanString(date.getTimeInMillis(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }


    public static String getTime(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;
    }

}
