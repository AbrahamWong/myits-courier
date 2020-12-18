package id.ac.its.myits.courier.ui.job;

import id.ac.its.myits.courier.data.db.model.PaketEksternal;
import id.ac.its.myits.courier.data.db.model.PaketInternal;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobMvpView extends MvpView {

    void onDataFetched(String id, String tipePaket);

    void setAllExternalText(PaketEksternal paket);

    void setAllInternalText(PaketInternal paket);

    void onUnitIdRetrieved(int idUnit);

    void goBack(String username, int idUnit);
}
