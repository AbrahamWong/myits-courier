package id.ac.its.myits.courier.ui.main.fragment.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.base.BaseFragment;

public class HistoryFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUp(getView());
    }

    @Override
    protected void setUp(View view) {
        ViewPager pager = view.findViewById(R.id.viewPager);
        pager.setAdapter(new HistoryViewPager(getChildFragmentManager(), 2));

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srl_history);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            pager.setAdapter(new HistoryViewPager(getChildFragmentManager(), 2));
            tabLayout.setupWithViewPager(pager);
            swipeRefreshLayout.setRefreshing(false);
            hideLoading();
        });
    }
}
