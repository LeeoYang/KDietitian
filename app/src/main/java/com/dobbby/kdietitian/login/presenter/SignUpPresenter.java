package com.dobbby.kdietitian.login.presenter;

import android.util.Log;
import com.dobbby.kdietitian.bean.User;
import com.dobbby.kdietitian.login.model.IUsernameGenerator;
import com.dobbby.kdietitian.login.model.SignUpService;
import com.dobbby.kdietitian.login.model.UsernameGenerator;
import com.dobbby.kdietitian.login.view.OnSignUpFeedbackListener;
import com.dobbby.kdietitian.util.TokenStorer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by Dobbby on 2017/6/25.
 * <p>
 * When signing up, clients should provide variables below:
 * {@link User#username}
 * {@link User#plainPassword}
 * {@link User#telephoneNumber}
 * {@link User#role}
 */
@ParametersAreNonnullByDefault
public class SignUpPresenter {
    private static final String TAG = SignUpPresenter.class.getSimpleName();

    private IUsernameGenerator usernameGenerator;
    private OnSignUpFeedbackListener listener;
    private SignUpService service;
    private User userToAdd;

    public SignUpPresenter(OnSignUpFeedbackListener listener, String baseUrl) {
        this.listener = listener;
        init(baseUrl);
    }

    private void init(String baseUrl) {
        usernameGenerator = new UsernameGenerator();
        userToAdd = new User();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(SignUpService.class);
    }

    public void checkIfTelUsed(final String telNo) {
        String username = usernameGenerator.generateUsernameFromTel(telNo);

        Call<Boolean> call = service.checkIfUsernameExists(username);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() != 200) {
                    listener.onNetworkError(response.message());
                    return;
                }

                Boolean res = response.body();

                if (res == null) {
                    listener.onNetworkError("response=null");
                    return;
                }

                if (!res) {
                    listener.onTelUsedError();
                } else {
                    listener.onTelAvailableForSign();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                listener.onNetworkError(t.getMessage());
            }
        });
    }

    public void checkNicknameUsed(final String nickname) {
        Call<Boolean> call = service.checkIfNicknameExists(nickname);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() != 200) {
                    listener.onNetworkError(response.message());
                    return;
                }

                Boolean res = response.body();

                if (res == null) {
                    listener.onNetworkError("response=null");
                    return;
                }

                if (!res) {
                    listener.onNicknameUsedError();
                } else {
                    listener.onReadyForSign();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                listener.onNetworkError(t.getMessage());
            }
        });
    }

    public void performSignUp() {
        Call<String> call = service.signUp(userToAdd);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() != 200) {
                    listener.onNetworkError(response.message());
                    return;
                }

                String token = response.body();

                if (token == null || token.length() == 0 || token.equals("null")) {
                    listener.onNetworkError("token=null");
                    return;
                }

                listener.onSignUpSuccessful();
                TokenStorer.storeToken(token);

                Log.d(TAG, "onResponse: token = " + token);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onNetworkError(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void queryTelPIN(String telNo) {
        Log.i(TAG, "queryTelPIN: ");
        // TODO: 2017/6/25 query tel PIN
    }

    public void setUserTel(String telNo) {
        userToAdd.setTelephoneNumber(telNo);
        userToAdd.setUsername(usernameGenerator.generateUsernameFromTel(telNo));

        Log.i(TAG, "setUserTel: tel=" + telNo);
    }

    public void setUserRole(User.Role role) {
        userToAdd.setRole(role);

        Log.i(TAG, "setUserRole: role=" + role.name());
    }

    public void setUserNickname(String nickname) {
        userToAdd.setNickname(nickname);

        Log.i(TAG, "setUserNickname: nickname=" + nickname);
    }

    public void setUserPassword(String password) {
        userToAdd.setPlainPassword(password);

        Log.i(TAG, "setUserPassword: password=" + password);
    }
}
