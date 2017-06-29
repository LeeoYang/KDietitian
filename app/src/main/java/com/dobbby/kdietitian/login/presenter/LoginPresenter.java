package com.dobbby.kdietitian.login.presenter;

import com.dobbby.kdietitian.login.model.IUsernameGenerator;
import com.dobbby.kdietitian.login.model.LoginService;
import com.dobbby.kdietitian.login.model.UsernameGenerator;
import com.dobbby.kdietitian.login.view.OnLoginFeedbackListener;
import com.dobbby.kdietitian.util.TokenStorer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.dobbby.kdietitian.util.Debug.DEBUG;

/**
 * Created by Dobbby on 2017/6/23.
 */
@ParametersAreNonnullByDefault
public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();

    private OnLoginFeedbackListener listener;
    private IUsernameGenerator usernameGenerator;
    private LoginService service;

    public LoginPresenter(OnLoginFeedbackListener listener, String baseUrl) {
        this.listener = listener;
        init(baseUrl);
    }

    private void init(String baseUrl) {
        usernameGenerator = new UsernameGenerator();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(LoginService.class);
    }

    public void performLoginByTel(String tel, String password) {
        if (DEBUG) {
            listener.onAuthorized();
            return;
        }

        String username = usernameGenerator.generateUsernameFromTel(tel);

        Call<String> call = service.login(username, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() != 200) {
                    listener.onError(response.message());
                    return;
                }

                String token = response.body();

                if (token == null || token.length() == 0 || token.equals("null")) {
                    listener.onAuthorizationFailed();
                    return;
                }

                TokenStorer.storeToken(token);
                listener.onAuthorized();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
