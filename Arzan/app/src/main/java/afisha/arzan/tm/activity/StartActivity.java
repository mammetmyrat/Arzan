package afisha.arzan.tm.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.response.Count;
import afisha.arzan.tm.response.StandardResponse;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static afisha.arzan.tm.app.SPManager.NOTIFY_COUNT;
import static afisha.arzan.tm.app.SPManager.OFFICIAL_COUNT;
import static afisha.arzan.tm.app.SPManager.TOKEN;
import static android.content.ContentValues.TAG;

public class StartActivity extends AppCompatActivity {

    ConstraintLayout layout;
    ImageView logo,name;
    private boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        logo = findViewById(R.id.logo);
        layout = findViewById(R.id.layout);
        name = findViewById(R.id.name);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        logo.setAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.name_anim);
        name.setAnimation(animation1);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.layout_anim);
        layout.setAnimation(animation2);
        isFirst = SPManager.getInstance(this).getBoolean(SPManager.isFirst,true);
        getCounts();
        next();
    }
    private void getCounts(){
        String token = SPManager.getInstance(StartActivity.this).getData(TOKEN,"");
        Call<Count> countCall = ApiClient.getInstance().getApi().getCounts(token);
        countCall.enqueue(new Callback<Count>() {
            @Override
            public void onResponse(Call<Count> call, Response<Count> response) {
                if(response.isSuccessful()){

                    int oldOffCount = SPManager.getInstance(StartActivity.this).getIntData(OFFICIAL_COUNT,0);
                    int oldNotifyCount = SPManager.getInstance(StartActivity.this).getIntData(NOTIFY_COUNT,0);
                    int officials = response.body().getOfficials();
                    int notifications = response.body().getNotifications();
                    Log.e(TAG, "officials: "+officials );
                    Log.e(TAG, "notifications: "+notifications );

                    Global.OFFICIAL_COUNT = officials-oldOffCount;
                    Global.NOTIFY_COUNT = notifications-oldNotifyCount;
                    Log.e("TAG", "oldOffCount: "+oldOffCount);
                    Log.e("TAG", "oldNotifyCount: "+oldNotifyCount);


                    SPManager.getInstance(StartActivity.this).setIntData(OFFICIAL_COUNT,response.body().getOfficials());
                    SPManager.getInstance(StartActivity.this).setIntData(NOTIFY_COUNT,response.body().getNotifications());
                }else   Log.e(TAG, "officials: "+response.code());
            }

            @Override
            public void onFailure(Call<Count> call, Throwable t) {
                Log.e(TAG, "officials: "+t.getLocalizedMessage() );

            }
        });
    }
    public String ping(String url) {
        String str = "";
        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/ping -c 1 " + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            // body.append(output.toString()+"\n");
            str = output.toString();
            // Log.d(TAG, str);
        } catch (IOException e) {
            // body.append("Error\n");
            e.printStackTrace();
        }
        Log.e("TAG", "ping: "+str );
        return str;
    }
    public long pingg (String domain){
        long timeofping = 0;
        Runtime runtime = Runtime.getRuntime();
        try {
            long a = (System.currentTimeMillis() % 100000);
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 "+domain);
            ipProcess.waitFor();
            long b = (System.currentTimeMillis() % 100000);
            if (b <= a) {
                timeofping = ((100000 - a) + b);
            } else {
                timeofping = (b - a);
            }
        }catch (Exception e){

        }
        Log.e("TAG", "timeofping: "+timeofping );
        return timeofping;
    }

    private void next(){
        new Handler().postDelayed(() -> {
            if (isFirst){
                Intent intent = new Intent(StartActivity.this, RegionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_left,R.anim.out_right);
            }else {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_left,R.anim.out_right);
            }
            finish();
        }, 2400);
    }
}