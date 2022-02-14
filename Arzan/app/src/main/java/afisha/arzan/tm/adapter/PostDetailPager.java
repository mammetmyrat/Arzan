package afisha.arzan.tm.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.RegisterActivity;
import afisha.arzan.tm.adapter.BannerAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.request.UserActionRequest;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailPager extends PagerAdapter {


    Activity activity;
    List<Post> posts;
    private String token;
    private UserActionRequest userActionRequest = new UserActionRequest();
    private int userId;
    private boolean isFavorite, isLike;
    private boolean hasLoggedIn;

    public PostDetailPager(Activity activity) {
        this.activity = activity;
        notifyDataSetChanged();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_post_pager, container, false);
        token = SPManager.getInstance(view.getContext()).getData(SPManager.TOKEN, "");
        hasLoggedIn = SPManager.getInstance(view.getContext()).getBoolean(SPManager.AUTH, false);
        Post post = posts.get(position);
        Log.e("TAG", "instantiateItem: "+post.getId());
        TextView title,desc,likeCount,shareCount,viewCount,createdAt;
        ViewPager viewPager;
        ImageView like, share,favorite;
        WormDotsIndicator dotsIndicator;
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.description);
        likeCount = view.findViewById(R.id.like_count);
        shareCount = view.findViewById(R.id.share_count);
        viewCount = view.findViewById(R.id.view_count);
        createdAt = view.findViewById(R.id.createdAt);
        viewPager = view.findViewById(R.id.viewPager);
        favorite = view.findViewById(R.id.fav);
        like = view.findViewById(R.id.like);
        share = view.findViewById(R.id.share);
        dotsIndicator = view.findViewById(R.id.dot);
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
        BannerAdapter bannerAdapter = new BannerAdapter(activity);
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
                if (hasLoggedIn) {
                    setLike(likeCount,post,like);
                }
                else activity.startActivity(new Intent(activity,RegisterActivity.class));
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
                }else activity.startActivity(new Intent(activity, RegisterActivity.class));

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view.equals(object);

    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }
    //set view
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
                    animLike(like);
                    count.setText(String.valueOf(n.getLikeCount()));
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
        activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.app_name)));
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
                    Toast.makeText(activity, R.string.unfav_gos, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(activity,R.string.fav_gos, Toast.LENGTH_SHORT).show();
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
    private void animLike(ImageView like){
        MediaPlayer mPlayer  = MediaPlayer.create(activity,R.raw.click);
        mPlayer.start();
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(like, "scaleX", 0.1f, 1f);
        bounceAnimX.setDuration(300);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(like, "scaleY", 0.1f, 1f);
        bounceAnimY.setDuration(300);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(like, "alpha", 1f, 0f);
        bgAlphaAnim.setDuration(200);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                like.setImageResource(R.drawable.ic_like_check_red);
            }
        });
        animatorSet.play(bounceAnimX).with(bounceAnimY);
        animatorSet.start();
    }
}
