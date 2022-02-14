package afisha.arzan.tm.fragment;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import afisha.arzan.tm.R;

public class TabStatistic extends FragmentPagerAdapter {
    private String titles[];
    private Fragment frags[];

    public TabStatistic(@NonNull @NotNull FragmentManager fm, Context context) {
        super(fm);
        Resources resources = context.getResources();
        titles = resources.getStringArray(R.array.statistic);
        frags  = new Fragment[titles.length];

        frags[0] = new DayFragment();
        frags[1] = new WeekFragment();
        frags[2] = new MonthFragment();

    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DayFragment tab1 = new DayFragment();
                return tab1;
            case 1:
                MonthFragment tab2 = new MonthFragment();
                return tab2;
            case 2:
                WeekFragment tab3 = new WeekFragment();
                return tab3;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return titles[0];
            case 1:
                return titles[1];
            case 2:
                return titles[2];

        }
        return null;
    }
}
