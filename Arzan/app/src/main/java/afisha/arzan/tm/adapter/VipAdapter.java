package afisha.arzan.tm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {

    Activity context;
    List<Post> posts;
    private UserActionRequest userActionRequest = new UserActionRequest();
    private String token;
    private boolean isFav,hasLoggedIn;

    public VipAdapter(Activity context) {
        this.context = context;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vip,parent,false);
        token = SPManager.getInstance(context).getData(SPManager.TOKEN,"");
        hasLoggedIn = SPManager.getInstance(context).getBoolean(SPManager.AUTH,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull VipAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        Global.posts = posts;
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        if (post!=null){
            Picasso.get().load(post.getFirstImage()).error(R.drawable.image).placeholder(circularProgressDrawable).into(holder.image);
            holder.title.setText(Html.fromHtml(post.getTitle()));
            isFav = post.getIsFavorite() == 1;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, PostDetailActivty.class);
                    SPManager.getInstance(context).setIntData(SPManager.POS,position);
                    context.overridePendingTransition(android.R.anim.bounce_interpolator, android.R.anim.bounce_interpolator);
                    context.startActivity(intent);
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
        ImageView image;
        TextView title;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
