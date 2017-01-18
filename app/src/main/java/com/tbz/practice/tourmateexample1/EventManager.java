package com.tbz.practice.tourmateexample1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by USER on 31-Dec-16.
 */

public class EventManager {

    Context context;
    Events events;
    UserDatabaseHelper helper;
    private SQLiteDatabase database;

    public EventManager(Context context) {
        helper=new UserDatabaseHelper(context);
        this.context=context;
    }

    private void openDatabase(){
        database=helper.getWritableDatabase();

    }

    private void closeDatabase(){
        //database.close();
        helper.close();
    }

    public boolean addEvent(Events events){
        this.openDatabase();

        ContentValues cv= new ContentValues();
        cv.put(UserDatabaseHelper.EVENT_COL_NAME,events.getEventName());
        cv.put(UserDatabaseHelper.EVENT_COL_START_PLACE,events.getStartPlace());
        cv.put(UserDatabaseHelper.EVENT_COL_DESTINATION,events.getDestination());
        cv.put(UserDatabaseHelper.EVENT_COL_DATE,events.getDate());
        cv.put(UserDatabaseHelper.EVENT_COL_BUDGET,events.getBudget());
        cv.put(UserDatabaseHelper.EVENT_COL_USER_ID,events.getUserId());


        long inserted = database.insert(UserDatabaseHelper.Event_TABLE,null,cv);
        this.closeDatabase();

        return (inserted>0);
    }

    public ArrayList<Events> getAllEvent(){
        ArrayList<Events> contactList = new ArrayList<>();
        this.openDatabase();

        Cursor cursor=database.query(UserDatabaseHelper.Event_TABLE,null,null,null,null,null,null);

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();

            for(int i=0; i<cursor.getCount();i++){
                int eventId = cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_ID));
                String eventName = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_NAME));
                String startingPLace = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_START_PLACE));
                String destination = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_DESTINATION));
                String date = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_DATE));
                String budget = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_BUDGET));
                int userId = cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_USER_ID));

                events = new Events(eventId,eventName,startingPLace,destination,date,budget,userId);
                contactList.add(events);
                cursor.moveToNext();
            }

        }
        this.closeDatabase();
        return  contactList;
    }

    public ArrayList<Events> getEvent(int id){
        ArrayList<Events> contactList = new ArrayList<>();
        this.openDatabase();

        Toast.makeText(context, "userid value is just pass to EvetnManager = "+id, Toast.LENGTH_SHORT).show();
        Cursor cursor=database.query(UserDatabaseHelper.Event_TABLE,new String[]{UserDatabaseHelper.EVENT_COL_ID,UserDatabaseHelper.EVENT_COL_NAME,
                                        UserDatabaseHelper.EVENT_COL_START_PLACE,UserDatabaseHelper.EVENT_COL_DESTINATION,UserDatabaseHelper.EVENT_COL_DATE,
                                        UserDatabaseHelper.EVENT_COL_BUDGET,UserDatabaseHelper.EVENT_COL_USER_ID},UserDatabaseHelper.EVENT_COL_USER_ID+" LIKE "+id,null,null,null,
                                        UserDatabaseHelper.EVENT_COL_DATE,null);
        //Cursor cursor=database.rawQuery("SELECT * FROM "+UserDatabaseHelper.Event_TABLE+" WHERE id LIKE "+"'"+id+"'",null);
        /*Cursor cursor=database.query(UserDatabaseHelper.Event_TABLE,null,null,null,null,null,null);*/

        Toast.makeText(context, cursor.getCount()+" is the db ammount", Toast.LENGTH_SHORT).show();

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();

            for(int i=1; i<=cursor.getCount();i++){

                int eventId = cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_ID));
                String eventName = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_NAME));
                String startingPLace = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_START_PLACE));
                String destination = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_DESTINATION));
                String date = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_DATE));
                String budget = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_BUDGET));
                int userId = cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.EVENT_COL_USER_ID));

                events = new Events(eventId,eventName,startingPLace,destination,date,budget,userId);
                contactList.add(events);
                cursor.moveToNext();
            }
        }

        this.closeDatabase();

        return  contactList;

    }

    public boolean updateBudget(int id,Events events){
        this.openDatabase();

        ContentValues cv = new ContentValues();

        cv.put(UserDatabaseHelper.EVENT_COL_BUDGET,events.getBudget());


        int updated = database.update(UserDatabaseHelper.Event_TABLE,cv,UserDatabaseHelper.EVENT_COL_ID + " = "+ id,null);

        this.closeDatabase();
        return (updated > 0);
    }

    public boolean updateEvent(int id,Events events){
        this.openDatabase();

        ContentValues cv = new ContentValues();

        cv.put(UserDatabaseHelper.EVENT_COL_NAME,events.getEventName());
        cv.put(UserDatabaseHelper.EVENT_COL_START_PLACE,events.getStartPlace());
        cv.put(UserDatabaseHelper.EVENT_COL_DESTINATION,events.getDestination());
        cv.put(UserDatabaseHelper.EVENT_COL_DATE,events.getDate());
        cv.put(UserDatabaseHelper.EVENT_COL_BUDGET,events.getBudget());

        int updated = database.update(UserDatabaseHelper.Event_TABLE,cv,UserDatabaseHelper.EVENT_COL_ID + " = "+ id,null);

        this.closeDatabase();
        return (updated > 0);
    }

    public boolean deleteEvent(int event_id){
        this.openDatabase();

        int deleted = database.delete(UserDatabaseHelper.Event_TABLE, UserDatabaseHelper.EVENT_COL_ID+" = "+event_id,null);

        this.closeDatabase();

        return (deleted>0);

    }


}
