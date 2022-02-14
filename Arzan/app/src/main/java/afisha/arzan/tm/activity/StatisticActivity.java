package afisha.arzan.tm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import afisha.arzan.tm.R;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.TabStatistic;
import afisha.arzan.tm.response.Statistic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_dialog);
        TextView region = findViewById(R.id.region);
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageView back = findViewById(R.id.back_btn);
        TabStatistic tabStatistic = new TabStatistic(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabStatistic);
        tabLayout.setupWithViewPager(viewPager, true);
        region.setText(SPManager.getInstance(StatisticActivity.this).getData(SPManager.REGION,"Aşgabat"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Global.statRegion == null) getStatistic();
    }

    private void getStatistic(){
        int regionId = SPManager.getInstance(StatisticActivity.this).getIntData(SPManager.REGION_ID,6);
        Call<Statistic> call = ApiClient.getInstance().getApi().getStatistic();
        call.enqueue(new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {
                if (response.isSuccessful()&&response.body()!=null){
                   for (int i=0;i<response.body().getStatRegions().size(); i++){
                       if (regionId==response.body().getStatRegions().get(i).getId()){
                           Global.statRegion = response.body().getStatRegions().get(i);
                           Log.e("TAG", "stat Region: "+Global.statRegion.getName());
                       }
                   }
                }
            }

            @Override
            public void onFailure(Call<Statistic> call, Throwable t) {

            }
        });
    }

}
