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

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.di.component.ActivityComponent;
import id.ac.its.myits.courier.ui.base.BaseFragment;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpView;

public class HomeFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.homeNameText)
    TextView name;

    @BindView(R.id.zoneText)
    TextView zone;

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

//        TextView usernameText = v.findViewById(R.id.homeNameText);
//        usernameText.setText(listener.getUserName() != null ? listener.getUserName() : v.getResources().getString(R.string.user_name));
        return v;
    }

    @Override
    protected void setUp(View view) {
        mPresenter.getUserInfo();
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
        // Change this to data from API
        ArrayList<String> unitName = new ArrayList<>();
        ArrayList<Integer> numOfJobs = new ArrayList<>();
        ArrayList<Integer> unitId = new ArrayList<>();

        for (Unit unit : unitList) {
            unitName.add(unit.getNamaUnit());
            numOfJobs.add(unit.getJumlahPaket());
            unitId.add(unit.get_id());
        }

        showRecycler(getView(), unitName, numOfJobs, unitId);
    }

    void showRecycler(View view, ArrayList<String> unitName, ArrayList<Integer> numOfJobs, ArrayList<Integer> unitId) {
        RecyclerView homeListView = view.findViewById(R.id.homeListview);
        homeListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        homeListView.setAdapter(new ListViewAdapter(unitName, numOfJobs, unitId));

        hideLoading();
    }

    public void changeNameDetail(String username, String zone) {
        name.setText(username);
        this.zone.setText(zone);
    }
}
