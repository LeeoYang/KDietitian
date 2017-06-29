package com.dobbby.kdietitian.login.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dobbby on 2017/6/23.
 */
public interface LoginService {
    @FormUrlEncoded
    @POST("login")
    Call<String> login(@Field("username") String username, @Field("password") String password);
}
