package afisha.arzan.tm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import afisha.arzan.tm.R;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context context;
    private List<Uri> mListPhoto;
    private List<String> oldImages;

    public  static int STRING=0;
    public  static int PATH=1;
    private int type = PATH;

    public ImagesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Uri> list) {
        this.mListPhoto = list;
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOldImages(List<String> oldImages) {
        this.oldImages = oldImages;
        notifyDataSetChanged();

    }

    @NonNull
    @NotNull
    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sel_image,parent,false);
        Log.e("TAG", "onCreateViewHolder: "+type);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImagesAdapter.ViewHolder holder, int position) {
        Log.e("TAG", "onBindViewHolder: "+type);
        if (type==PATH){
        Uri uri = mListPhoto.get(position);
        if (uri ==null){
            return;
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
            if (bitmap!=null){
                holder.imgPhoto.setImageBitmap(bitmap);
                Log.e("TAG", "onBindViewHolder: "+bitmap);
            }
        } catch (IOException e) {
            holder.imgPhoto.setImageResource(R.drawable.def_image);

            e.printStackTrace();
        }
        }else {
            String imageurl  = oldImages.get(position);
            Log.e("TAG", "onBindViewHolder: "+imageurl);
            Picasso.get().load(imageurl)
                    .error(R.drawable.def_image)
                    .into(holder.imgPhoto);
        }

    }

    @Override
    public int getItemCount() {
        if (mListPhoto !=null){
            return mListPhoto.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.image);
        }
    }
}
