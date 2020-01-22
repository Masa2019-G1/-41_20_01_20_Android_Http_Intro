package com.telran.a20_01_20_cw;

import android.util.Log;

import com.google.gson.Gson;
import com.telran.a20_01_20_cw.dto.AuthDto;
import com.telran.a20_01_20_cw.dto.ContactDto;
import com.telran.a20_01_20_cw.dto.ContactListDto;
import com.telran.a20_01_20_cw.dto.ErrorDto;
import com.telran.a20_01_20_cw.dto.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpProvider {
    private static final String BASE_URL = "https://contacts-telran.herokuapp.com";
    private static final HttpProvider ourInstance = new HttpProvider();
    private Gson gson;
    private MediaType JSON;
    private OkHttpClient client;

    public static HttpProvider getInstance() {
        return ourInstance;
    }

    private HttpProvider() {
        gson = new Gson();
        JSON = MediaType.get("application/json; charset=utf-8");
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();
    }

    public String registration(String email, String password) throws IOException {
        UserDto user = new UserDto(email,password);
        String json = gson.toJson(user);

        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            String jsonResponse = response.body().string();
            AuthDto auth = gson.fromJson(jsonResponse,AuthDto.class);
            return auth.getToken();
        }else if(response.code() == 400 || response.code() == 409){
            String jsonResponse = response.body().string();
            ErrorDto error = gson.fromJson(jsonResponse,ErrorDto.class);
            throw new RuntimeException(error.getMessage());
        }else{
            String jsonResponse = response.body().string();
            Log.d("MY_TAG", "registration: " + jsonResponse);
            throw new RuntimeException("Server error! Call to support");
        }
    }

    public String login(String email, String password) throws IOException {
        UserDto user = new UserDto(email,password);
        String json = gson.toJson(user);

        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            String jsonResponse = response.body().string();
            AuthDto auth = gson.fromJson(jsonResponse,AuthDto.class);
            return auth.getToken();
        }else if(response.code() == 400 || response.code() == 401){
            String jsonResponse = response.body().string();
            ErrorDto error = gson.fromJson(jsonResponse,ErrorDto.class);
            throw new RuntimeException(error.getMessage());
        }else{
            String jsonResponse = response.body().string();
            Log.d("MY_TAG", "login: " + jsonResponse);
            throw new RuntimeException("Server error! Call to support");
        }
    }

    public List<ContactDto> getAllContacts(String token) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/contact")
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            String json = response.body().string();
            ContactListDto dto = gson.fromJson(json,ContactListDto.class);
            return dto.getContacts();
        }else if(response.code() == 401){
            String json = response.body().string();
            ErrorDto error = gson.fromJson(json,ErrorDto.class);
            throw new RuntimeException(error.getMessage());
        }else{
            String json = response.body().string();
            Log.d("MY_TAG", "getAllContacts: " + json);
            throw new RuntimeException("Server error!Call to support");
        }
    }
}
