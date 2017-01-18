package com.tbz.practice.tourmateexample1.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.EventManager;
import com.tbz.practice.tourmateexample1.Events;
import com.tbz.practice.tourmateexample1.MainActivity;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.UserManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by USER on 30-Dec-16.
 */

public class EventCreation extends Fragment {
    private Button btnAddEvent;
    private Button btnCancle;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    private EditText eventNameEt;
    private EditText startingPlaceEt;
    private EditText destinationEt;
    private EditText dateEt;
    private EditText budgetEt;

    private Events events;
    private EventManager manager;
    private int userid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_event,container,false);

        btnAddEvent= (Button) rootView.findViewById(R.id.btn_AddEvent);
        btnCancle= (Button) rootView.findViewById(R.id.btn_CancleEvent);
        eventNameEt= (EditText) rootView.findViewById(R.id.input_EventName);
        startingPlaceEt= (EditText) rootView.findViewById(R.id.input_StartPlace);
        destinationEt= (EditText) rootView.findViewById(R.id.input_Destination);
        dateEt= (EditText) rootView.findViewById(R.id.input_Date);
        budgetEt= (EditText) rootView.findViewById(R.id.input_Budget);

        manager=new EventManager(getContext().getApplicationContext());

        myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.DATE, -1);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                EventListFragment listFragment = new EventListFragment();
                ft.replace(R.id.eventFragmentContainer,listFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });





        return rootView;
    }

    private void createEvent() {
        MainActivity mainActivity = (MainActivity) getActivity();      //login er shathe shathe jei id diye dhuklam shetar value ai fragment a niye ashlam
        userid = mainActivity.getIntent().getExtras().getInt("userid");
        //Toast.makeText(getContext().getApplicationContext(), userid+"", Toast.LENGTH_SHORT).show();


        String eventName=eventNameEt.getText().toString();
        String startingPLace=startingPlaceEt.getText().toString();
        String destination=destinationEt.getText().toString();
        String date=dateEt.getText().toString();
        String budget=budgetEt.getText().toString();
        int userId=userid;

        events=new Events(eventName,startingPLace,destination,date,budget,userId);
        boolean inserted= manager.addEvent(events);
        if(inserted){
            Toast.makeText(getContext().getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            EventListFragment listFragment = new EventListFragment();
            ft.replace(R.id.eventFragmentContainer,listFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }else {
            Toast.makeText(getContext().getApplicationContext(), "Successfylly Failed To Save", Toast.LENGTH_SHORT).show();
        }




    }

    private void updateLabel() {

        String myFormat = "EEE, d MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEt.setText(sdf.format(myCalendar.getTime()));
    }
}
