package com.tbz.practice.tourmateexample1.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by TYCOHANX on 1/12/2017.
 */

public interface WebServiceAPI {

    @GET()
    Call<WebResponse> getAllProvider(@Url String url);
}
