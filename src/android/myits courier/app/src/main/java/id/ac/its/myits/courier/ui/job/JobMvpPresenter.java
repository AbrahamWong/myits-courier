package id.ac.its.myits.courier.ui.job;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobMvpPresenter<V extends JobMvpView & MvpView> extends MvpPresenter<V> {
    void requestPermissions(String... permissions);
    void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    GeoPoint getLocation();
    void getEksternalPaket(int id);
    void getInternalPaket(String kodePaket);
}
