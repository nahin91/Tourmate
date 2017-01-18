package com.tbz.practice.tourmateexample1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by USER on 30-Dec-16.
 */

public class UserManager {
    private Users users;
    private UserDatabaseHelper helper;
    private SQLiteDatabase database;

    public UserManager(Context context) {
        helper=new UserDatabaseHelper(context);
    }

    private void openDatabase(){
        database=helper.getWritableDatabase();

    }

    private void closeDatabase(){
        //database.close();
        helper.close();
    }

    public boolean addUser(Users users){
        this.openDatabase();

        ContentValues cv= new ContentValues();
        cv.put(UserDatabaseHelper.COL_NAME,users.getName());
        cv.put(UserDatabaseHelper.COL_EMAIL,users.getEmail());
        cv.put(UserDatabaseHelper.COL_PASSWORD,users.getPassword());
        cv.put(UserDatabaseHelper.COL_MOBILE,users.getMobile());

        long inserted = database.insert(UserDatabaseHelper.USER_TABLE,null,cv);
        this.closeDatabase();

        return (inserted>0);
    }

    public Users getUser(String emailCheck,String passwordCheck){
        this.openDatabase();

        Users users;
        Cursor cursor=database.query(UserDatabaseHelper.USER_TABLE,new String[]{UserDatabaseHelper.COL_ID,UserDatabaseHelper.COL_NAME,
                UserDatabaseHelper.COL_EMAIL,UserDatabaseHelper.COL_PASSWORD,UserDatabaseHelper.COL_MOBILE},
                UserDatabaseHelper.COL_EMAIL+" LIKE "+"'"+emailCheck+"' AND "+UserDatabaseHelper.COL_PASSWORD+" LIKE "+"'"+passwordCheck+"'",
                null,null,null,null);

        cursor.moveToFirst();

        int userId=cursor.getInt(cursor.getColumnIndex(UserDatabaseHelper.COL_ID));
        //String name=cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL_NAME));
        String email=cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL_EMAIL));
        String password=cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL_PASSWORD));
        //String mobileNo=cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL_MOBILE));

        users=new Users(userId,email,password);  // ekhane "id"  aikarone dorkar j, user er login successfull hole oi ID er reference a kaj korbe.
        this.closeDatabase();
        return users;

    }
}
