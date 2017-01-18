package com.tbz.practice.tourmateexample1.Weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.Weather.Services_Forecast.ForeCastResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TYCOHANX on 12/17/2016.
 */

public class ForeCastActivity extends Fragment {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy-E");





    ForeCasts forecasts;
    ArrayList<ForeCasts>forecastList=new ArrayList<>();
    ListView listView;
    ForeCastAdapter forecastAdapter;

    Retrofit retrofit;
    public static String BASE_URL="http://api.openweathermap.org/";
    ForeCastResponse forecastResponses;

    String tempData;
    public String unit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_forecast_weather, container, false);

        listView=(ListView)rootView.findViewById(R.id.forecast_listview);
        tempData=getArguments().getString("city");
        unit=getArguments().getString("a");

        forecastAdapter=new ForeCastAdapter(getContext().getApplicationContext(),forecastList);



        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForeCastAPI foreCastAPI =retrofit.create(ForeCastAPI.class);

        String url = String.format("data/2.5/forecast/daily?q=%s&mode=json&units=%s&cnt=16&appid=a08e0c10fac76b0cf63afb035f0ffc7e", tempData, unit);
        final Call<ForeCastResponse> foreCastResponseCall=foreCastAPI.getForeCastProvider(url);

        foreCastResponseCall.enqueue(new Callback<ForeCastResponse>() {
            @Override
            public void onResponse(Call<ForeCastResponse> call, Response<ForeCastResponse> response) {
                forecastResponses= response.body();

                createList();
                listView.setAdapter(forecastAdapter);
            }
            @Override
            public void onFailure(Call<ForeCastResponse> call, Throwable t) {

                Toast.makeText(getContext().getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }


    public void createList(){
        for(int i=1;i<15;i++){
            String date=sdf.format(calendar.getTime());
            String speed=forecastResponses.getList().get(i).getSpeed().toString();
            String icon=forecastResponses.getList().get(i).getWeather().get(0).getIcon();
            double min=forecastResponses.getList().get(i).getTemp().getMin();
            double max=forecastResponses.getList().get(i).getTemp().getMax();
            String description = forecastResponses.getList().get(i).getWeather().get(0).getDescription();
            String humidity = forecastResponses.getList().get(i).getHumidity().toString();
            String pressure = forecastResponses.getList().get(i).getPressure().toString();

            forecasts= new ForeCasts();

            forecasts.setSpeed(speed);
            forecasts.setDescription(description);
            forecasts.setIcon(icon);
            forecasts.setMinTemp(min);
            forecasts.setMaxTemp(max);
            forecasts.setDate(date);
            forecasts.setHumidty(humidity);
            forecasts.setPressure(pressure);


            forecastList.add(forecasts);
            calendar.add(Calendar.DATE,1);



        }






    }
}
