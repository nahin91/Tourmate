package com.tbz.practice.tourmateexample1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.EventAdaptor;
import com.tbz.practice.tourmateexample1.EventManager;
import com.tbz.practice.tourmateexample1.Events;
import com.tbz.practice.tourmateexample1.MainActivity;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.forecastResponse.List;

import java.util.ArrayList;

/**
 * Created by USER on 30-Dec-16.
 */

public class EventListFragment extends Fragment {
    ListView listView;

    Events events;
    EventManager manager;
    EventAdaptor adaptor;
    ArrayList<Events> eventList;
    public static int userId;
    MainActivity mainActivity;

    static final String EVENT_POSITION="pos";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_list_event,container,false);

        listView= (ListView) rootView.findViewById(R.id.listview_event);

        manager=new EventManager(getContext());
        eventList=new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //FragmentManager fm = getFragmentManager();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                EventCreation creation = new EventCreation();
                ft.replace(R.id.eventFragmentContainer,creation);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                ft.commit();
            }
        });

        mainActivity = (MainActivity) getActivity();      //login er shathe shathe jei id diye dhuklam shetar value ai fragment a niye ashlam
        if(mainActivity.getIntent().getExtras().getInt("userid") > 0 ){
            EventListFragment.userId = mainActivity.getIntent().getExtras().getInt("userid");
            //Toast.makeText(getContext().getApplicationContext(), "mainactivity has data ", Toast.LENGTH_SHORT).show();
        }


        eventList=manager.getEvent(userId);
        //eventList=manager.getAllEvent();
        adaptor=new EventAdaptor(getContext(),eventList);

        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startActivity(new Intent(MainActivity.this,ContactDetailsActivity.class));
                EventDetails eventDetails=new EventDetails();
                Log.e("Click","Click");

                Bundle bundle = new Bundle();
                bundle.putInt(EVENT_POSITION,position);
                bundle.putString("EVENT_NAME",eventList.get(position).getEventName());
                bundle.putString("STARTING_PLACE",eventList.get(position).getStartPlace());
                bundle.putString("DESTINATION",eventList.get(position).getDestination());
                bundle.putString("DATE",eventList.get(position).getDate());
                bundle.putString("BUDGET",eventList.get(position).getBudget());
                bundle.putInt("USER_ID",eventList.get(position).getUserId());
                bundle.putInt("EVENT_ID",eventList.get(position).getId());

                eventDetails.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.eventFragmentContainer,eventDetails).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

                /*FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                EventDetails eventDetails1 = new EventDetails();
                ft.replace(R.id.eventFragmentContainer,eventDetails1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();*/

                /*Intent intent = new Intent(getContext().getApplicationContext(),EventDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);*/
            }
        });


        return rootView;
    }
}
