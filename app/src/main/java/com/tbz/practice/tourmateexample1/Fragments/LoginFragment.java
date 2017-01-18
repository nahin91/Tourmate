package com.tbz.practice.tourmateexample1.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.AlertDialogManager;
import com.tbz.practice.tourmateexample1.LoginRegistrationActivity;
import com.tbz.practice.tourmateexample1.MainActivity;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.SessionManagement;
import com.tbz.practice.tourmateexample1.UserManager;
import com.tbz.practice.tourmateexample1.Users;

/**
 * Created by USER on 26-Dec-16.
 */

public class LoginFragment extends Fragment{
    TextView createRegTv;
    EditText emailEt;
    EditText passwordEt;
    Button btnLogin;
    SessionManagement session;
    AlertDialogManager alert = new AlertDialogManager();

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    //Users users;
    UserManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        createRegTv= (TextView) view.findViewById(R.id.createAccount);
        emailEt = (EditText) view.findViewById(R.id.input_email_Et);
        passwordEt = (EditText) view.findViewById(R.id.input_password_Et);
        btnLogin= (Button) view.findViewById(R.id.btnLogin);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Login Form");
        ((LoginRegistrationActivity)getActivity()).setSupportActionBar(toolbar);

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //users=new Users()
        manager=new UserManager(getContext().getApplicationContext());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String userName=emailEt.getText().toString();
                String userPass=passwordEt.getText().toString();

                if(userName.trim().length() > 0  &&  userPass.trim().length() > 0){
                    if(userName.equals("test")  &&  userPass.equals("test")){
                        Intent intent=new Intent(getContext().getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(getContext(),"Login Failed..","User Name/Password is incorrect",false);

                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(getContext(), "Login failed..", "Please enter username and password", false);

                }*/

                login();
            }
        });

        createRegTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                RegistrationFragment registrationFragment = new RegistrationFragment();
                ft.replace(R.id.fragmentContainer,registrationFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }

    public void login() {
        //Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

         btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");


        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        Users users=manager.getUser(email,password);

        if(users==null){
            alert.showAlertDialog(getContext(),"Login Failed..","No record in database",false);
        }else{
            String emailCheck = users.getEmail().toString();
            String passwordCheck=users.getPassword().toString();
            int userId=users.getId();

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("emailSh", emailCheck);
            editor.putString("passwordSh", passwordCheck);
            editor.commit();

            Intent intent=new Intent(getContext().getApplicationContext(), MainActivity.class);
            intent.putExtra("userid", userId);
            startActivity(intent);

            // TODO: Implement your own authentication logic here.
            /*if(email.trim().length() > 0  &&  password.trim().length() > 0){
                if( email.equals(emailCheck) &&  password.equals(passwordCheck) ){
                    //session.creteLoginSession("tabrez","sec.tabrez03@gmail.com");



                    Intent intent=new Intent(getContext().getApplicationContext(), MainActivity.class);
                    intent.putExtra("userid", userId);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getContext(), userId+"", Toast.LENGTH_SHORT).show();

                }else{
                    // username / password doesn't match
                    progressDialog.show();
                    alert.showAlertDialog(getContext(),"Login Failed..","email: "+emailCheck+"  pass: "+passwordCheck,false);
                }
            }else{
                // user didn't entered username or password
                // Show alert asking him to enter the details
                alert.showAlertDialog(getContext(), "Login failed..", "Please enter username and password", false);
            }*/
            // TODO: here my logic ends.
        }




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);

    }

    public void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEt.setError("enter a valid email address");
            valid = false;
        } else {
            emailEt.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEt.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEt.setError(null);
        }

        return valid;
    }

    public static interface MyListener{
        public void getData(String string);
    }
}
