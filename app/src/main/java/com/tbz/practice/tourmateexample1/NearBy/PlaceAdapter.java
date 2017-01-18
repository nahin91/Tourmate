package com.tbz.practice.tourmateexample1.NearBy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.tbz.practice.tourmateexample1.R;

import java.util.ArrayList;

/**
 * Created by TYCOHANX on 1/15/2017.
 */

public class PlaceAdapter extends ArrayAdapter{
    Context context;
    ArrayList<Places>placesList;
    Places places;

    public PlaceAdapter(Context context, ArrayList<Places> placesList) {
        super(context, R.layout.place_row_layout,placesList);
        this.context=context;
        this.placesList=placesList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.place_row_layout,parent,false);

            viewHolder= new ViewHolder();
            viewHolder.placeNameTV=(TextView)view.findViewById(R.id.place_name_tv);
            viewHolder.placeAddressTV=(TextView)view.findViewById(R.id.place_address_tv);
            //viewHolder.placeRatingTv=(TextView)view.findViewById(R.id.placeRatingTv);
            //viewHolder.placeOpeningHourTv=(TextView)view.findViewById(R.id.placeOpensTv);
            view.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) view.getTag();
        }

        places=placesList.get(position);

        viewHolder.placeNameTV.setText(places.getName());
        viewHolder.placeAddressTV.setText(places.getAddress());
//        viewHolder.placeRatingTv.setText("Rating : "+places.getRating().toString());
        //viewHolder.placeOpeningHourTv.setText(String.valueOf(places.isOpeningHour()));



        return view;


    }

    static class ViewHolder{

        TextView placeNameTV;
        TextView placeAddressTV;
        TextView placeOpeningHourTv;
        TextView placeRatingTv;



    }
}
