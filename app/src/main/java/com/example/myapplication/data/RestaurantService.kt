package com.example.myapplication.data

import retrofit2.Call
import retrofit2.http.GET


interface RestaurantService {

    @GET("ShowRestaurants")
    fun listRepos(): Call<Model>
}
