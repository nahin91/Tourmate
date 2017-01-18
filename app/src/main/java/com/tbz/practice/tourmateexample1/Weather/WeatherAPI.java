package com.tbz.practice.tourmateexample1.Weather;

import com.tbz.practice.tourmateexample1.Weather.Services.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by TYCOHANX on 12/20/2016.
 */

public interface WeatherAPI {

    @GET()
    Call<WeatherResponse> getWeatherProvider(@Url String url);
}
