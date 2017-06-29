package com.dobbby.kdietitian.login.view;

/**
 * Created by Dobbby on 2017/6/25.
 */
public interface OnSignUpFeedbackListener {
    void onTelUsedError();

    void onTelAvailableForSign();

    void onNicknameUsedError();

    void onReadyForSign();

    void onSignUpSuccessful();

    void onNetworkError(String msg);
}
