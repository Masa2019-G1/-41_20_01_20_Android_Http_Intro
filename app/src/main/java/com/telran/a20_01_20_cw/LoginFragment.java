package com.telran.a20_01_20_cw;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.telran.a20_01_20_cw.dto.UserDto;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText inputEmail, inputPassword;
    Button regBtn, loginBtn;
    ViewGroup progressFrame;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        regBtn = view.findViewById(R.id.regBtn);
        loginBtn = view.findViewById(R.id.loginBtn);
        progressFrame = view.findViewById(R.id.progressFrame);

        regBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        progressFrame.setOnClickListener(null);
        return view;
    }

    private void showProgress(boolean show) {
        progressFrame.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void showError(String msg) {
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog1, which) -> {
                    Toast.makeText(requireContext(), "Ok Clicked", Toast.LENGTH_SHORT).show();
                })
                .create();
        dialog.show();
    }


    private void showNextView() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.root,new ListFragment())
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                new LoginTask().execute();
                break;
            case R.id.regBtn:
                new RegTask().execute();
                break;
        }
    }

    class RegTask extends AsyncTask<Void, Void, String> {
        String email, password;
        boolean isSuccess = true;

        @Override
        protected void onPreExecute() {
            showProgress(true);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "Registration ok";
            try {
                String token = HttpProvider
                        .getInstance()
                        .registration(email, password);
                StoreProvider.getInstance().saveToken(token);
            } catch (IOException e) {
                isSuccess = false;
                result = "Connection error! Check your internet!";
            } catch (RuntimeException e){
                isSuccess = false;
                result = e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(false);
            if(!isSuccess){
                showError(s);
            }else {
                showNextView();
            }
        }
    }

    class LoginTask extends AsyncTask<Void, Void, String> {
        String email, password;
        boolean isSuccess = true;
        @Override
        protected void onPreExecute() {
            showProgress(true);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "Login OK!";
            try {
                String token = HttpProvider
                        .getInstance()
                        .login(email,password);
                StoreProvider.getInstance().saveToken(token);
            } catch (IOException e) {
                isSuccess = false;
                result = "Connection error!Check your internet";
            }catch (RuntimeException e){
                isSuccess = false;
                result = e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(false);
            if(!isSuccess){
                showError(s);
            }else {
                showNextView();
            }
        }
    }
}
