 package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.PostAdapter;
import afisha.arzan.tm.adapter.VipAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.main.HomeFragment;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.response.MainPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView back,viewType;
    private ArrayList<Post> posts = new ArrayList<>();
    private PostAdapter adapter;
    private NestedScrollView scrollView;
    private int limit=15,offset=0;
    private String token;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);

        recyclerView = findViewById(R.id.rv_vip);
        scrollView = findViewById(R.id.nested);
        back  = findViewById(R.id.back_btn);
        viewType = findViewById(R.id.view_type);
        progressBar = findViewById(R.id.progress);
        adapter = new PostAdapter(this);
        token = SPManager.getInstance(this).getData(SPManager.TOKEN,"");

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    offset = offset+limit;
                    progressBar.setVisibility(View.VISIBLE);
                    getPosts(limit,offset);
                }
            }
        });
        getPosts(limit,offset);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void getPosts(int limit,int offset){
        Call<MainPost> call = ApiClient.getInstance().getApi().getFavorites(token,1,"",limit,offset);
        call.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()&& response.body().getPosts()!=null){
                    posts.addAll(response.body().getPosts());
                    adapter.setType(adapter.getType());
                    adapter.setPosts(posts);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setSaveEnabled(true);
                    if (adapter.getType()==PostAdapter.SMALL){
                        recyclerView.setLayoutManager(new GridLayoutManager(RecommendedActivity.this,3));

                    }else {
                        recyclerView.setLayoutManager(new LinearLayoutManager(RecommendedActivity.this));
                    }
                    Log.e("TAG", "recommended: "+response.body().getPosts());
                    recyclerView.setAdapter(adapter);
                    changeViewType(adapter);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RecommendedActivity.this, R.string.network_error_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void changeViewType(PostAdapter adapter){
        viewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getType()==PostAdapter.STANDARD){
                    adapter.setType(PostAdapter.SMALL);
                    recyclerView.setLayoutManager(new GridLayoutManager(RecommendedActivity.this,3));
                    viewType.setImageResource(R.drawable.ic_view_big);
                }else if (adapter.getType()==PostAdapter.BIG){
                    adapter.setType(PostAdapter.STANDARD);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RecommendedActivity.this));
                    viewType.setImageResource(R.drawable.ic_view_small);
                }else {
                    adapter.setType(PostAdapter.BIG);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RecommendedActivity.this));
                    viewType.setImageResource(R.drawable.ic_view_standart);
                }
            }
        });
    }
}