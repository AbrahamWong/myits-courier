package id.ac.its.myits.courier.ui.main;

import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface MainMvpPresenter <V extends MainMvpView & MvpView> extends MvpPresenter<V> {
    // ArrayList<Unit> getUnit();

    // void plugRecycler(RecyclerView v);

    void onLoggingOut();

    void test();

    UserInfo getUserInfo();

    void getUnits(String username);

    void getAllHistory();

    void getTodayHistory();
}
