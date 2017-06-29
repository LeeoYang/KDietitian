package com.dobbby.kdietitian.login.view;

/**
 * Created by Dobbby on 2017/6/25.
 */
public interface OnInitProfileFeedbackListener {
    void onError(String msg);

    void onSetGenderBirthdaySuccessful();

    void onSetHeightWeightSuccessful();

    void onSetPlanSuccessful();
}
