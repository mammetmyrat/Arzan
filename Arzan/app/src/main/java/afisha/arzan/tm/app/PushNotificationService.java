package afisha.arzan.tm.app;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import afisha.arzan.tm.R;

public class PushNotificationService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TAG", "fire token: " + s);
    }

    private Bitmap largeIcon;

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message data payload: " + remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Uri imageUrl = remoteMessage.getNotification().getImageUrl();
        Integer priority = remoteMessage.getNotification().getNotificationPriority();
        String data = remoteMessage.getData().get("postId");
        String subTXT = remoteMessage.getData().get("title");
        @SuppressLint("RemoteViewLayout") RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.item_post_standart);
        Log.e("TAG", "onMessageReceived: "+data);
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        largeIcon = bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        });


        String click_action  = remoteMessage.getNotification().getClickAction();
        Intent intent = new Intent(click_action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("newNotify",remoteMessage);
//        intent.putExtra("postId",Integer.valueOf(data));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setColor(Color.BLUE);
        builder.setSubText(subTXT);
        builder.setVisibility(NotificationCompat.VISIBILITY_SECRET);
        builder.setShowWhen(true);
        builder.setOngoing(true);
        builder.setCustomContentView(remoteViews);
        builder.setLargeIcon(largeIcon);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}
