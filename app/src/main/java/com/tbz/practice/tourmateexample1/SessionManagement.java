package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by USER on 09-Dec-16.
 */

public class SessionManagement {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SessionManagement session;
    AlertDialogManager alert = new AlertDialogManager();
    private static  final String PREF_NAME="MyPreferedReference";
    private static  final String IS_LOGIN="IsLoggedIn";
    public static  final String KEY_NAME="name";
    public static  final String KEY_MAIL="email";

    public SessionManagement(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    //This function simply stores login status(true), name, email in shared preferences.
    public void creteLoginSession(String name, String email){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_MAIL,email);
        editor.commit();
    }

    //The following function will read shared preferences and returns user data in HashMap
    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        user.put(KEY_NAME,preferences.getString(KEY_NAME,null));
        user.put(KEY_MAIL,preferences.getString(KEY_MAIL,null));
        return user;
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN,false);
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginRegistrationActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginRegistrationActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }



}
