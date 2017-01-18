package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 26-Dec-16.
 */

public class EventAdaptor extends ArrayAdapter {
    private Events events;
    private ArrayList<Events> eventList;
    private Context context;
    long days;



    public EventAdaptor(Context context, ArrayList<Events> eventList) {
        super(context,R.layout.event_list,eventList);
        this.eventList=eventList;
        this.context=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_list,parent,false);

            viewHolder=new ViewHolder();

            viewHolder.nameTv=(TextView)view.findViewById(R.id.eventNameTv);
            viewHolder.destinationTv=(TextView)view.findViewById(R.id.destinationTv);
            viewHolder.dateTv=(TextView)view.findViewById(R.id.dateTv);
            viewHolder.userIDTv=(TextView)view.findViewById(R.id.userIDTv);
            viewHolder.daysLeftTv= (TextView) view.findViewById(R.id.daysLeftTv);

            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();

        }

        events =eventList.get(position);

        String toyBornTime = events.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, d MMM yyyy");

        try {

            Date oldDate = dateFormat.parse(toyBornTime);
            System.out.println(oldDate);

            Date currentDate = new Date();
            Calendar c = Calendar.getInstance();
            currentDate = c.getTime();

            if(oldDate.getTime() > currentDate.getTime()){
                long diff = oldDate.getTime() - currentDate.getTime();
                days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                days=days+1;
                if(days==1){
                    viewHolder.daysLeftTv.setText(" Tomorrow");
                }else
                    viewHolder.daysLeftTv.setText(days+" days left");
            }else
                viewHolder.daysLeftTv.setText("Expired");



            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        viewHolder.nameTv.setText(events.getEventName());
        viewHolder.destinationTv.setText(events.getDestination());
        viewHolder.dateTv.setText(events.getDate());
        viewHolder.userIDTv.setText(events.getUserId()+"");


        return view;
    }

    private static class ViewHolder{
        TextView nameTv;
        TextView destinationTv;
        TextView dateTv;
        TextView userIDTv;
        TextView daysLeftTv;
    }
}
