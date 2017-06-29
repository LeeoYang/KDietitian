package com.dobbby.kdietitian.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.dobbby.kdietitian.KDApplication;

/**
 * Created by Dobbby on 2017/6/25.
 */
public class TokenStorer {
    private static final String NAME_TOKEN = "token_sp_name";
    private static final String TOKEN_HEAD = "Bearer ";

    private static SharedPreferences preferences;
    private static String token;

    private static void checkSharedPreferencesNonNull() {
        if (preferences == null) {
            preferences = KDApplication.getInstance().getSharedPreferences(NAME_TOKEN, Context.MODE_PRIVATE);
        }
    }

    /**
     * I don't see the method needed for client use,
     * so I've made it private.
     *
     * @return token raw value
     */
    private static String getToken() {
        if (token == null) {
            checkSharedPreferencesNonNull();
            token = preferences.getString("token", null);
        }

        return token;
    }

    /**
     * When sending to server through Authorization header,
     * use this method instead of {@link #getToken()}.
     *
     * @return Authorization header value
     */
    public static String getTokenHeaderValue() {
        return TOKEN_HEAD + getToken();
    }

    /**
     * Store token to SharedPreference and refresh {@code token}.
     *
     * @param token token that needs to be stored.
     */
    public static void storeToken(String token) {
        checkSharedPreferencesNonNull();

        TokenStorer.token = token;

        preferences.edit()
                .putString("token", token)
                .apply();
    }
}
