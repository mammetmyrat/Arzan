package afisha.arzan.tm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.PostDetailActivty;
import afisha.arzan.tm.activity.RegisterActivity;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.request.UserActionRequest;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Activity context;
    List<Post> posts;
    public static int STANDARD = 0;
    public static int BIG = 1;
    public static int SMALL= 2;
    private int type = STANDARD;
    private UserActionRequest userActionRequest = new UserActionRequest();
    private String token;
    private boolean isFav,hasLoggedIn;

    public PostAdapter(Activity context) {
        this.context = context;
        this.posts = posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();

    }

    public int getType() {
        return type;
    }

    @Override
    public int getItemViewType(int position) {
        if (type==STANDARD) return R.layout.item_post_standart;
        else if (type==BIG)return R.layout.item_post_big;
        else return R.layout.item_post_small;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        token = SPManager.getInstance(context).getData(SPManager.TOKEN,"");
        hasLoggedIn = SPManager.getInstance(context).getBoolean(SPManager.AUTH,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        if (post!=null){
            Picasso.get().load(post.getFirstImage()).error(R.drawable.image).placeholder(circularProgressDrawable).into(holder.image);
            holder.title.setText(Html.fromHtml(post.getTitle()));
            holder.desc.setText(Html.fromHtml(post.getContent()));
            if (type!=SMALL){
                holder.createdAt.setText(post.getCreatedAt());
            }else {
                holder.createdAt.setText(post.getCreatedAt());
            }
            isFav = post.getIsFavorite() == 1;
            holder.fav.setImageResource(isFav?R.drawable.ic__fav_check:R.drawable.ic_favorite);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Global.posts = posts;
                    Intent intent =new Intent(context, PostDetailActivty.class);
                    SPManager.getInstance(context).setIntData(SPManager.POS,position);
                    context.overridePendingTransition(android.R.anim.bounce_interpolator, android.R.anim.bounce_interpolator);
                    context.startActivity(intent);
                }
            });

            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hasLoggedIn){
                        if (isFav){
                            isFav = false;
                            setUnFavPost(holder.fav, post);
                        }
                        else {
                            isFav = true;
                            setFavPost(holder.fav,post);
                        }
                    }else {
                        context.startActivity(new Intent(context, RegisterActivity.class));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (posts.size()==0)return 0;
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,fav;
        TextView title,desc,createdAt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            createdAt = itemView.findViewById(R.id.createdAt);
            fav  = itemView.findViewById(R.id.favorite);
        }
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
                    fav.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(context, R.string.unfav_gos, Toast.LENGTH_SHORT).show();
                    Log.e("Tag", "favorite ugradyldy " + response.body());
                } else
                    Log.e("Tag", "favorite error " + response.code());

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
                    Log.e("Tag", "favorite error " + response.code());

            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("Tag", "favorite ugradylmady " + t.getLocalizedMessage());

            }
        });
    }
}
