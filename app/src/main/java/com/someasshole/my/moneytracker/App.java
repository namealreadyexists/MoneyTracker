package com.someasshole.my.moneytracker;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static final String PREFS_NAME = "shared_preferences";
    private static final String KEY_TOKEN = "auth_token";

    private static final String TAG = App.class.getSimpleName();
    private Api mApi;

    @Override
    public void onCreate(){
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ?HttpLoggingInterceptor.Level.BODY
                :HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor())
                .build();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd.MM.yyyy HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        mApi = retrofit.create(Api.class);
    }
    public Api getApi(){
        return mApi;
    }


    protected void saveAuthToken(String token){
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(KEY_TOKEN,token)
                .apply();
    }

    private String getAuthToken() {
        return getSharedPreferences(PREFS_NAME,MODE_PRIVATE).getString(KEY_TOKEN,null);
    }

    public boolean isAuthorized(){
        return !TextUtils.isEmpty(getAuthToken());
    }

    private class AuthInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException{
            Request request = chain.request();
            HttpUrl url = request.url();

            HttpUrl.Builder urlBuilder = url.newBuilder();
            HttpUrl newUrl = urlBuilder.addQueryParameter("auth-token",getAuthToken()).build();

            Request.Builder requestBuilder = request.newBuilder();
            Request newRequest = requestBuilder.url(newUrl).build();

            return chain.proceed(newRequest);
        }
    }
}
