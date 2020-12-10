package id.ac.its.myits.courier.ui.main.fragment.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.di.component.ActivityComponent;
import id.ac.its.myits.courier.ui.base.BaseFragment;
import id.ac.its.myits.courier.ui.main.MainActivity;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpView;

public class AllDayFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_allday, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, v));
            mPresenter.onAttach(this);
        }

        setUp(v);
        return v;
    }

    @Override
    protected void setUp(View v) {
        mPresenter.getUserInfo();

        RecyclerView allList = v.findViewById(R.id.all_day_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allList.setLayoutManager(layoutManager);

        TodayRecyclerViewAdapter adapter = new TodayRecyclerViewAdapter(new ArrayList<>(), 0);
        allList.setAdapter(adapter);

        mPresenter.getAllHistory(MainActivity.username != null ? MainActivity.username : "");
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showAllHistory(ArrayList<DetilPekerjaan> jobList, int totalJobs) {
        RecyclerView allList = getView().findViewById(R.id.all_day_list);
        AllDayRecyclerViewAdapter adapter = new AllDayRecyclerViewAdapter(jobList, totalJobs);
        allList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openLoginActivity() {

    }

    @Override
    public void openDetailActivity(Unit unit) {

    }

    @Override
    public void openQRActivity() {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void makeCurrentFragment(Fragment fragment) {

    }

    @Override
    public void showUnitList(ArrayList<Unit> unitList) {

    }

    @Override
    public void showTodayHistory(ArrayList<DetilPekerjaan> jobList, int totalJobs) {

    }
}
