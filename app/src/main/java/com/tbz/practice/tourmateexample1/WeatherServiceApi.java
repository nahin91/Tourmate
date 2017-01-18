package com.tbz.practice.tourmateexample1;

import com.tbz.practice.tourmateexample1.response.WeatherResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by USER on 03-Jan-17.
 */

public interface WeatherServiceApi {

    //a08e0c10fac76b0cf63afb035f0ffc7e

    @GET()
    Call<WeatherResponce> getWeatherInformation(@Url String url);


}
