package com.example.myapplication.data;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class AppNetworkSource {
    private RestaurantService webService;
    // mutable list which contains values from network source
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppNetworkSource sInstance;
    private final MutableLiveData<List<Restaurant>> loadedRestaurants;



    private Retrofit retrofit;

    private AppNetworkSource() {
        if(retrofit == null) {
            retrofit = getRetrofit();
        }

        webService = retrofit.create(RestaurantService.class);
        loadedRestaurants = new MutableLiveData<>();

    }

    /**
     * Get the singleton for this class
     */
    public static AppNetworkSource getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppNetworkSource();
            }
        }
        return sInstance;
    }

    private Retrofit getRetrofit() {
        // Add all interceptors you want (headers, URL, logging, stetho logs)
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String BASE_URL = "http://10.0.2.52:8081/";
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                // Add your adapter factory to handler Errors
                .client(httpClient.build())
                .build();
    }

    public MutableLiveData<List<Restaurant>> getLoadedRestaurants() {
        return loadedRestaurants;
    }

    public void loadRestaurants() {
        Call<Model> resResponse = webService.listRepos();
        resResponse.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    // posting value to the live data
                    Log.d("myTag","on response " + response.body().getRestaurant().size() );
                    loadedRestaurants.postValue(response.body().getRestaurant());

                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("myTag","onFailure called inside retrofit loading");
                if (t != null)
                    Log.e("myTag", t.getMessage());
            }
        });

    }


}
