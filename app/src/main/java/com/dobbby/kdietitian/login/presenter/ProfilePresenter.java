package com.dobbby.kdietitian.login.presenter;

import android.util.Log;
import com.dobbby.kdietitian.bean.DietPlan;
import com.dobbby.kdietitian.bean.Health;
import com.dobbby.kdietitian.bean.User;
import com.dobbby.kdietitian.login.model.ProfileService;
import com.dobbby.kdietitian.login.view.OnInitProfileFeedbackListener;
import com.dobbby.kdietitian.util.TokenStorer;
import com.dobbby.kdietitian.util.gson.DateTypeAdapter;
import com.dobbby.kdietitian.util.gson.UserUpdateExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Date;

/**
 * Created by Dobbby on 2017/6/25.
 */
@ParametersAreNonnullByDefault
public class ProfilePresenter {
    private static final String TAG = ProfilePresenter.class.getSimpleName();

    private OnInitProfileFeedbackListener listener;
    private ProfileService service;
    private Health health;
    private DietPlan dietPlan;
    private String baseUrl;

    public ProfilePresenter(OnInitProfileFeedbackListener listener, String baseUrl) {
        this.listener = listener;
        this.baseUrl = baseUrl;
        init(baseUrl);
    }

    private void init(String baseUrl) {
        dietPlan = new DietPlan();
        health = new Health();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ProfileService.class);
    }

    public void performInitGenderBirthday(User.Gender gender, Date birthday) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new UserUpdateExclusionStrategy())
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProfileService service = retrofit.create(ProfileService.class);

        User user = new User();
        user.setGender(gender);
        user.setBirthday(birthday);

        Log.i(TAG, "performInitGenderBirthday: gender=" + gender.name() + ", birthday=" + birthday);

        Call<User> call = service.initializeProfile(TokenStorer.getTokenHeaderValue(), user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    listener.onSetGenderBirthdaySuccessful();
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void performInitHeightWeight(float height, float weight) {
        health.setHeight(height).setWeight(weight);

        Log.i(TAG, "performInitHeightWeight: height=" + height + ", weight=" + weight);

        Call<Health> call = service.initializeHeightWeight(TokenStorer.getTokenHeaderValue(), health);

        call.enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                if (response.code() == 200) {
                    listener.onSetHeightWeightSuccessful();
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void dietPlanSetAimWeight(float aimWeight) {
        dietPlan.setAimWeight(aimWeight);
        Log.i(TAG, "dietPlanSetAimWeight: aimWeight=" + aimWeight);
    }

    public float getCurrentWeight() {
        return health.getWeight();
    }

    public void performAddDietPlan(float dietPerWeek) {
        dietPlan.setDietPerWeek(dietPerWeek);
        dietPlan.setStartWeight(health.getWeight());

        Log.i(TAG, "performAddDietPlan: dietPerWeek=" + dietPerWeek);

        Call<DietPlan> call = service.addDietPlan(TokenStorer.getTokenHeaderValue(), dietPlan);

        call.enqueue(new Callback<DietPlan>() {
            @Override
            public void onResponse(Call<DietPlan> call, Response<DietPlan> response) {
                if (response.code() == 200) {
                    listener.onSetPlanSuccessful();
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DietPlan> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
