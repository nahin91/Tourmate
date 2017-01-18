package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tbz.practice.tourmateexample1.forecastResponse.ForecastResponse;
import com.tbz.practice.tourmateexample1.forecastResponse.List;

import java.util.Calendar;

/**
 * Created by USER on 03-Jan-17.
 */

public class ForecastAdapter extends ArrayAdapter {

    java.util.List<List> forecastList;
    ForecastResponse forecastResponse;
    Context context;


    ImageView iconIv;
    TextView dayTv;
    TextView tempMinMaxTv;
    TextView weatherConditionTv;
    TextView windSpeedTv;
    TextView humidityTv;
    TextView pressureTv;
    TextView dateTv;
    Picasso picasso;
    Calendar calendar=Calendar.getInstance();
    int year=calendar.get(calendar.YEAR);
    int month=calendar.get(calendar.MONTH);
    int date=calendar.get(calendar.DATE);

    public ForecastAdapter(Context context, java.util.List<List> forecastList) {
        super(context,R.layout.weather_adapter_layout,forecastList);
        if(forecastList!=null)
            Toast.makeText(context, "forecast has data", Toast.LENGTH_SHORT).show();
        this.context=context;
        this.forecastList=forecastList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.weather_adapter_layout,parent,false);

        dayTv=(TextView)view.findViewById(R.id.weather_day_tv);
        iconIv=(ImageView) view.findViewById(R.id.weather_icon_iv);
        dateTv=(TextView)view.findViewById(R.id.weather_day_tv);
        tempMinMaxTv= (TextView) view.findViewById(R.id.temMinMax);
        weatherConditionTv= (TextView) view.findViewById(R.id.weatherConditionTv);
        windSpeedTv= (TextView) view.findViewById(R.id.weatherWindSpeed);
        humidityTv= (TextView) view.findViewById(R.id.weatherHumidityTv);
        pressureTv= (TextView) view.findViewById(R.id.weatherPressurTv);

        //dateTv.setText("Date: "+String.valueOf((int)date+position)+"-"+month+"-"+year);
        int days=calendar.get(calendar.DAY_OF_WEEK)+position;
        if(days>7)
            days=days%7;
        String dayName=null;
        switch (days)
        {
            case 1:dayName="Sunday";
                break;
            case 2:dayName="Monday";
                break;
            case 3:dayName="Tuesday";
                break;
            case 4:dayName="Wednesday";
                break;
            case 5:dayName="Thursday";
                break;
            case 6:dayName="Friday";
                break;
            case 7:dayName="Saturday";
                break;
            default:break;
        }

        dayTv.setText(dayName+" "+String.valueOf((int)date+position)+"-"+month+"-"+year);

        picasso.with(context).load(WeatherActivity.BASE_URL_OF_ICON
                +forecastList.get(position).getWeather().get(0).getIcon()+WeatherActivity._PNG).into(iconIv);

        tempMinMaxTv.setText(forecastList.get(position).getTemp().getMax().toString()+"° / "+forecastList.get(position).getTemp().getMin().toString()+"°");
        weatherConditionTv.setText(forecastList.get(position).getWeather().get(0).getDescription());
        windSpeedTv.setText(forecastList.get(position).getSpeed().toString()+" m/s");
        humidityTv.setText(forecastList.get(position).getHumidity().toString()+" %");
        pressureTv.setText(forecastList.get(position).getPressure().toString()+" hpa");


        return view;
    }
}
