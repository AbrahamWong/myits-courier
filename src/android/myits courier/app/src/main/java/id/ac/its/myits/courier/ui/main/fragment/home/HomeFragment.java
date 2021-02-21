package id.ac.its.myits.courier.ui.main.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.di.component.ActivityComponent;
import id.ac.its.myits.courier.ui.base.BaseFragment;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpView;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.Statics;

public class HomeFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.homeNameText)
    TextView name;

    @BindView(R.id.zoneText)
    TextView zone;

    @BindView(R.id.srl_home)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert container != null;
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, v));
            mPresenter.onAttach(this);
        }

        setUp(v);

        // Jika seandainya sudah pernah request nama pengguna dan zona, tidak perlu menunggu
        // request daftar unit selesai untuk set text
        if (Statics.username != null) {
            name.setText(Statics.username);
            zone.setText(Statics.userZone);
        }

//        TextView usernameText = v.findViewById(R.id.homeNameText);
//        usernameText.setText(listener.getUserName() != null ? listener.getUserName() : v.getResources().getString(R.string.user_name));
        return v;
    }

    @Override
    protected void setUp(View view) {
        mPresenter.getUserInfo();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getUserInfo();
            swipeRefreshLayout.setRefreshing(false);
        });
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
        showRecycler(getView(), unitList);
    }

    @Override
    public void showAllHistory(ArrayList<DetilPekerjaan> jobList, int totalJobs) {

    }

    @Override
    public void showTodayHistory(ArrayList<DetilPekerjaan> jobList, int totalJobs) {

    }

    void showRecycler(View v, ArrayList<Unit> unitList) {
        RecyclerView homeListView = v.findViewById(R.id.homeListview);
        ArrayList<String> unitName = new ArrayList<>();
        ArrayList<Integer> numOfExternalJobs = new ArrayList<>();
        ArrayList<Integer> numOfInternalInJobs = new ArrayList<>();
        ArrayList<Integer> numOfInternalOutJobs = new ArrayList<>();
        ArrayList<Integer> unitId = new ArrayList<>();

        for (Unit unit : unitList) {
            unitName.add(unit.getNamaUnit());
            numOfExternalJobs.add(unit.getJumlahPaketEksternal());
            numOfInternalInJobs.add(unit.getJumlahPaketInternalMasuk());
            numOfInternalOutJobs.add(unit.getJumlahPaketInternalKeluar());
            unitId.add(unit.get_id());
        }

        ListViewAdapter adapter = new ListViewAdapter(unitName,
                numOfExternalJobs,
                numOfInternalInJobs,
                numOfInternalOutJobs,
                unitId);

        homeListView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        homeListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        hideLoading();
    }

    public void changeNameDetail(String username, String zone) {
        name.setText(username);
        this.zone.setText(zone);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        AppLogger.d("onDestroy is called");
        super.onDestroy();
    }
}
