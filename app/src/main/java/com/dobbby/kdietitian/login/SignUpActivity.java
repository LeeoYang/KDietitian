package com.dobbby.kdietitian.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.bean.User;
import com.dobbby.kdietitian.login.presenter.SignUpPresenter;
import com.dobbby.kdietitian.login.view.OnSignUpFeedbackListener;
import com.dobbby.kdietitian.util.WbToastFactory;
import com.dobbby.kdietitian.util.WbToolbarPlugin;

public class SignUpActivity extends AppCompatActivity implements
        SignUpQueryTelFragment.OnFragmentInteractionListener,
        SignUpQueryRoleFragment.OnFragmentInteractionListener,
        SignUpQueryPasswordFragment.OnFragmentInteractionListener,
        SignUpTermFragment.OnFragmentInteractionListener,
        OnSignUpFeedbackListener {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private SignUpQueryTelFragment queryTelFragment;
    private SignUpQueryRoleFragment queryRoleFragment;
    private SignUpQueryPasswordFragment queryPasswordFragment;
    private SignUpTermFragment termFragment;

    private static final int QUERY_TEL_STATE = 0;
    private static final int QUERY_ROLE_STATE = QUERY_TEL_STATE + 1;
    private static final int QUERY_PASSWORD_STATE = QUERY_ROLE_STATE + 1;
    private static final int VIEW_TERM_STATE = QUERY_PASSWORD_STATE + 1;

    private SignUpPresenter presenter;
    private boolean isDietitian = false;
    private int currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        WbToolbarPlugin.attach(this);

        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        queryTelFragment = SignUpQueryTelFragment.newInstance();
        queryRoleFragment = SignUpQueryRoleFragment.newInstance();
        queryPasswordFragment = SignUpQueryPasswordFragment.newInstance();
        termFragment = SignUpTermFragment.newInstance();

        presenter = new SignUpPresenter(this, getString(R.string.base_url));

        addInitQueryTelFragment();
    }

    private void addInitQueryTelFragment() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.sign_up));
        currentState = QUERY_TEL_STATE;

        fragmentManager
                .beginTransaction()
                .replace(R.id.sign_up_fragment_container, queryTelFragment)
                .commit();
    }

    @Override
    public void goToSelectRoleView() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.your_role));
        currentState = QUERY_ROLE_STATE;

        presenter.setUserTel(queryTelFragment.getTelNo());

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.sign_up_fragment_container, queryRoleFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void queryPin(String telNo) {
        presenter.checkIfTelUsed(telNo);
    }

    @Override
    public void goToQueryPasswordView(User.Role role) {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.sign_up));
        currentState = QUERY_PASSWORD_STATE;

        if (role == User.Role.dietitian) {
            isDietitian = true;
        }

        presenter.setUserRole(role);

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.sign_up_fragment_container, queryPasswordFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showTermOfUseDetails() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.term_of_use));
        currentState = VIEW_TERM_STATE;

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.sign_up_fragment_container, termFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void signUp() {
        presenter.checkNicknameUsed(queryPasswordFragment.getNickname());
    }

    private void backToQueryPasswordView(boolean agreeTerm) {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.sign_up));
        currentState = QUERY_PASSWORD_STATE;

        queryPasswordFragment.setTermCheckBoxChecked(agreeTerm);

        fragmentManager.popBackStack();
    }

    @Override
    public void agreeToTerm() {
        backToQueryPasswordView(true);
    }

    @Override
    public void disagreeToTerm() {
        backToQueryPasswordView(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        switch (--currentState) {
            case QUERY_TEL_STATE:
                // fall through
            case QUERY_PASSWORD_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.sign_up));
                break;
            case QUERY_ROLE_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.your_role));
                break;
            case VIEW_TERM_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.term_of_use));
                break;
            default:
                break;
        }
    }

    @Override
    public void onTelUsedError() {
        queryTelFragment.showTelUsedError();
    }

    @Override
    public void onTelAvailableForSign() {
        presenter.queryTelPIN(queryTelFragment.getTelNo());
    }

    @Override
    public void onNicknameUsedError() {
        queryPasswordFragment.showNicknameUsedError();
    }

    @Override
    public void onReadyForSign() {
        presenter.setUserNickname(queryPasswordFragment.getNickname());
        presenter.setUserPassword(queryPasswordFragment.getPassword());

        try {
            presenter.performSignUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSignUpSuccessful() {
        InitializeProfileActivity.startMe(this, isDietitian);
        finish();
    }

    @Override
    public void onNetworkError(String msg) {
        WbToastFactory.create(this, msg).show();
    }
}
