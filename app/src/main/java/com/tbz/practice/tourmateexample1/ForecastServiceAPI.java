package com.tbz.practice.tourmateexample1;

import com.tbz.practice.tourmateexample1.forecastResponse.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by USER on 03-Jan-17.
 */

public interface ForecastServiceAPI {

    @GET("data/2.5/forecast/daily?q=Dhaka&units=metric&cnt=7&appid=a08e0c10fac76b0cf63afb035f0ffc7e")
    Call<ForecastResponse> getForecastResponse();

}
