package com.dobbby.kdietitian.login.view;

/**
 * Created by Dobbby on 2017/6/23.
 */
public interface OnLoginFeedbackListener {
    void onError(String error);

    void onAuthorized();

    void onAuthorizationFailed();
}
