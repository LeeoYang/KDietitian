package com.dobbby.kdietitian.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.bean.DietitianAuthQuery;
import com.dobbby.kdietitian.bean.User;
import com.dobbby.kdietitian.login.presenter.DietitianAuthPresenter;
import com.dobbby.kdietitian.login.presenter.ProfilePresenter;
import com.dobbby.kdietitian.login.view.OnInitProfileFeedbackListener;
import com.dobbby.kdietitian.normal.UserMainActivity;
import com.dobbby.kdietitian.util.WbToastFactory;
import com.dobbby.kdietitian.util.WbToolbarPlugin;

import java.util.Date;

public class InitializeProfileActivity extends AppCompatActivity implements
        InitGenderBirthdayFragment.OnFragmentInteractionListener,
        InitHeightWeightFragment.OnFragmentInteractionListener,
        InitGoalFragment.OnFragmentInteractionListener,
        InitPlanFragment.OnFragmentInteractionListener,
        SubmitDietitianAuthTextFragment.OnFragmentInteractionListener,
        SubmitDietitianAuthPhotoFragment.OnFragmentInteractionListener,
        OnInitProfileFeedbackListener {

    private static final String ARG_IS_DIETITIAN = "isDietitian";

    private FragmentManager fragmentManager;
    private InitGenderBirthdayFragment genderBirthdayFragment;

    // below are for normal users
    private InitHeightWeightFragment heightWeightFragment;
    private InitGoalFragment goalFragment;
    private InitPlanFragment planFragment;

    // below are for dietitians
    private SubmitDietitianAuthTextFragment dietitianAuthTextFragment;
    private SubmitDietitianAuthPhotoFragment dietitianAuthPhotoFragment;

    private static final int INIT_GENDER_BIRTHDAY_STATE = 0;

    // below are for normal users
    private static final int INIT_HEIGHT_WEIGHT_STATE = 1;
    private static final int INIT_GOAL_STATE = 2;
    private static final int INIT_PLAN_STATE = 3;

    // below are for dietitians
    private static final int INIT_DIETITIAN_AUTH_TEXT_STATE = 4;
    private static final int INIT_DIETITIAN_AUTH_PHOTO_STATE = 5;

    private ProfilePresenter profilePresenter;
    private DietitianAuthPresenter dietitianAuthPresenter;

    private int currentState;
    private boolean isDietitian;

    public static void startMe(Context context, boolean isDietitian) {
        context.startActivity(
                new Intent(context, InitializeProfileActivity.class)
                        .putExtra(ARG_IS_DIETITIAN, isDietitian)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_profile);

        WbToolbarPlugin.attach(this);

        init();
    }

    private void init() {
        isDietitian = getIntent().getBooleanExtra(ARG_IS_DIETITIAN, false);
        fragmentManager = getSupportFragmentManager();
        genderBirthdayFragment = InitGenderBirthdayFragment.newInstance();
        heightWeightFragment = InitHeightWeightFragment.newInstance();
//        goalFragment = InitGoalFragment.newInstance();
//        planFragment = InitPlanFragment.newInstance();
        profilePresenter = new ProfilePresenter(this, getString(R.string.base_url));
        dietitianAuthPresenter = new DietitianAuthPresenter();

        addInitGenderBirthFragment();
    }

    private void addInitGenderBirthFragment() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.init_profile));
        currentState = INIT_GENDER_BIRTHDAY_STATE;

        fragmentManager
                .beginTransaction()
                .replace(R.id.init_profile_fragment_container, genderBirthdayFragment)
                .commit();
    }

    @Override
    public void initGenderBirthday(User.Gender gender, Date birthday) {
        profilePresenter.performInitGenderBirthday(gender, birthday);
    }

    @Override
    public void initHeightWeight(float height, float weight) {
        profilePresenter.performInitHeightWeight(height, weight);
    }

    @Override
    public void initAimWeight(float aimWeight) {
        profilePresenter.dietPlanSetAimWeight(aimWeight);
        goToInitPlanView(profilePresenter.getCurrentWeight(), aimWeight);
    }

    @Override
    public void initPlan(float dietPerWeek) {
        profilePresenter.performAddDietPlan(dietPerWeek);
    }

    @Override
    public void submitAuthText(DietitianAuthQuery dietitianAuthQuery) {
        dietitianAuthPresenter.setDietitianAuthQuery(dietitianAuthQuery);
        goToInitDietitianAuthPhotoView();
    }

    @Override
    public void sendDietitianAuthQuery() {
        dietitianAuthPresenter.sendDietitianAuthQuery();
        LoginActivity.startMeAsThanksPage(this);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        switch (--currentState) {
            case INIT_GENDER_BIRTHDAY_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.init_profile));
                break;
            case INIT_HEIGHT_WEIGHT_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.my_progress));
                break;
            case INIT_GOAL_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.my_goal));
                break;
            case INIT_PLAN_STATE:
                currentState = INIT_GENDER_BIRTHDAY_STATE;
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.init_profile));
                break;
            case INIT_DIETITIAN_AUTH_TEXT_STATE:
                WbToolbarPlugin.setToolbarTitle(this, getString(R.string.init_auth));
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String msg) {
        WbToastFactory.create(this, msg).show();
    }

    private void goToInitHeightWeightView() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.my_progress));
        currentState = INIT_HEIGHT_WEIGHT_STATE;

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.init_profile_fragment_container, heightWeightFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToInitGoalView() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.my_goal));
        currentState = INIT_GOAL_STATE;

        goalFragment = InitGoalFragment.newInstance();

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.init_profile_fragment_container, goalFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToInitPlanView(float currentWeight, float aimWeight) {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.my_plan));
        currentState = INIT_PLAN_STATE;

        planFragment = InitPlanFragment.newInstance(currentWeight, aimWeight);

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.init_profile_fragment_container, planFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToInitDietitianAuthTextView() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.init_auth));
        currentState = INIT_DIETITIAN_AUTH_TEXT_STATE;

        dietitianAuthTextFragment = SubmitDietitianAuthTextFragment.newInstance();

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.init_profile_fragment_container, dietitianAuthTextFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToInitDietitianAuthPhotoView() {
        WbToolbarPlugin.setToolbarTitle(this, getString(R.string.init_auth));
        currentState = INIT_DIETITIAN_AUTH_PHOTO_STATE;

        dietitianAuthPhotoFragment = SubmitDietitianAuthPhotoFragment.newInstance();

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.init_profile_fragment_container, dietitianAuthPhotoFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onSetGenderBirthdaySuccessful() {
        if (isDietitian) {
            goToInitDietitianAuthTextView();
        } else {
            goToInitHeightWeightView();
        }
    }

    @Override
    public void onSetHeightWeightSuccessful() {
        goToInitGoalView();
    }

    @Override
    public void onSetPlanSuccessful() {
        startActivity(new Intent(this, UserMainActivity.class));
        finish();
    }
}
