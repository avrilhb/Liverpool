package com.example.liverpooltest.network;

import com.example.liverpooltest.entity.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/appclienteservices/services/v3/plp")
    Call<Result> getSearch
            (@Query("search-string") String searchString,
             @Query("page-number") int pageNumber);

    @GET("/appclienteservices/services/v3/plp")
    Call<Result> getSearchMinSortPrice
            (@Query("search-string") String searchString,
             @Query("page-number") int pageNumber,
             @Query("minSortPrice") int sortPrice);
}
