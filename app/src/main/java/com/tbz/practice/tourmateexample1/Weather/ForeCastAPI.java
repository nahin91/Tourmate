package com.tbz.practice.tourmateexample1.Weather;

import com.tbz.practice.tourmateexample1.Weather.Services_Forecast.ForeCastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by TYCOHANX on 12/20/2016.
 */

public interface ForeCastAPI {



    @GET()
    Call<ForeCastResponse>getForeCastProvider(@Url String url);

    //"data/2.5/forecast/daily?q="+city+"&mode=json&units=metric&cnt=16&appid=b777c40f7a93cb4dd73c245ac5e91f46"


}
