package afisha.arzan.tm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yandex.metrica.impl.ob.G;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.LoginActivity;
import afisha.arzan.tm.activity.ProfileTravelActivity;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.User;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfficialAdapter extends RecyclerView.Adapter<OfficialAdapter.ViewHolder> {

    Context context;
    List<User> officials;
    public static int STANDARD = 0;
    public static int LARGE = 1;
    private int type =STANDARD;
    private boolean isFollow,hasLoggedIn;
    private String token;

    public OfficialAdapter(Context context) {
        this.context = context;
    }

    public void setOfficials(List<User> officials) {
        this.officials = officials;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == STANDARD) return R.layout.item_official;
        return R.layout.item_official_large;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType,parent,false);
        token = SPManager.getInstance(context).getData(SPManager.TOKEN,"");
        hasLoggedIn = SPManager.getInstance(context).getBoolean(SPManager.AUTH,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OfficialAdapter.ViewHolder holder, int position) {
        User official = officials.get(position);
        Picasso.get().load(official.getUserImage())
                .error(R.drawable.user)
                .into(holder.userimage);
        holder.username.setText(official.getUsername());
        holder.about.setText(official.getAbout());
        if (Global.followings!=null) {
            isFollow = Global.followings.contains(official.getId());
            holder.follow.setText(isFollow ? R.string.unfollow : R.string.follow);
            Log.e("TAG", "isFollow: " + isFollow);
            holder.follow_btn.setBackgroundResource(isFollow ? R.drawable.unfollow_bg : R.drawable.follow_bg);
        }
            holder.follow_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hasLoggedIn) {
                        isFollow = Global.followings.contains(official.getId());
                        if (isFollow) {
                            isFollow = false;
                            holder.follow_btn.setBackgroundResource(R.drawable.follow_bg);
                            holder.follow.setText(R.string.follow);
                            setUnFollow(official.getId());
                        } else {
                            isFollow = true;
                            setFollow(official.getId());
                            holder.follow.setText(R.string.unfollow);
                            holder.follow_btn.setBackgroundResource(R.drawable.unfollow_bg);
                        }
                    } else {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (official.getId()!=SPManager.getInstance(context).getIntData(SPManager.MY_ID,0)){
                        SPManager.getInstance(context).setIntData(SPManager.ID,official.getId());
                        context.startActivity(new Intent(context, ProfileTravelActivity.class));
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return officials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userimage;
        TextView username,about,follow;
        RelativeLayout follow_btn;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            userimage = itemView.findViewById(R.id.userimage);
            username = itemView.findViewById(R.id.username);
            about = itemView.findViewById(R.id.about);
            follow = itemView.findViewById(R.id.follow);
            follow_btn = itemView.findViewById(R.id.follow_btn);
        }
    }
    private void setUnFollow(int id) {
        Call<StandardResponse> call = ApiClient.getInstance().getApi().UnFollow(token, id);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    Global.followings.remove(new Integer(id));
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
