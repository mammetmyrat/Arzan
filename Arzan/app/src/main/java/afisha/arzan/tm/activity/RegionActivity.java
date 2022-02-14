package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.RegionAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Region;
import afisha.arzan.tm.response.MainRegion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Activity context;
    private List<Region> regions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = RegionActivity.this;
        setContentView(R.layout.activity_region);
        regions = SPManager.getRegions();
        Global.regions = SPManager.getRegions();
        recyclerView = findViewById(R.id.rv_regions);
        RegionAdapter adapter = new RegionAdapter(context,regions);
        adapter.setType(RegionAdapter.STANDARD);
        recyclerView.setAdapter(adapter);
        Global.regions = regions;

    }
}