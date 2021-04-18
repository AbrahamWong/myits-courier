package id.ac.its.myits.courier.ui.jobstatus;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobStatusMvpPresenter<V extends JobStatusMvpView & MvpView> extends MvpPresenter<V> {
    void getExternalStatuses();

    void getInternalStatuses();

    void postExternalStatus(int idPaket, String status);

    void postInternalStatus(String kodePaket, String status);

    ArrayList<RadioButton> createRadioButtons(ArrayList<String> statuses, RadioGroup rg);
}
