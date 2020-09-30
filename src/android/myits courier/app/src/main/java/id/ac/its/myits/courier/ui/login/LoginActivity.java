package id.ac.its.myits.courier.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.BuildConfig;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.main.MainActivity;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.AuthStateManager;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.button_login)
    Button buttonLogin;

    private final String AUTH_ENDPOINT = BuildConfig.DEV_MYITS_URL + "/authorize";
    private final String TOKEN_ENDPOINT = BuildConfig.DEV_MYITS_URL + "/token";
    private final Uri REDIRECT_URI = Uri.parse(BuildConfig.REDIRECT_URI);
    private final String CLIENT_ID = BuildConfig.CLIENT_ID;
    public static final String LOG_TAG = "AppAuthSample";

    private int RC_AUTH = 100;
    AuthorizationService mAuthService;
    AuthStateManager mStateManager;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(LoginActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);

        mAuthService = new AuthorizationService(this);
        mStateManager= AuthStateManager.getInstance(this);

        setUp();
    }

    @Override
    protected void setUp() {
        mPresenter.onUserCheck();
    }

    @OnClick(R.id.button_login) void onLoginClick(View v){
        if(isNetworkConnected()){
            doAuthorization();
        }
        else {
            showNoInternetConnectionMessage(null);
        }
    }

    private void doAuthorization() {
        AuthorizationServiceConfiguration serviceConfig =
                new AuthorizationServiceConfiguration(
                        Uri.parse(AUTH_ENDPOINT), // authorization endpoint
                        Uri.parse(TOKEN_ENDPOINT)// token endpoint
                );
        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        serviceConfig, // the authorization service configuration
                        CLIENT_ID, // the client ID, typically pre-registered and static
                        ResponseTypeValues.CODE, // the response_type value: we want a code
                        REDIRECT_URI); // the redirect URI to which the auth response is sent

        AuthorizationRequest authRequest = authRequestBuilder
                .setScope("profile openid")
                .setPrompt("login")
                .build();

        Intent authIntent = mAuthService.getAuthorizationRequestIntent(authRequest);
        startActivityForResult(authIntent, RC_AUTH);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_AUTH ) {
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);

            if (resp != null) {
                mAuthService = new AuthorizationService(this);
                mStateManager.updateAfterAuthorization(resp,ex);

                mAuthService.performTokenRequest(
                        resp.createTokenExchangeRequest(),
                        new AuthorizationService.TokenResponseCallback() {
                            @Override public void onTokenRequestCompleted(
                                    TokenResponse resp, AuthorizationException ex) {
                                if (resp != null) {
                                    // exchange succeeded
                                    mStateManager.updateAfterTokenResponse(resp,ex);
                                    AppLogger.d(LOG_TAG + " access token " + resp.accessToken);
                                    mPresenter.onPersistAccessToken(resp.accessToken);
                                    mPresenter.onLoginSuccesful();


                                } else {
                                    // authorization failed, check ex for more details

                                }
                            }
                        });

            } else {
                // authorization failed, check ex for more details
            }

        } else {
            // ...
        }

        if (mStateManager.getCurrent().isAuthorized()){

            AppLogger.d(LOG_TAG + "Done");
            mStateManager.getCurrent().performActionWithFreshTokens(mAuthService, new AuthState.AuthStateAction() {
                @Override
                public void execute(@Nullable String accessToken, @Nullable String idToken, @Nullable AuthorizationException ex) {
                    if (accessToken!=null){
                        AppLogger.d(LOG_TAG + "IS AUTHORIZED " + accessToken);
                        mPresenter.onPersistAccessToken(accessToken);
                        mPresenter.onLoginSuccesful();
                    }
                }
            });
        }
    }

    @Override
    public void openMainActivity() {
        hideLoading();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void checkAuthorized() {
        if (mStateManager.getCurrent().isAuthorized()){
            AppLogger.d(LOG_TAG + "Done");
            mStateManager.getCurrent().performActionWithFreshTokens(mAuthService, new AuthState.AuthStateAction() {
                @Override
                public void execute(@Nullable String accessToken, @Nullable String idToken, @Nullable AuthorizationException ex) {
                    if (accessToken!=null){
                        AppLogger.d(LOG_TAG + "IS AUTHORIZED " + accessToken);
                        mPresenter.onPersistAccessToken(accessToken);
                        mPresenter.onLoginSuccesful();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Sesi berakhir, harap melakukan login lagi", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        mAuthService.dispose();
        mAuthService = null;
        super.onDestroy();
    }
}