package id.ac.its.myits.courier.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.adapter.MainAdapter;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.detail.DetailActivity;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.utils.AuthStateManager;


public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.rv_unit)
    RecyclerView rvUnit;

    AuthStateManager stateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();

        stateManager = AuthStateManager.getInstance(this);
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(MainActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MainActivity.this);
        mPresenter.plugRecycler(rvUnit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_qr:
                scanQR();
                break;
            case R.id.menu_logout:
                logOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openDetailActivity(Unit unit) {
        final Gson gson = new Gson();

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("KEY_UNIT", gson.toJson(unit));
        startActivity(detailIntent);
    }

    @Override
    public void scanQR() {

    }

    @Override
    public void logOut() {
        mPresenter.onLoggingOut(stateManager);

        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }
}