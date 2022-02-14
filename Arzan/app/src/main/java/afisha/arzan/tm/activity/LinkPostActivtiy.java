package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONException;

import java.util.regex.Pattern;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.BannerAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.request.UserActionRequest;
import afisha.arzan.tm.response.MainOnePost;
import afisha.arzan.tm.response.MainPost;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinkPostActivtiy extends AppCompatActivity {
    private TextView title,desc,likeCount,shareCount,viewCount,createdAt;
    private ViewPager viewPager;
    private ImageView like, share,favorite;
    private WormDotsIndicator dotsIndicator;
    private boolean isFavorite, isLike;
    private boolean hasLoggedIn;
    private String token;
    private Dialog dialog;
    private Context context;
    private UserActionRequest userActionRequest = new UserActionRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LinkPostActivtiy.this;
        dialog = new Dialog(this);
        SPManager.loading(dialog);
        getPost();
        setContentView(R.layout.activity_link_post_activtiy);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.description);
        likeCount = findViewById(R.id.like_count);
        shareCount = findViewById(R.id.share_count);
        viewCount = findViewById(R.id.view_count);
        createdAt =findViewById(R.id.createdAt);
        viewPager = findViewById(R.id.viewPager);
        favorite = findViewById(R.id.fav);
        like =findViewById(R.id.like);
        share = findViewById(R.id.share);
        dotsIndicator =findViewById(R.id.dot);
        RemoteMessage remoteMessage = getIntent().getParcelableExtra("newNotify");
        Log.e("TAG", "getPost: "+remoteMessage );
    }

    private void getPost(){
        Call<MainOnePost> call = ApiClient.getInstance().getApi().getOnePost(101);
        call.enqueue(new Callback<MainOnePost>() {
            @Override
            public void onResponse(Call<MainOnePost> call, Response<MainOnePost> response) {
                if (response.isSuccessful()){
                    Log.e("TAG", "one Post: "+response.body().getPosts());
                    setData(response.body().getPosts());
                    dialog.dismiss();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<MainOnePost> call, Throwable t) {
                dialog.dismiss();
                Log.e("TAG", "one Post: "+t.getLocalizedMessage());


            }
        });
    }
    private void setData(Post post){
        String patternString ="(?:(?:http|https):\\/\\/)?(?:www.)?(?:instagram.com|instagr.am)\\/([A-Za-z0-9-_]+)";
        Pattern pattern = Pattern.compile(patternString);
        String scheme = "http://instagram.com/";
        title.setText(Html.fromHtml(post.getTitle()));
        desc.setText(Html.fromHtml(post.getContent()));
        likeCount.setText(Html.fromHtml(String.valueOf(post.getLikeCount())));
        shareCount.setText(Html.fromHtml(String.valueOf(post.getShareCount())));
        viewCount.setText(Html.fromHtml(String.valueOf(post.getViewCount())));
        createdAt.setText(post.getDateObject());
        desc.setLinksClickable(true);
        Linkify.addLinks(desc,pattern,scheme);

        isLike = post.getIsLike() ==1;
        like.setImageResource(isLike?R.drawable.ic_like_check_red:R.drawable.ic_like);

        isFavorite = post.getIsFavorite() == 1;
        favorite.setImageResource(isFavorite?R.drawable.ic__fav_check:R.drawable.ic_favorite);
        BannerAdapter bannerAdapter = new BannerAdapter(LinkPostActivtiy.this);
        bannerAdapter.setType(BannerAdapter.IMAGE);
        try {
            bannerAdapter.setImages(post.getImages());
            viewPager.setAdapter(bannerAdapter);
            dotsIndicator.setViewPager(viewPager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setView(viewCount,post);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasLoggedIn) setLike(likeCount,post,like);
                else startActivity(new Intent(context,RegisterActivity.class));
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(post);
                setShareCount(shareCount,post);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasLoggedIn){
                    if (isFavorite){
                        isFavorite = false;
                        setUnFavPost(favorite,post);
                    }
                    else {
                        isFavorite = true;
                        setFavPost(favorite,post);
                    }
                }else startActivity(new Intent(context, RegisterActivity.class));

            }
        });
    }
    private void setView(TextView count, Post n) {
        userActionRequest.setType("post");
        userActionRequest.setAction("view");
        userActionRequest.setRelId(n.getId());
        userActionRequest.setCount(1);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postCount(token, userActionRequest);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    n.setViewCount(1);
                    count.setText(String.valueOf(n.getViewCount() + 1));
                    Log.e("Tag", "view ugradyldy " + response.body());
                } else
                    Log.e("Tag", "view error " + response.body());
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("Tag", "view ugradylmady " + t.getLocalizedMessage());

            }
        });
    }
    //set like
    private void setLike(TextView count, Post n,ImageView like) {
        userActionRequest.setType("post");
        userActionRequest.setAction("like");
        userActionRequest.setRelId(n.getId());
        userActionRequest.setCount(1);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postCount(token, userActionRequest);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    n.setIsLike(1);
                    n.setLikeCount(1);
                    count.setText(String.valueOf(n.getLikeCount()));
                    like.setImageResource(R.drawable.ic_like_check_red);
                    Log.e("Tag", "like ugradyldy " + response.body());
                } else
                    Log.e("Tag", "like error " + response.body());
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("Tag", "like ugradylmady " + t.getLocalizedMessage());
            }
        });
    }
    //share click
    private void share( Post post) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sharingIntent.setType("text/plain");
        String shareBody = SPManager.POST_LINK+post.getId();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, post.getTitle());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.app_name)));
    }
    private void setShareCount(TextView count, Post n) {
        userActionRequest.setType("post");
        userActionRequest.setAction("share");
        userActionRequest.setRelId(n.getId());
        userActionRequest.setCount(1);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postCount(token, userActionRequest);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    n.setShareCount(1);
                    count.setText(String.valueOf(n.getShareCount() + 1));
                    Log.e("Tag", "view ugradyldy " + response.body());
                } else
                    Log.e("Tag", "view error " + response.body());
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("Tag", "view ugradylmady " + t.getLocalizedMessage());

            }
        });
    }
    //remove favorite post
    private void setUnFavPost(ImageView fav ,Post post) {
        userActionRequest.setType("post");
        userActionRequest.setAction("favorite");
        userActionRequest.setRelId(post.getId());
        userActionRequest.setCount(-1);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postCount(token, userActionRequest);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    post.setIsFavorite(0);
                    isFavorite = false;
                    fav.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(context, R.string.unfav_gos, Toast.LENGTH_SHORT).show();
                    Log.e("Tag", "favorite ugradyldy " + response.body());
                } else
                    Log.e("Tag", "favorite error " + response.body());

            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("Tag", "favorite ugradylmady " + t.getLocalizedMessage());

            }
        });
    }
    //add favorite post
    private void setFavPost(ImageView fav, Post post) {
        userActionRequest.setType("post");
        userActionRequest.setAction("favorite");
        userActionRequest.setRelId(post.getId());
        userActionRequest.setCount(1);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postCount(token, userActionRequest);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    post.setIsFavorite(1);
                    fav.setImageResource(R.drawable.ic__fav_check);
                    Toast.makeText(context,R.string.fav_gos, Toast.LENGTH_SHORT).show();
                    Log.e("Tag", "favorite ugradyldy " + response.body());
                } else
                    Log.e("Tag", "favorite error " + response.body());

            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("Tag", "favorite ugradylmady " + t.getLocalizedMessage());

            }
        });
    }
}