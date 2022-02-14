package afisha.arzan.tm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.AddPostActivity;
import afisha.arzan.tm.app.FirebaseTopic;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Region;

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder> {

    Activity context;
    List<Region> regions;
    public static int STANDARD = 0;
    public static int SMALL = 1;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RegionAdapter(Activity context, List<Region> regions) {
        this.context = context;
        this.regions = regions;
    }

    @Override
    public int getItemViewType(int position) {
        if (type==STANDARD) return R.layout.item_region;
        else return R.layout.item_region_small;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RegionAdapter.ViewHolder holder, int position) {
        Region region = regions.get(position);
        holder.textView.setText(region.getName());
        if (type==STANDARD){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = SPManager.getInstance(context).getIntData(SPManager.REGION_ID,6);
                    SPManager.getInstance(context).setIntData(SPManager.LAST_REGION_ID,id);
                    SPManager.getInstance(context).setIntData(SPManager.REGION_ID,region.getId());
                    SPManager.getInstance(context).setData(SPManager.REGION,region.getName());
                    context.startActivity(new Intent(context, MainActivity.class));
                    context.overridePendingTransition(R.anim.in_left,R.anim.out_right);

                    FirebaseTopic.unSubscribeRegion(SPManager.getInstance(context).getIntData(SPManager.LAST_REGION_ID,0));
                }
            });
        }else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddPostActivity.regionText.setText(region.getName());
                    Global.region = region;
                    AddPostActivity.expansionLayout.toggle(false);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (regions!=null) return regions.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            textView  = itemView.findViewById(R.id.region_text);
        }
    }
}
