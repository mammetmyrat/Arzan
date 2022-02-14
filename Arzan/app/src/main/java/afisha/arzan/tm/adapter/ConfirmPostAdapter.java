package afisha.arzan.tm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.EditPostActivity;
import afisha.arzan.tm.activity.PostDetailActivty;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.main.ProfileFragment;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.request.UserActionRequest;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPostAdapter extends RecyclerView.Adapter<ConfirmPostAdapter.ViewHolder> {

    Activity context;
    List<Post> posts;
    public static int STANDARD = 0;
    private UserActionRequest userActionRequest = new UserActionRequest();
    private String token;
    private boolean hasLoggedIn;

    public ConfirmPostAdapter(Activity context) {
        this.context = context;
        this.posts = posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_post,parent,false);
        token = SPManager.getInstance(context).getData(SPManager.TOKEN,"");
        hasLoggedIn = SPManager.getInstance(context).getBoolean(SPManager.AUTH,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ConfirmPostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        if (post!=null){
            Picasso.get().load(post.getFirstImage()).error(R.drawable.image).placeholder(circularProgressDrawable).into(holder.image);
            holder.title.setText(Html.fromHtml(post.getTitle()));
            holder.desc.setText(Html.fromHtml(post.getContent()));
            holder.createdAt.setText(post.getCreatedAt());
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
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMore(view,post);
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
        ImageView image,more;
        TextView title,desc,createdAt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            createdAt = itemView.findViewById(R.id.createdAt);
            more  = itemView.findViewById(R.id.more);
        }
    }
    //remove favorite post
   @SuppressLint("RestrictedApi")
   private void showMore(View view,Post post){
       final PopupMenu popupMenu = new PopupMenu(context,view);
       popupMenu.inflate(R.menu.more_menu);
       popupMenu.show();

       popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.delete:
                       deletePost(post); break;

               }
               return false;
           }
       });
       MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popupMenu.getMenu(), view);
       menuHelper.setForceShowIcon(true);
       menuHelper.show();
   }

   private void deletePost(Post post){
       Call<StandardResponse> call = ApiClient.getInstance().getApi().deletePost(token,post.getId());
       call.enqueue(new Callback<StandardResponse>() {
           @Override
           public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
               if (response.isSuccessful()){
                   posts.remove(post);
                   Toast.makeText(context, R.string.post_deleted, Toast.LENGTH_SHORT).show();
                   Log.e("TAG", "onResponse deleted: " );
               }
           }

           @Override
           public void onFailure(Call<StandardResponse> call, Throwable t) {
               Log.e("TAG", "onResponse deleted: "+t.getLocalizedMessage() );

           }
       });
   }
}
