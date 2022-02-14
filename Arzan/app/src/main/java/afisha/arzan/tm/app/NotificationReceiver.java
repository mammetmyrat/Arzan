package afisha.arzan.tm.app;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class NotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(intent.getIntExtra("postId",0));
        int id = intent.getIntExtra("postId",0);
        Log.e("TAG", "onReceive: "+id);
    }
}
