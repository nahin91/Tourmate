package com.tbz.practice.tourmateexample1.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.tbz.practice.tourmateexample1.UserManager;
import com.tbz.practice.tourmateexample1.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    TextView toRegTv;
    EditText nameEt;
    EditText emailEt;
    EditText mobileEt;
    EditText passwordEt;
    EditText reEnterPasswordEt;
    Button btnReg;

    Users users;
    UserManager manager;
    AlertDialogManager alert = new AlertDialogManager();


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Registration Form");
        ((LoginRegistrationActivity)getActivity()).setSupportActionBar(toolbar);

        toRegTv= (TextView) view.findViewById(R.id.gotoLogin);
        manager=new UserManager(getContext().getApplicationContext());
        nameEt= (EditText) view.findViewById(R.id.input_name);
        emailEt= (EditText) view.findViewById(R.id.input_email);
        mobileEt= (EditText) view.findViewById(R.id.input_mobile);
        passwordEt= (EditText) view.findViewById(R.id.input_password);
        reEnterPasswordEt= (EditText) view.findViewById(R.id.input_reEnterPassword);
        btnReg= (Button) view.findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        toRegTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                ft.replace(R.id.fragmentContainer,loginFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }

    private void signUp() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnReg.setEnabled(false);

        /*final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();*/

        String name = nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String mobile = mobileEt.getText().toString();
        String password = passwordEt.getText().toString();
        String reEnterPassword = reEnterPasswordEt.getText().toString();


        // TODO: Implement your own signup logic here.
        users=new Users(name,email,password,mobile);
        boolean inserted= manager.addUser(users);
        if(inserted){
            //Toast.makeText(getContext().getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
            alert.showAlertDialog(getContext(), "", "You have successfully created an Account", false);
            /*Intent intent=new Intent(getContext(), MainActivity.class);
            startActivity(intent);*/
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            ft.replace(R.id.fragmentContainer,loginFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else
            //Toast.makeText(getContext().getApplicationContext(), "Successfylly Failed To Save", Toast.LENGTH_SHORT).show();
            alert.showAlertDialog(getContext(), "Failed...", "Account could not be created.", false);
        //TODO: ends here

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();

                        //progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        btnReg.setEnabled(true);
        //Toast.makeText(getContext(), "Data saved from onSignupSuccess", Toast.LENGTH_LONG).show();

    }

    public void onSignupFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnReg.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String mobile = mobileEt.getText().toString();
        String password = passwordEt.getText().toString();
        String reEnterPassword = reEnterPasswordEt.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameEt.setError("at least 3 characters");
            valid = false;
        } else {
            nameEt.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEt.setError("enter a valid email address");
            valid = false;
        } else {
            emailEt.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()>20) {
            mobileEt.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileEt.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEt.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEt.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordEt.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordEt.setError(null);
        }

        return valid;
    }

}
