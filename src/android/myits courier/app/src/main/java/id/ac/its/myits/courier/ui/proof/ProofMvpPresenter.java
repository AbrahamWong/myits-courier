package id.ac.its.myits.courier.ui.proof;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface ProofMvpPresenter<V extends ProofMvpView & MvpView> extends MvpPresenter<V> {
    void getProof(String kodePaket);

    void checkStoragePermission();

    String getCurrentPhotoPath();

    void convertImgToBase64();
}
