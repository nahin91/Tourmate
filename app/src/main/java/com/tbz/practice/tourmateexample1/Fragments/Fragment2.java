package com.tbz.practice.tourmateexample1.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.tbz.practice.tourmateexample1.ForecastAdapter;
import com.tbz.practice.tourmateexample1.ForecastServiceAPI;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.forecastResponse.ForecastResponse;
import com.tbz.practice.tourmateexample1.forecastResponse.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 15-Dec-16.
 */
public class Fragment2 extends Fragment {

    ForecastResponse forcastResponse;
    ForecastServiceAPI forecastServiceAPI;
    ForecastAdapter adapter;
    ListView showList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.frag2,container,false);
        showList= (ListView) rootView.findViewById(R.id.showList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        forecastServiceAPI = retrofit.create(ForecastServiceAPI.class);
        calldata();

        //Toast.makeText(getContext().getApplicationContext(), weatherResponce.getMain().getTemp().toString(), Toast.LENGTH_SHORT).show();

        return rootView;

    }

    private void calldata() {
        Call<ForecastResponse> forecastResponseCall=forecastServiceAPI.getForecastResponse();
        forecastResponseCall.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                ForecastResponse forecastResponse=response.body();

                java.util.List<List> lists = forecastResponse.getList();

                Toast.makeText(getContext(), forecastResponse.getCity().getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), lists.get(0).getTemp().getDay().toString(), Toast.LENGTH_SHORT).show();;

                adapter=new ForecastAdapter(getContext().getApplicationContext(),lists);
                showList.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //weatherResponce = (WeatherResponce) getArguments().getSerializable("response");


        /*if(weatherResponce!=null)
            Toast.makeText(getContext().getApplicationContext(), "got data", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext().getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();*/
    }
    public void getWeatherResponse(ForecastResponse example){
        this.forcastResponse = example;
    }
}
