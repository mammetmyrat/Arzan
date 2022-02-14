package afisha.arzan.tm.app;

import android.app.Activity;

import androidx.viewpager.widget.ViewPager;


import java.util.TimerTask;

import afisha.arzan.tm.adapter.BannerAdapter;

public class SliderTask extends TimerTask {
    private BannerAdapter adapter;
    private Activity activity;
    private ViewPager bannerPager;

    public SliderTask(BannerAdapter adapter, Activity activity, ViewPager bannerPager) {
        this.adapter = adapter;
        this.activity = activity;
        this.bannerPager = bannerPager;
    }

    @Override
    public void run() {
        if (activity != null){
            activity.runOnUiThread(() -> {
                if (bannerPager.getCurrentItem() < adapter.getCount() - 1) {
                    bannerPager.setCurrentItem(bannerPager.getCurrentItem() + 1, true);
                } else {
                    bannerPager.setCurrentItem(0, true);
                }
            });
        }
    }
}
