package com.example.momtobe.remote_data;


import com.example.momtobe.models.NutritionAdvice;
import com.example.momtobe.models.User;
import com.example.momtobe.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @POST("api/auth/users")
    Call<User> registrationNewUser(@Body User user);
    @POST("api/auth/login")Call<LoginResponse> checkLoginUser(@Body User user);

    @GET("api/nutrition-advice/trimester/{trimester}/")
    Call<List<NutritionAdvice>> getNutritionAdviceByTrimester(@Path("trimester") int trimester);
}
