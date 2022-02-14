package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.OfficialAdapter;
import afisha.arzan.tm.adapter.PostAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.model.User;
import afisha.arzan.tm.response.MainOfficial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class OfficalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView back,viewType;
    private List<User> officials = new ArrayList<>();
    private NestedScrollView scrollView;
    private int limit=15,offset=0;
    private String token;
    private TextView count;
    private OfficialAdapter officialAdapter;
    private ProgressBar progressBar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officals);
        context = OfficalsActivity.this;
        recyclerView = findViewById(R.id.rv_officials );
        scrollView = findViewById(R.id.nested);
        back  = findViewById(R.id.back_btn);
        viewType = findViewById(R.id.type_icon);
        progressBar = findViewById(R.id.progress);
        count = findViewById(R.id.count);
        token = SPManager.getInstance(this).getData(SPManager.TOKEN,"");
        officialAdapter = new OfficialAdapter(context);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    offset = offset+limit;
                    progressBar.setVisibility(View.VISIBLE);
                    getOfficials(token,limit,offset);
                }
            }
        });
        back.setOnClickListener(view -> onBackPressed());
        getOfficials(token,limit,offset);

    }
    private void getOfficials(String token ,int limit, int offset ) {
        Call<MainOfficial> call = ApiClient.getInstance().getApi().getOfficials(token,"no-users",limit,offset);
        call.enqueue(new Callback<MainOfficial>() {
            @Override
            public void onResponse(Call<MainOfficial> call, Response<MainOfficial> response) {
                if (response.isSuccessful()) {
                    officials = response.body().getOfficial();
                    officialAdapter.setOfficials(response.body().getOfficial());
                    officialAdapter.setType(officialAdapter.getType());
                    count.setText(String.valueOf(response.body().getCount()));
                    if (officials.size()!=0) {
                        progressBar.setVisibility(View.GONE);
                        if (officialAdapter.getType()==OfficialAdapter.STANDARD){
                            recyclerView.setLayoutManager(new GridLayoutManager(context,3));

                        }else {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        }
                        recyclerView.setAdapter(officialAdapter);
                        changeViewType(officialAdapter);

                    }else{
                        progressBar.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MainOfficial> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.v("Tag", "officials gelmedi " + t.getMessage());

            }
        });
    }
    private void changeViewType(OfficialAdapter adapter){
        viewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getType()==OfficialAdapter.LARGE){
                    adapter.setType(OfficialAdapter.STANDARD);
                    recyclerView.setLayoutManager(new GridLayoutManager(context,3));
                    viewType.setImageResource(R.drawable.ic_view_standart);
                }else{
                    adapter.setType(OfficialAdapter.LARGE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    viewType.setImageResource(R.drawable.ic_view_small);
                }
            }
        });
    }
}