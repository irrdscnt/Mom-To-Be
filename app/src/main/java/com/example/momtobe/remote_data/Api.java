package com.example.momtobe.remote_data;


import com.example.momtobe.models.Baby;
import com.example.momtobe.models.Fitness;
import com.example.momtobe.models.NutritionAdvice;
import com.example.momtobe.models.User;
import com.example.momtobe.response.LoginResponse;
import com.example.momtobe.ui.calendar.Event;

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

    @GET("api/babies/week/{week}/")
    Call<List<Baby>> getBabyInfoByTrimester(@Path("week") int week);

    @POST("api/events/")
    Call<Void> saveEvent(@Body Event event);

    @GET("api/events/date/{date}/")
    Call<List<Event>> getEventsByDate(@Path("date") String date);

    @GET("api/fitness/trimester/{trimester}/")
    Call<List<Fitness>> getFitnessByTrimester(@Path("trimester") int trimester);
}
