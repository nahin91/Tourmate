package com.tbz.practice.tourmateexample1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by USER on 09-Dec-16.
 */

public class AlertDialogManager {
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public void showAlertDialog(final Context context, String title, String message, Boolean status){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);  //builder class er help niye alertDialog k invoke kora lage
        builder.setTitle(title).setMessage(message);                    //builder  class  erpor  title,message,icon  aeshob ready kore day.
        if(status != null){
            builder.setIcon((status) ? R.drawable.success : R.drawable.fail);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }

    }


}
