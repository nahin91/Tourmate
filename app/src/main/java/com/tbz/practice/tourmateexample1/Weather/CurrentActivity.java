package com.tbz.practice.tourmateexample1.Weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.Weather.Services.WeatherResponse;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TYCOHANX on 12/17/2016.
 */

public class CurrentActivity extends Fragment {

    TextView temperatureTV;
    TextView updateTV;
    TextView dateTV;
    TextView humidityTV;
    TextView sunriseTV;
    TextView sunsetTV;
    TextView pressureTV;
    TextView cityTV;
    TextView windSpeedTV;
    WeatherAPI weatherApi;
    Retrofit retrofit;
    Picasso picasso;
    ImageView iconIV;
    String tempData;
    String unit;

    public String showTempUnit;
    String showSpeedUnit;

   public  String tempString,humidity,pressure,sunRiseString,sunSetString,city,update,speed;


    double temp;
    private static final String BASE_URL = "http://api.openweathermap.org/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_current_weather, container, false);


        tempData = getArguments().getString("city");
        unit=getArguments().getString("a");

        iconIV = (ImageView) rootView.findViewById(R.id.icon);
        temperatureTV = (TextView) rootView.findViewById(R.id.temperature_current);
        dateTV = (TextView) rootView.findViewById(R.id.date_current);
        humidityTV = (TextView) rootView.findViewById(R.id.humidity_current);
        sunriseTV = (TextView) rootView.findViewById(R.id.sunrise_current);
        sunsetTV = (TextView) rootView.findViewById(R.id.sunset_current);
        pressureTV = (TextView) rootView.findViewById(R.id.pressure_current);
        cityTV = (TextView) rootView.findViewById(R.id.city_current);
        updateTV = (TextView) rootView.findViewById(R.id.updated_tv);
        windSpeedTV = (TextView) rootView.findViewById(R.id.wind_speed);
        callAPI();

        return rootView;


    }

    public void callAPI(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherAPI.class);


        String currentCity = tempData;

        String url = String.format("data/2.5/weather?q=%s&mode=json&units=%s&appid=b777c40f7a93cb4dd73c245ac5e91f46", currentCity, unit);


        final Call<com.tbz.practice.tourmateexample1.Weather.Services.WeatherResponse> weatherResponseCall = weatherApi.getWeatherProvider(url);


        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                WeatherResponse weatherResponses = response.body();


                Double tempTemp = weatherResponses.getMain().getTemp();
                DecimalFormat df = new DecimalFormat("0.##");
                temp = tempTemp;


                tempString = String.valueOf(df.format(temp));
                humidity = weatherResponses.getMain().getHumidity().toString();
                 pressure = weatherResponses.getMain().getPressure().toString();
                 city = weatherResponses.getName().toString();

                 String icon = weatherResponses.getWeather().get(0).getIcon();
                 speed = weatherResponses.getWind().getSpeed().toString();


                long srTime = (weatherResponses.getSys().getSunrise()) * 1000;
                Date sunRise = new Date(srTime);
                sunRiseString = new SimpleDateFormat("hh:mma").format(sunRise);


                long ssTime = (weatherResponses.getSys().getSunset()) * 1000;
                Date sunSet = new Date(ssTime);
                 sunSetString = new SimpleDateFormat("hh:mma").format(sunSet);

                update = "↺ last updated " + weatherResponses.getCustomUpdateTime();

                dateTV.setText(android.text.format.DateFormat.format("yyyy-MMM-dd-E", new Date()));
               if(unit=="metric"){
                    showTempUnit=" °C";
                   showSpeedUnit=" m/s";
               }else {showTempUnit="°F";
                showSpeedUnit="Mi/hr";}
                temperatureTV.setText(tempString +showTempUnit);
                humidityTV.setText(humidity + " %RH");
                pressureTV.setText(pressure + " hPA");
                sunriseTV.setText(sunRiseString);
                sunsetTV.setText(sunSetString);
                cityTV.setText(city);
                updateTV.setText(update);
                Picasso.with(getContext().getApplicationContext()).load("http://openweathermap.org/img/w/"+icon+".png").into(iconIV);

                windSpeedTV.setText(speed + showSpeedUnit);

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

                dateTV.setText("no connection");

            }
        });

    }



}




