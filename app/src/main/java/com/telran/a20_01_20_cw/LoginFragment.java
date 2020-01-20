package com.telran.a20_01_20_cw;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

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

    private void showProgress(boolean show){
        progressFrame.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                new LoginTask().execute();
                break;
            case R.id.regBtn:
                new RegTask().execute();
                break;
        }
    }

    class RegTask extends AsyncTask<Void,Void,String>{
        String email, password;
        @Override
        protected void onPreExecute() {
            showProgress(true);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "Registration ok";
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();

//            String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
            Gson gson = new Gson();
            User user = new User();
            user.email = email;
            user.password = password;
            String json = gson.toJson(user);
            Log.d("MY_TAG", "doInBackground: " + json);
            RequestBody body = RequestBody.create(JSON,json);

            Request request = new Request.Builder()
                    .url("https://contacts-telran.herokuapp.com/api/registration")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String res = response.body().string();
                Log.d("MY_TAG", "doInBackground: " + res + " code: " + response.code());
            } catch (IOException e) {
                e.printStackTrace();
                result = "Connection error";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(false);
            Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    class LoginTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Login ok!";
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(false);
            Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}
