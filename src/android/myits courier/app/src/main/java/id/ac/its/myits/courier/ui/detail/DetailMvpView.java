package id.ac.its.myits.courier.ui.detail;

import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface DetailMvpView extends MvpView {
    String retrieveFromIntent();
}
