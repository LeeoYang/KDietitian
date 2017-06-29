package com.dobbby.kdietitian.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.login.presenter.LoginPresenter;
import com.dobbby.kdietitian.login.view.OnLoginFeedbackListener;
import com.dobbby.kdietitian.normal.UserMainActivity;
import com.dobbby.kdietitian.util.Debug;
import com.dobbby.kdietitian.util.WbToastFactory;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        LoginFragment.OnFragmentInteractionListener,
        LoginMethodFragment.OnFragmentInteractionListener,
        ThankForSupportFragment.OnFragmentInteractionListener,
        OnLoginFeedbackListener {

    private static final String ARG_REVEAL_THANKS = "revealThanks";

    private static WeakReference<LoginActivity> instance;

    private LoginPresenter presenter;
    private FragmentManager fragmentManager;
    private LoginMethodFragment loginMethodFragment;
    private LoginFragment loginFragment;
    private ImageButton backArrow;
    private View actionBarLine;
    private boolean onLoginByTelState;

    private boolean revealThanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Debug.attachDebugController(findViewById(R.id.main_background));

        instance = new WeakReference<>(this);
        revealThanks = getIntent().getBooleanExtra(ARG_REVEAL_THANKS, false);
        init();
    }

    public static void finishIfStarted() {
        if (instance != null && instance.get() != null) {
            instance.get().finish();
        }
    }

    public static void startMeAsThanksPage(Context context) {
        finishIfStarted();

        context.startActivity(
                new Intent(context, LoginActivity.class)
                        .putExtra(ARG_REVEAL_THANKS, true)
        );
    }

    private void init() {
        presenter = new LoginPresenter(this, getString(R.string.base_url));
        fragmentManager = getSupportFragmentManager();
        loginFragment = LoginFragment.newInstance();
        loginMethodFragment = LoginMethodFragment.newInstance();
        backArrow = (ImageButton) findViewById(R.id.back_arrow);
        actionBarLine = findViewById(R.id.action_bar_line);
        onLoginByTelState = false;

        assert backArrow != null;
        backArrow.setVisibility(View.INVISIBLE);
        backArrow.setOnClickListener(this);

        assert actionBarLine != null;
        actionBarLine.setBackgroundColor(getResources().getColor(R.color.actionBarLineColor));

        if (revealThanks) {
            addThanksPageFragment();
        } else {
            addInitLoginMethodFragment();
        }
    }

    private void addThanksPageFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.log_fragment_container, ThankForSupportFragment.newInstance())
                .commit();

        backArrow.setVisibility(View.INVISIBLE);
        actionBarLine.setBackgroundColor(getResources().getColor(R.color.actionBarLineColor));

        onLoginByTelState = false;
    }

    private void addInitLoginMethodFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.log_fragment_container, loginMethodFragment)
                .commit();

        backArrow.setVisibility(View.INVISIBLE);
        actionBarLine.setBackgroundColor(getResources().getColor(R.color.actionBarLineColor));

        onLoginByTelState = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                backToSelectMethodView();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (onLoginByTelState) {
            backToSelectMethodView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void performLoginByTel(String tel, String password) {
        presenter.performLoginByTel(tel, password);
    }

    @Override
    public void backToSelectMethodView() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.log_fragment_container, loginMethodFragment)
                .commit();

        backArrow.setVisibility(View.INVISIBLE);
        actionBarLine.setBackgroundColor(getResources().getColor(R.color.actionBarLineColor));

        onLoginByTelState = false;
    }

    @Override
    public void goToLoginByTelView() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.log_fragment_container, loginFragment)
                .commit();

        backArrow.setVisibility(View.VISIBLE);
        actionBarLine.setBackgroundColor(getResources().getColor(R.color.actionBarLineSelectedColor));

        onLoginByTelState = true;
    }

    @Override
    public void goToSignUpByTelView() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void continueToLogin() {
        goToLoginByTelView();
    }

    @Override
    public void onError(String error) {
        WbToastFactory.create(this, error).show();
    }

    @Override
    public void onAuthorized() {
        startActivity(new Intent(this, UserMainActivity.class));
        finish();
    }

    @Override
    public void onAuthorizationFailed() {
        loginFragment.showWrongInfoError();
    }
}
