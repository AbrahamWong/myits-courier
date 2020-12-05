package id.ac.its.myits.courier.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.history.fragment.HistoryFragment;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.ui.main.fragment.home.HomeFragment;
import id.ac.its.myits.courier.ui.main.fragment.profile.ProfileFragment;
import id.ac.its.myits.courier.ui.qr.QrActivity;


public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.fab)
    FloatingActionButton fabQR;

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

    public static String username = null;
    public static String userZone = null;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        makeCurrentFragment(homeFragment);
                        return true;
                    case R.id.history:
                        makeCurrentFragment(new HistoryFragment());
                        return true;
                    case R.id.profile:
                        makeCurrentFragment(new ProfileFragment());
                        return true;
                }
                return false;
            }
        });

        bottomNavigation.setBackground(null);
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(MainActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MainActivity.this);
        // mPresenter.plugRecycler(rvUnit);
        // mPresenter.test()

        makeCurrentFragment(new HomeFragment());
    }

    @Override
    public void openDetailActivity(Unit unit) {
    }

    @OnClick(R.id.fab) @Override
    public void openQRActivity() {
        Intent qrIntent = new Intent(this, QrActivity.class);
        startActivity(qrIntent);
    }

    @Override
    public void logOut() {
        // mPresenter.onLoggingOut(stateManager);

        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void makeCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_layout, fragment)
                .commit();
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(getApplicationContext());
        startActivity(intent);
    }

    @Override
    public void showUnitList(ArrayList<Unit> unitList) {

    }
}