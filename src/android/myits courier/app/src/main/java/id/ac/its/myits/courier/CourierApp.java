package id.ac.its.myits.courier;

import android.app.Application;
import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.network.model.interceptor.RefreshTokenInterceptor;
import id.ac.its.myits.courier.di.component.ApplicationComponent;
import id.ac.its.myits.courier.di.component.DaggerApplicationComponent;
import id.ac.its.myits.courier.di.module.ApplicationModule;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.utils.AppLogger;
import okhttp3.OkHttpClient;

public class CourierApp extends Application {
    @Inject
    DataManager dataManager;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        applicationComponent.inject(this);

        AppLogger.init();

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new RefreshTokenInterceptor(dataManager,
                        new RefreshTokenInterceptor.RefreshTokenErrorListener() {
                            @Override
                            public void onError(int code) {

                                AppLogger.d("Error on refreshing token, opening login activity.. %d", code);

                                Intent intent = LoginActivity.getStartIntent(getApplicationContext());
                                startActivity(intent);
                            }
                        }))
                .build();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
