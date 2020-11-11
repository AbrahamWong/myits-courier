package id.ac.its.myits.courier.ui.main;

import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void openDetailActivity(Unit unit);
    void openQRActivity();
    void logOut();
}
