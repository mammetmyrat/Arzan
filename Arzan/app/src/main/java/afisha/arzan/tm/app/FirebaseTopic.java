package afisha.arzan.tm.app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class FirebaseTopic {
    public static void subscribeRegion(int id){
        String topic = "region_"+id;
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Void unused) {
                        Log.e("TAG", "subscribe: "+topic);
                    }
                });
    }
    public static void unSubscribeRegion(int id){
        String topic = "region_"+id;
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Void unused) {
                        Log.e("TAG", "unsubscribe: "+topic);
                    }
                });
    }

    public static void subscribeAll(){
        String topic = "all";
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Void unused) {
                        Log.e("TAG", "subscribe: "+topic);
                    }
                });
    }

    public static void subscribeStatus(String status){
        String topic =status;
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Void unused) {
                        Log.e("TAG", "subscribe: "+topic);
                    }
                });
    }
    public static void subscribeOwn(int id){
        String topic ="user_"+id;
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Void unused) {
                        Log.e("TAG", "subscribe: "+topic);
                    }
                });
    }
}
