/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package id.ac.its.myits.courier.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import butterknife.Unbinder;
import id.ac.its.myits.courier.CourierApp;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.di.component.ActivityComponent;
import id.ac.its.myits.courier.di.component.DaggerActivityComponent;
import id.ac.its.myits.courier.di.module.ActivityModule;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.utils.CommonUtils;
import id.ac.its.myits.courier.utils.NetworkUtils;

/**
 * Created by janisharali on 27/01/17.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

    private ProgressDialog mProgressDialog;

    private TextView mProgressDialogText;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((CourierApp) getApplication()).getComponent())
                .build();

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(newBase);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading(String title, String message) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this, title, message);
    }

    @Override
    public void showLoading(String title, String message, boolean dimmedBackground) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this, title, message, dimmedBackground);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                //.findViewById(android.support.design.R.id.snackbar_text);
            .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showNoInternetConnectionMessage(@Nullable View.OnClickListener actionListener) {
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                getResources().getString(R.string.no_internet_connection),
                Snackbar.LENGTH_INDEFINITE);

        if (actionListener == null) {
            snackbar.setAction(getResources().getString(R.string.retry).toUpperCase(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
        } else {
            snackbar.setAction(getResources().getString(R.string.retry).toUpperCase(), actionListener);
        }

        snackbar.show();
    }

    @Override
    public void showErrorMessage(String errorMsg, @Nullable View.OnClickListener actionListener) {
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), errorMsg,
                Snackbar.LENGTH_INDEFINITE);

        if (actionListener == null) {
            snackbar.setAction(getResources().getString(R.string.retry).toUpperCase(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
        } else {
            snackbar.setAction(getResources().getString(R.string.retry).toUpperCase(), actionListener);
        }

        snackbar.show();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        /*startActivity(LoginActivity.getStartIntent(this));
        finish();*/
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        super.onDestroy();
    }

    protected abstract void setUp();
}
