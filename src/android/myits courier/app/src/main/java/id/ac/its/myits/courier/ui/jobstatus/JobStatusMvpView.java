package id.ac.its.myits.courier.ui.jobstatus;

import android.widget.RadioButton;

import java.util.ArrayList;

import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobStatusMvpView extends MvpView {
    void onExternalListGet(ArrayList<String> status);

    void onInternalListGet(ArrayList<String> status);

    RadioButton onRadioButtonRequested();
}
