package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.NotificationAdapter;
import afisha.arzan.tm.adapter.OfficialAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Notification;
import afisha.arzan.tm.model.User;
import afisha.arzan.tm.response.MainNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActvity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView back,viewType;
    private List<Notification> notifications = new ArrayList<>();
    private NestedScrollView scrollView;
    private int limit=15,offset=0;
    private String token;
    private TextView count;
    private NotificationAdapter notificationAdapter;
    private ProgressBar progressBar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_actvity);
        recyclerView = findViewById(R.id.rv_notification);
        scrollView = findViewById(R.id.nested);
        back  = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progress);
        count = findViewById(R.id.count);
        token = SPManager.getInstance(this).getData(SPManager.TOKEN,"");

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    offset = offset+limit;
                    progressBar.setVisibility(View.VISIBLE);
                    getNotification(limit,offset);
                }
            }
        });

        back.setOnClickListener(v->onBackPressed());
        getNotification(limit,offset);

    }
    private void getNotification(int limit,int offset){
        Call<MainNotification> call = ApiClient.getInstance().getApi().getNotification(token,"",limit,offset);
        call.enqueue(new Callback<MainNotification>() {
            @Override
            public void onResponse(Call<MainNotification> call, Response<MainNotification> response) {
                if (response.isSuccessful()&& response.body().getNotifications()!=null){
                    notifications.addAll(response.body().getNotifications());
                    Global.notifications = notifications;
                    recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActvity.this));
                    notificationAdapter = new NotificationAdapter(NotificationActvity.this,notifications);
                    recyclerView.setAdapter(notificationAdapter);
                    count.setText(response.body().getCount());
                    progressBar.setVisibility(View.GONE);
                    Log.e("TAG", "notification: "+response.body().getNotifications());
                }else {
                    Log.e("TAG", "notification: "+response.code());

                }

            }

            @Override
            public void onFailure(Call<MainNotification> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("TAG", "notification: "+t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Global.NOTIFY_COUNT = 0;
        Global.OFFICIAL_COUNT = 0;

    }
}