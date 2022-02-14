package afisha.arzan.tm.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.ImageViewActivity;
import afisha.arzan.tm.activity.LinkPostActivtiy;
import afisha.arzan.tm.activity.PostDetailActivty;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Banner;

public class BannerAdapter extends PagerAdapter {

    Activity activity;
    List<Banner> banners;
    List<String> images;

    public static int BANNER = 0;
    public static int IMAGE = 1;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public BannerAdapter(Activity activity) {
        this.activity = activity;
        this.banners = banners;
    }


    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }


    public void setImages(List<String> images) {
        this.images = images;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view  = null;
        if (type==BANNER){
            view= LayoutInflater.from(activity).inflate(R.layout.item_banner,container,false);
        ImageView imageView = view.findViewById(R.id.image);
        Picasso.get()
                .load(banners.get(position).getImage())
                .error(R.drawable.image)
                .into(imageView);

            view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("TAG", "onClick: "+banners.get(position).linkId() );
                    if (banners.get(position).linkType()==null) return;
                    String linkType =banners.get(position).linkType();
                    switch (linkType){
                        case "post":openPost(banners.get(position).linkId());
                        break;
                        case "link":openLink(banners.get(position).linkId());
                            break;


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        }
        if (type==IMAGE) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_image,container,false);
            ImageView imageView = view.findViewById(R.id.image);

            Picasso.get()
                    .load(images.get(position))
                    .error(R.drawable.image)
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ImageViewActivity.class);
                    Global.images1  = images;
                    SPManager.getInstance(activity).setIntData(SPManager.POS,position);
                    activity.startActivity(intent);
                }
            });
        }
        container.addView(view,0);
        return view;
    }
    private void openPost(String id) {
        Intent intent = new Intent(activity, LinkPostActivtiy.class);
        intent.putExtra("postId",Integer.valueOf(id));
        activity.startActivity(intent);
    }

    private void openLink(String link) {
        Uri uri = Uri.parse(link);
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(uri);
        activity.startActivity(intent1);
    }

    @Override
    public int getCount() {
        if (type==BANNER){
            if (banners!=null) return banners.size();
            return 0;
        }else {
            if (images!=null) return images.size();
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }
}
