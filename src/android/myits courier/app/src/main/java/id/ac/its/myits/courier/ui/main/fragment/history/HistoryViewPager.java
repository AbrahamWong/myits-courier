package id.ac.its.myits.courier.ui.main.fragment.history;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryViewPager extends FragmentStatePagerAdapter {

    int fragmentCount;
    private ArrayList<String> fragmentTitleList = new ArrayList<>(Arrays.asList(
            "Hari Ini",
            "Semua"
    ));

    public HistoryViewPager(@NonNull FragmentManager fm, int fragmentCount) {
        super(fm);
        this.fragmentCount = fragmentCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new AllDayFragment();
            default:
                return new TodayFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentCount;
    }
}
