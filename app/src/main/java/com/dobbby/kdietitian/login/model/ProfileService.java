package com.dobbby.kdietitian.login.model;

import com.dobbby.kdietitian.bean.DietPlan;
import com.dobbby.kdietitian.bean.Health;
import com.dobbby.kdietitian.bean.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

/**
 * Created by Dobbby on 2017/6/25.
 */
public interface ProfileService {
    String AUTH = "Authorization";

    @PATCH("profile/update")
    Call<User> initializeProfile(@Header(AUTH) String token, @Body User user);

    @POST("health")
    Call<Health> initializeHeightWeight(@Header(AUTH) String token, @Body Health health);

    @POST("dietplan")
    Call<DietPlan> addDietPlan(@Header(AUTH) String token, @Body DietPlan dietPlan);
}
