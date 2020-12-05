package id.ac.its.myits.courier.ui.main;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void openLoginActivity();
    void openDetailActivity(Unit unit);
    void openQRActivity();
    void logOut();

    void makeCurrentFragment(Fragment fragment);

    void showUnitList(ArrayList<Unit> unitList);
}
