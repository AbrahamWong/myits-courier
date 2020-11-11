package id.ac.its.myits.courier.ui.qr;

import android.app.Activity;

import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface QrMvpView extends MvpView {
    Activity getQrActivity();
    void onQrInitiated();
}
