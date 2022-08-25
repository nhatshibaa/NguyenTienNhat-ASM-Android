package com.example.filmmoi.repository;

import com.example.filmmoi.entity.Item;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {
    String SERVER = "https://springfilm.herokuapp.com";

    @GET("/apifree/home")
    Call<Item> apiGetData();
}
