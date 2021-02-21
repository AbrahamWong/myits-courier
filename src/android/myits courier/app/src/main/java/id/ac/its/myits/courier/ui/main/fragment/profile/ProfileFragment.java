package id.ac.its.myits.courier.ui.main.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.BaseFragment;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpView;
import id.ac.its.myits.courier.utils.Statics;

public class ProfileFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.profileName)
    TextView profileName;

    @BindView(R.id.profileLocation)
    TextView profileLocation;

    @BindView(R.id.logOutButton)
    Button logOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        setUp(v);
        return v;
    }

    @Override
    protected void setUp(View view) {
        getActivityComponent().inject(ProfileFragment.this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(ProfileFragment.this);

        if (Statics.username != null) {
            profileName.setText(Statics.username);
            profileLocation.setText("Zona " + Statics.userZone);
        }
    }

    @OnClick(R.id.logOutButton)
    public void logOut() {
        if (isNetworkConnected()) {
            mPresenter.onLoggingOut();
        } else {
            showNoInternetConnectionMessage(null);
        }

        if (getActivity() != null) {
            startActivity(LoginActivity.getStartIntent(getActivity()));
            getActivity().finish();
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(getView().getContext());
        startActivity(intent);
    }

    @Override
    public void openDetailActivity(Unit unit) {

    }

    @Override
    public void openQRActivity() {

    }

    @Override
    public void makeCurrentFragment(Fragment fragment) {

    }

    @Override
    public void showUnitList(ArrayList<Unit> unitList) {

    }

    @Override
    public void showAllHistory(ArrayList<DetilPekerjaan> jobList, int totalJobs) {

    }

    @Override
    public void showTodayHistory(ArrayList<DetilPekerjaan> jobList, int totalJobs) {

    }

    public void setProfile(String username, String zone) {
        profileName.setText(username);
        profileLocation.setText(String.format(Locale.ENGLISH, "Zona %s", zone));
    }
}
