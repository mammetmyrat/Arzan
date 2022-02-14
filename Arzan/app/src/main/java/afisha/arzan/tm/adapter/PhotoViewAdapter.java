package afisha.arzan.tm.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import afisha.arzan.tm.R;


public class PhotoViewAdapter extends PagerAdapter {

    List<String> images;
    Context context;

    public PhotoViewAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view.equals(object);    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.photo_dialog,container,false);
        String image = images.get(position);
        ImageView imageView = view.findViewById(R.id.image);
        if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){

            Log.e("TAG", "land: " );
        }else {
            Log.e("TAG", "prot: " );
        }
        Picasso.get()
                .load(image)
                .error(R.drawable.image)
                .into(imageView);

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }
}
