package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.PostAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.model.User;
import afisha.arzan.tm.response.MainPost;
import afisha.arzan.tm.response.Profile;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileTravelActivity extends AppCompatActivity {

    private String token,bannerUrl = "dsadasd";
    private int myId;
    private Context context;
    private ImageView banner, userImage,back;
    private TextView region, followerCount, username, postCount, about, readMore, follow;
    private LinearLayout following_line;
    private RelativeLayout followBtn;
    private ArrayList<Post> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private boolean hasLoggedId, isFollow;
    private int folCount;
    private boolean isVisible = false;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_travel);
        context = ProfileTravelActivity.this;
        dialog = new Dialog(context);
        token = SPManager.getInstance(context).getData(SPManager.TOKEN, "");
        myId = SPManager.getInstance(context).getIntData(SPManager.ID, 0);
        hasLoggedId = SPManager.getInstance(context).getBoolean(SPManager.AUTH, false);
        banner = findViewById(R.id.banner);
        userImage = findViewById(R.id.userimage);
        region = findViewById(R.id.region);
        username = findViewById(R.id.username);
        followerCount = findViewById(R.id.follower_count);
        following_line = findViewById(R.id.following_line);
        postCount = findViewById(R.id.all_post_count);
        about = findViewById(R.id.about);
        readMore = findViewById(R.id.read_more);
        followBtn = findViewById(R.id.follow_btn);
        follow = findViewById(R.id.follow);
        recyclerView = findViewById(R.id.rv_all_posts);
        back = findViewById(R.id.back_btn);
        adapter = new PostAdapter(ProfileTravelActivity.this);

        if (Global.followings!=null){
            isFollow = Global.followings.contains(myId);
            followBtn.setBackgroundResource(isFollow ? R.drawable.unfollow_bg : R.drawable.follow_bg);
            follow.setText(isFollow ? R.string.unfollow : R.string.follow);
        }

        SPManager.loading(dialog);
        getProfile(token, myId);
        onClick();
    }

    private void onClick() {
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasLoggedId) {
                    if (isFollow){
                        isFollow = false;
                        followBtn.setBackgroundResource(R.drawable.follow_bg);
                        follow.setText(R.string.follow);
                        setUnFollow(myId);
                    }else {
                        isFollow = true;
                        setFollow(myId);
                        follow.setText(R.string.unfollow);
                        followBtn.setBackgroundResource(R.drawable.unfollow_bg);
                    }
                }else startActivity(new Intent(context,LoginActivity.class));
            }
        });
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVisible){
                    about.setMaxLines(3);
                    isVisible = false;
                }else {
                    about.setMaxLines(Integer.MAX_VALUE);
                    isVisible = true;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void getProfile(String token, int myId) {
        Call<Profile> call = ApiClient.getInstance().getApi().getProfile(token, myId);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful() && response.body().getUser() != null) {
                    setData(response.body().getUser());
                    dialog.dismiss();
                    if (response.body().getBanners().size()!=0){
                        bannerUrl = response.body().getBanners().get(0).getImage();
                        banner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> image = new ArrayList<>();
                                image.add(bannerUrl);
                                Global.images1 = image;
                                startActivity(new Intent(context, ImageViewActivity.class));
                            }
                        });
                    }
                    Log.e("TAG", "onResponse: "+bannerUrl );
                    Picasso.get().load(bannerUrl)
                            .error(R.drawable.image)
                            .into(banner);

                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ProfileTravelActivity.this, R.string.network_error_toast, Toast.LENGTH_SHORT).show();
            }
        });
        Call<MainPost> call1 = ApiClient.getInstance().getApi().getUserPosts(token, myId);
        call1.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()){
                    posts.addAll(response.body().getPosts());
                    adapter.setType(adapter.getType());
                    adapter.setPosts(posts);
                    recyclerView.setAdapter(adapter);
                    postCount.setText(String.valueOf(posts.size()));
                }
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
            }
        });
    }

    private void setData(User user) {
        folCount = user.getFollowersCount();
        username.setText(user.getUsername());
        Picasso.get().load(user.getUserImage()).error(R.drawable.user)
                .into(userImage);
        region.setText(SPManager.getInstance(context).getData(SPManager.REGION, "Aşgabat"));
        if (user.getStatus().equals("user")) following_line.setVisibility(View.GONE);
        Log.e("TAG", "followers: " + user.getFollowersCount());
        followerCount.setText(String.valueOf(folCount));
        about.setText(user.getAbout());
        about.post(new Runnable() {
            @Override
            public void run() {
              int lineCount =   about.getLineCount();
              if (lineCount>2) {
                  readMore.setVisibility(View.VISIBLE);
              } else readMore.setVisibility(View.GONE);
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> image = new ArrayList<>();
                image.add(user.getUserImage());
                Global.images1 = image;
                startActivity(new Intent(context, ImageViewActivity.class));
            }
        });


    }

    private void setUnFollow(int id) {
        Call<StandardResponse> call = ApiClient.getInstance().getApi().UnFollow(token, id);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    Global.followings.remove(new Integer(id));
                    folCount --;
                    followerCount.setText(String.valueOf(folCount));
                    Log.e("Tag", "followings size " + Global.followings.size());
                    Log.e("Tag ", "UnFollow " + response.body().getSuccess());
                    Log.e("Tag", "isFollowing " + id);
                } else Log.e("Tag ", "UnFollow error" + response.body());
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {

            }
        });
    }
    private void setFollow(int id) {
        Call<StandardResponse> call = ApiClient.getInstance().getApi().Follow(token, id);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    Global.followings.add(id);
                    folCount ++;
                    followerCount.setText(String.valueOf(folCount));
                    Log.e("Tag", "followings size " + Global.followings.size());
                    Log.e("Tag ", "Follow " + response.body().getSuccess());
                    Log.e("Tag", "isFollowing " + id);
                } else Log.e("Tag ", "Follow error" + response.body());
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {

            }
        });
    }
}