package com.tbz.practice.tourmateexample1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by USER on 14-Jan-17.
 */

public class ExpenditureManager  {
    Context context;
    Expenditures expenditures;
    UserDatabaseHelper helper;
    private SQLiteDatabase database;

    public ExpenditureManager(Context context) {
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

    public boolean addExpenditure(Expenditures expenditures){
        this.openDatabase();

        ContentValues cv= new ContentValues();
        cv.put(UserDatabaseHelper.EXP_COL_TITLE,expenditures.getTitle());
        cv.put(UserDatabaseHelper.EXP_COL_COST,expenditures.getCost());
        cv.put(UserDatabaseHelper.EXP_COL_DATE_TIME,expenditures.getDate());
        cv.put(UserDatabaseHelper.EXP_COL_EVENT_ID,expenditures.getEvent_id());



        long inserted = database.insert(UserDatabaseHelper.EXPENDITURE_TABLE,null,cv);
        this.closeDatabase();

        return (inserted>0);
    }

    public ArrayList<Expenditures> getExpenditures(int event_id){
        ArrayList<Expenditures> expList = new ArrayList<>();
        this.openDatabase();

        Toast.makeText(context, "eventid value is just pass to EvetnManager = "+event_id, Toast.LENGTH_SHORT).show();

        Cursor cursor=database.query(UserDatabaseHelper.EXPENDITURE_TABLE,new String[]{UserDatabaseHelper.EXP_COL_ID,UserDatabaseHelper.EXP_COL_TITLE,
                        UserDatabaseHelper.EXP_COL_COST,UserDatabaseHelper.EXP_COL_DATE_TIME,UserDatabaseHelper.EXP_COL_EVENT_ID},UserDatabaseHelper.EXP_COL_EVENT_ID+" LIKE "+event_id,null,null,null,
                UserDatabaseHelper.EXP_COL_DATE_TIME,null);
        //Cursor cursor=database.rawQuery("SELECT * FROM "+UserDatabaseHelper.Event_TABLE+" WHERE id LIKE "+"'"+id+"'",null);
        /*Cursor cursor=database.query(UserDatabaseHelper.Event_TABLE,null,null,null,null,null,null);*/

        //Toast.makeText(context, cursor.getCount()+" is the db ammount", Toast.LENGTH_SHORT).show();

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();

            for(int i=1; i<=cursor.getCount();i++){

                int expId = cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.EXP_COL_ID));
                String expTitle = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EXP_COL_TITLE));
                String expCost = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EXP_COL_COST));
                String expDateTime = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.EXP_COL_DATE_TIME));
                int eventId = cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.EXP_COL_EVENT_ID));

                expenditures = new Expenditures(expId,expTitle,expCost,expDateTime,eventId);
                expList.add(expenditures);
                cursor.moveToNext();
            }
        }

        this.closeDatabase();
        return  expList;
    }

    public boolean deleteExpenditures(int event_id){
        this.openDatabase();

        int deleted = database.delete(UserDatabaseHelper.EXPENDITURE_TABLE, UserDatabaseHelper.EXP_COL_EVENT_ID+" = "+event_id,null);

        this.closeDatabase();

        return (deleted>0);

    }

}
