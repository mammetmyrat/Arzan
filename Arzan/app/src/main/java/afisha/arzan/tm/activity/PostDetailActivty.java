package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.PostDetailPager;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.response.MainPost;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivty extends AppCompatActivity {
    private int afishaPos ;
    private ViewPager pagerAfisha;
    private PostDetailPager pager;
    private MaterialButton sendFeedback;
    private List<Post> posts = new ArrayList<>();
    private Dialog dialog,loading,done;
    private String token,message;
    private int regionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_activty);
        regionId = SPManager.getInstance(this).getIntData(SPManager.REGION_ID,6);
        token = SPManager.getInstance(this).getData(SPManager.TOKEN,"");
        dialog = new Dialog(this);
        loading =new Dialog(this);
        sendFeedback = findViewById(R.id.feedback);
        afishaPos = SPManager.getInstance(this).getIntData(SPManager.POS,0);
        pagerAfisha = findViewById(R.id.pager_afisha);
        posts = Global.posts;
        pager = new PostDetailPager(PostDetailActivty.this);
        pager.setPosts(posts);
        pagerAfisha.setAdapter(pager);
        pagerAfisha.setCurrentItem(afishaPos);
        pagerAfisha.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = pagerAfisha.getCurrentItem();
                if (currentItem==(pagerAfisha.getAdapter().getCount()-1)){
                    Log.e("TAG", "post: "+pagerAfisha.getAdapter().getCount());
                    getNewPosts(regionId,15,pagerAfisha.getAdapter().getCount(),pagerAfisha.getAdapter().getCount()-1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void getNewPosts(int regionId,int limit,int offset,int currentItem) {
        Call<MainPost> call = ApiClient.getInstance().getApi().getAllPosts("", regionId, 5, limit, offset);
        call.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()) {
                    posts.addAll(response.body().getPosts());
                    pager = new PostDetailPager(PostDetailActivty.this);
                    pager.setPosts(posts);
                    pagerAfisha.setAdapter(pager);
                    pagerAfisha.setCurrentItem(currentItem);
                }
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {

            }
        });
    }
    private void showDialog(){
        dialog.setContentView(R.layout.feedback_dialog);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(true);
        MaterialButton send = dialog.findViewById(R.id.send);
        EditText editText = dialog.findViewById(R.id.editText);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPManager.loading(dialog);
                message = editText.getText().toString();
                sendFeedback(message);
            }
        });


    }
    private  void sendFeedback(String message){
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postThanks(token,message,"complain");
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()){
                    done  = new Dialog(PostDetailActivty.this);
                    dialog.dismiss();
                    Toast.makeText(PostDetailActivty.this, R.string.sended, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    SPManager.doneDialog(PostDetailActivty.this,getResources().getString(R.string.complain_sended),done);
                    MaterialButton button = done.findViewById(R.id.ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            done.dismiss();
                        }
                    });
                    Log.e("TAG", "onResponse: " );
                }else {
                    Log.e("TAG", "onResponse: error "+response.code() );
                    dialog.dismiss();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("TAG", "onResponse: " );
                dialog.dismiss();
                loading.dismiss();
            }
        });
    }
}