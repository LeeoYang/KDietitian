package com.dobbby.kdietitian.login.model;

import com.dobbby.kdietitian.bean.User;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Dobbby on 2017/6/25.
 */
public interface SignUpService {
    @GET("register/check")
    Call<Boolean> checkIfUsernameExists(@Query("username") String username);

    @GET("register/check")
    Call<Boolean> checkIfNicknameExists(@Query("nickname") String nickname);

    @POST("register")
    Call<String> signUp(@Body User user);
}
