package id.ac.its.myits.courier.di.component;

import dagger.Component;
import id.ac.its.myits.courier.di.PerActivity;
import id.ac.its.myits.courier.di.module.ActivityModule;
import id.ac.its.myits.courier.ui.historydetail.HistoryDetailActivity;
import id.ac.its.myits.courier.ui.job.JobActivity;
import id.ac.its.myits.courier.ui.joblist.JobListActivity;
import id.ac.its.myits.courier.ui.jobstatus.JobStatusActivity;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.ui.main.MainActivity;
import id.ac.its.myits.courier.ui.main.fragment.history.AllDayFragment;
import id.ac.its.myits.courier.ui.main.fragment.history.TodayFragment;
import id.ac.its.myits.courier.ui.main.fragment.home.HomeFragment;
import id.ac.its.myits.courier.ui.main.fragment.profile.ProfileFragment;
import id.ac.its.myits.courier.ui.qr.QrActivity;
import id.ac.its.myits.courier.ui.splash.SplashActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    // Inject splash screen activity
    void inject(SplashActivity splashActivity);

    // Inject login screen activity
    void inject(LoginActivity activity);

    // Inject main activity
    void inject(MainActivity activity);

    // Inject QR scanner activity
    void inject(QrActivity qrActivity);

    // Inject profile fragment
    void inject(ProfileFragment profileFragment);

    // Inject package history detail activity
    void inject(HistoryDetailActivity historyDetailActivity);

    // Inject job detail activity
    void inject(JobActivity jobActivity);

    // Inject job list activity
    void inject(JobListActivity jobListActivity);

    // Inject job status change activity
    void inject(JobStatusActivity jobStatusActivity);

    // Inject main fragments
    void inject(HomeFragment homeFragment);
    void inject(TodayFragment todayFragment);
    void inject(AllDayFragment allDayFragment);
}
