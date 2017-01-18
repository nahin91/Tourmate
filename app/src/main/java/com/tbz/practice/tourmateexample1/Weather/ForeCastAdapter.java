package com.tbz.practice.tourmateexample1.Weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tbz.practice.tourmateexample1.R;

import java.util.ArrayList;

/**
 * Created by TYCOHANX on 12/21/2016.
 */

public class ForeCastAdapter extends ArrayAdapter{

    private ForeCasts foreCasts;
    private ArrayList<ForeCasts>foreCastsList;
    private Context context;
    String showTempUnit,showSpeedUnit;

    public ForeCastAdapter (Context context, ArrayList<ForeCasts>foreCastsList){
        super(context, R.layout.fragment_event_details,foreCastsList);
        this.context=context;
        this.foreCastsList=foreCastsList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if(view ==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.forecast_layout_weather,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.dateTV=(TextView)view.findViewById(R.id.forecast_date_tv);
            viewHolder.speedTV=(TextView)view.findViewById(R.id.forecast_speed_tv);
            viewHolder.minTV=(TextView)view.findViewById(R.id.forecast_min_tv);
            viewHolder.maxTV=(TextView)view.findViewById(R.id.forecast_max_tv);
            viewHolder.descriptionTV=(TextView)view.findViewById(R.id.forecast_description_tv);
            viewHolder.iconIV=(ImageView) view.findViewById(R.id.forecast_icon_iv);
            viewHolder.humidityTV= (TextView) view.findViewById(R.id.forecast_humidity_tv);
            viewHolder.pressureTV= (TextView) view.findViewById(R.id.forecast_pressure_tv);

            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)view.getTag();
        }




        foreCasts =foreCastsList.get(position);
        viewHolder.dateTV.setText(foreCasts.getDate());

        if(MainActivityWeather.unit=="metric"){
            showTempUnit=" °C";
            showSpeedUnit=" m/s";
        }else
        { showTempUnit="°F";
        showSpeedUnit="Mi/hr";}
        viewHolder.speedTV.setText(foreCasts.getSpeed()+showSpeedUnit);
        viewHolder.minTV.setText(foreCasts.getMinTemp().toString()+showTempUnit+"/");
        viewHolder.maxTV.setText(foreCasts.getMaxTemp().toString()+showTempUnit);
        viewHolder.descriptionTV.setText(foreCasts.getDescription());
        viewHolder.humidityTV.setText(foreCasts.getHumidty()+"%");
        viewHolder.pressureTV.setText(foreCasts.getPressure()+" hpa");
        String icon =foreCasts.getIcon();



        Picasso.with(getContext().getApplicationContext()).load("http://openweathermap.org/img/w/"+icon+".png").into(viewHolder.iconIV);



        return view;
    }


    static class ViewHolder{

        TextView dateTV;
        TextView speedTV;
        TextView minTV;
        TextView maxTV;
        TextView descriptionTV;
        ImageView iconIV;
        TextView humidityTV;
        TextView pressureTV;


    }
}



