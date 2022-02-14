package afisha.arzan.tm.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Notification;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<Notification> notifications;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Notification n = notifications.get(position);
        holder.content.setText(Html.fromHtml(n.getContent()));
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Picasso.get().load(n.getImage()).error(R.drawable.image).placeholder(circularProgressDrawable).into(holder.image);
        holder.createdAt.setText(n.getCreatedAt());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.startActivity(new Intent(context,NotificationDetailActivity.class));
                SPManager.getInstance(context).setIntData(SPManager.POS,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView content,createdAt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            content = itemView.findViewById(R.id.content);
            createdAt = itemView.findViewById(R.id.date);
        }
    }
}
