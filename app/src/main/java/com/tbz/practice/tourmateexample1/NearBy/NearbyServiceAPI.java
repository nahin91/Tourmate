package com.tbz.practice.tourmateexample1.NearBy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by TYCOHANX on 12/25/2016.
 */

public interface NearbyServiceAPI {

    @GET()
    Call<NearByResponse>getAllResponses(@Url String urlString);
}
