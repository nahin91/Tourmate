package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 28-Dec-16.
 */

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tour mate";
    public static final String USER_TABLE = "users";
    public static final String Event_TABLE = "events";
    public static final String EXPENDITURE_TABLE = "expenditures";
    public static final int DATABASE_VERSION = 3;
    Context context;

    //this is user table columns
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_MOBILE = "mobile";

    //this is event table columns
    public static final String EVENT_COL_ID = "id";
    public static final String EVENT_COL_NAME = "name";
    public static final String EVENT_COL_START_PLACE = "start_place";
    public static final String EVENT_COL_DESTINATION = "destination";
    public static final String EVENT_COL_DATE = "date";
    public static final String EVENT_COL_BUDGET = "budget";
    public static final String EVENT_COL_USER_ID = "user_id";

    //this is expenditure table column
/*    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());*/

    public static final String EXP_COL_ID = "id";
    public static final String EXP_COL_TITLE = "title";
    public static final String EXP_COL_COST = "cost";
    public static final String EXP_COL_DATE_TIME = "time";
    public static final String EXP_COL_EVENT_ID = "event_id";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE
            + " ( " + COL_ID + " INTEGER PRIMARY KEY," + COL_NAME + " TEXT," + COL_EMAIL + " TEXT UNIQUE," + COL_PASSWORD
            + " TEXT, " + COL_MOBILE + " TEXT " + ")";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + Event_TABLE
            + " ( " + EVENT_COL_ID + " INTEGER PRIMARY KEY," + EVENT_COL_NAME + " TEXT," + EVENT_COL_START_PLACE + " TEXT," + EVENT_COL_DESTINATION
            + " TEXT, " + EVENT_COL_DATE + " TEXT, " + EVENT_COL_BUDGET + " TEXT, "+ EVENT_COL_USER_ID + " INTEGER " + ")";

    private static final String CREATE_EXPENDITURE_TABLE = "CREATE TABLE " + EXPENDITURE_TABLE
            + " ( " + EXP_COL_ID + " INTEGER PRIMARY KEY," + EXP_COL_TITLE + " TEXT," + EXP_COL_COST + " TEXT," + EXP_COL_DATE_TIME
            + " TEXT, " + EXP_COL_EVENT_ID + " INTEGER " + ")";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
        sqLiteDatabase.execSQL(CREATE_EXPENDITURE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int newVersion, int oldVersion) {

    }
}
