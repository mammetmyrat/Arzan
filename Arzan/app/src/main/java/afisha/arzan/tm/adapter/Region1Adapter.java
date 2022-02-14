package afisha.arzan.tm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.multiselector.MultiSelector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.AddPostActivity;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.RegionFragment;
import afisha.arzan.tm.model.PaymentServices;
import afisha.arzan.tm.model.Region;
import afisha.arzan.tm.model.Servi;

public class Region1Adapter extends RecyclerView.Adapter<Region1Adapter.ViewHolder> {

    Activity context;
    List<Region> regions;
    PaymentServices paymentServices = Global.paymentServices;
    private List<Integer> pay = new ArrayList<>();
    private MultiSelector multiSelector = new MultiSelector();


    public Region1Adapter(Activity context, List<Region> regions) {
        this.context = context;
        this.regions = regions;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_region_dialog, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Region1Adapter.ViewHolder holder, int position) {
        Region region = regions.get(position);
        try {
            JSONArray jsonArray = paymentServices.getRegions();
            pay.add(jsonArray.getJSONObject(position).getInt("value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.textView.setText(region.getName());
        multiSelector.setSelected(holder.getAdapterPosition(), holder.getItemId(), false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (multiSelector.isSelected(holder.getAdapterPosition(), holder.getItemId())) {
                    holder.radio.setImageResource(R.drawable.i_radio);
                    multiSelector.setSelected(holder.getAdapterPosition(), holder.getItemId(), false);
                    Global.selectedRegions.remove(region.getName());
                    Global.money=Global.money-pay.get(position);

                    Log.e("TAG", "money: "+Global.money );
                    Log.e("TAG", "onClick: "+Global.selectedRegions );

                } else {
                    Global.money=Global.money+pay.get(position);
                    Log.e("TAG", "money: "+Global.money );
                    holder.radio.setImageResource(R.drawable.ic_radio_check);
                    multiSelector.setSelected(holder.getAdapterPosition(), holder.getItemId(), true);
                    Global.selectedRegions.add(region.getName());
                    Log.e("TAG", "onClick: "+Global.selectedRegions );
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (regions != null) return regions.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView radio;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.region);
            radio = itemView.findViewById(R.id.redio);

        }
    }
}
