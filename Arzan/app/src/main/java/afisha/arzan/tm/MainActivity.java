package afisha.arzan.tm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yandex.metrica.YandexMetrica;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import afisha.arzan.tm.activity.AddPostActivity;
import afisha.arzan.tm.activity.LoginActivity;
import afisha.arzan.tm.activity.RegisterActivity;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.FirebaseTopic;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.NetworkChangeListener;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.AboutFragment;
import afisha.arzan.tm.fragment.ContactUsFragment;
import afisha.arzan.tm.fragment.FirstFragment;
import afisha.arzan.tm.fragment.SettingFragment;
import afisha.arzan.tm.fragment.main.ProfileFragment;
import afisha.arzan.tm.fragment.main.HomeFragment;
import afisha.arzan.tm.fragment.main.SearchFragment;
import afisha.arzan.tm.response.Count;
import afisha.arzan.tm.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static afisha.arzan.tm.app.SPManager.AUTH;
import static afisha.arzan.tm.app.SPManager.METRICA_API_KEY;
import static afisha.arzan.tm.app.SPManager.MY_ID;
import static afisha.arzan.tm.app.SPManager.NOTIFY_COUNT;
import static afisha.arzan.tm.app.SPManager.OFFICIAL_COUNT;
import static afisha.arzan.tm.app.SPManager.REGION_ID;
import static afisha.arzan.tm.app.SPManager.STATUS;
import static afisha.arzan.tm.app.SPManager.TOKEN;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {


    private Context context;

    private Fragment selectedFragment  = null;
    private DrawerLayout drawer;
    private boolean isOpen = false;
    private int regionId ;
    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView bottomNavigationView;
    private AppUpdateManager appUpdateManager;
    private FloatingActionButton add_btn;
    private ExpansionLayout expansionLayout;
    private LinearLayout about,share,contact,rate,services,setting;
    private NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private static final int RV_APP_UPDATE = 100;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        regionId = SPManager.getInstance(context).getIntData(REGION_ID,6);
        FirebaseTopic.subscribeRegion(regionId);
        FirebaseTopic.subscribeAll();
        if (SPManager.getInstance(context).getBoolean(AUTH,false)){
            FirebaseTopic.subscribeStatus(SPManager.getInstance(context).getData(STATUS,""));
            FirebaseTopic.subscribeOwn(SPManager.getInstance(context).getIntData(MY_ID,0));
        }

        SPManager.getInstance(this).getData(SPManager.LANG,"rus");
        SPManager.getInstance(this).setBoolean(SPManager.isFirst,false);
        drawer = findViewById(R.id.drawer_layout);
        Global.regions = SPManager.getRegions();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        expansionLayout = findViewById(R.id.expanded_layout);
        about = findViewById(R.id.about_us);
        share = findViewById(R.id.share);
        contact = findViewById(R.id.contact);
        rate = findViewById(R.id.rate);
        add_btn = findViewById(R.id.fab);
        setting = findViewById(R.id.setting);
        services= findViewById(R.id.services);

        loadFragment(new HomeFragment());
        getFireToken();
        getNewToken();
        update();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPManager.getInstance(context).getBoolean(SPManager.AUTH,false)){
                    startActivity(new Intent(context, AddPostActivity.class));
                }else {
                    startActivity(new Intent(context, RegisterActivity.class));
                }
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                            .replace(R.id.frame,new AboutFragment())
                            .commit();
                    drawer.close();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                        .replace(R.id.frame,new SettingFragment())
                        .commit();
                drawer.close();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.share);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share)+ " " + "https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(shareIntent, "Täze Programma"));
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                        .replace(R.id.frame,new ContactUsFragment())
                        .commit();
                drawer.close();
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                }
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPManager.getInstance(context).getBoolean(AUTH,false)){
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                            .replace(R.id.frame,new FirstFragment())
                            .commit();
                    drawer.close();
                }else startActivity(new Intent(context,LoginActivity.class));
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
         
    }

    private void getFireToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }else {
                    String token = task.getResult();
                    SPManager.getInstance(context).setData(SPManager.FIRE_TOKEN,token);
                    Log.e(TAG, "fire token "+token);

                }
            }
        });
    }


    private void getNewToken(){
        String token = SPManager.getInstance(context).getData(SPManager.TOKEN,"");
        String fireToken = SPManager.getInstance(context).getData(SPManager.FIRE_TOKEN,"");
        Log.e(TAG, "fireToken: "+fireToken);
        Call<LoginResponse> call = ApiClient.getInstance().getApi().getNewToken(token,fireToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    SPManager.getInstance(context).setData(SPManager.TOKEN,"Bearer "+response.body().getToken());
                    Log.e(TAG, "new Token: "+response.body().getToken() );
                }else {
                    Log.e(TAG, "new token: "+response.code() );
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
    public boolean loadFragment(Fragment fragment)
    {
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                    .replace(R.id.frame,fragment)
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }

    public void setLanguage(Activity activity, String language){
        Locale locale = new Locale(language);
        Resources resources  =activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                {
                if (bottomNavigationView.getSelectedItemId()!=R.id.home){
                    selectedFragment = new HomeFragment();break;
                }else {
                    HomeFragment.scrollView.fullScroll(NestedScrollView.FOCUS_UP);break;
                }
            }

            case R.id.search:
            {
                if (bottomNavigationView.getSelectedItemId()!=R.id.search)
                selectedFragment = new SearchFragment();break;
            }
            case R.id.menu: {
                drawer.openDrawer(GravityCompat.START);break;
            }
            case R.id.profile:
            {
                if (bottomNavigationView.getSelectedItemId()!=R.id.profile)
                {
                    if (SPManager.getInstance(context).getBoolean(SPManager.AUTH,false))
                    {
                        selectedFragment = new ProfileFragment();break;
                    }else {
                        startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            }
        }
        return loadFragment(selectedFragment);
    }
    @Override
    public void onBackPressed()
    {
        if (bottomNavigationView.getSelectedItemId() == R.id.home)
        {
         if (doubleBackToExitPressedOnce){
             finishAffinity();
             return;
         }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else
        {
            bottomNavigationView.setSelectedItemId(R.id.home);
        }

    }
    private void update(){
         appUpdateManager = AppUpdateManagerFactory.create(context);
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE,MainActivity.this,RV_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull @NotNull InstallState installState) {
            if(installState.installStatus() == InstallStatus.DOWNLOADED){
                Toast.makeText(context, R.string.downloaded, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode==RV_APP_UPDATE){
            Toast.makeText(context, R.string.start_update, Toast.LENGTH_SHORT).show();
            if (requestCode!= RESULT_OK){
                Log.e(TAG, "Update Result failed: "+resultCode);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver( networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        YandexMetrica.resumeSession(this);
        YandexMetrica.getReporter(getApplicationContext(), METRICA_API_KEY).resumeSession();

    }

    @Override
    protected void onPause() {
        YandexMetrica.pauseSession(this);
        super.onPause();
    }

}