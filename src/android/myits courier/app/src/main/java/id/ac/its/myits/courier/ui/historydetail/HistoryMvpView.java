package id.ac.its.myits.courier.ui.historydetail;

import java.util.ArrayList;

import id.ac.its.myits.courier.data.db.model.DetilStatus;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface HistoryMvpView extends MvpView {
    void showHistoryDetails(DetilRiwayat historyDetail, ArrayList<DetilStatus> historyList);
}
