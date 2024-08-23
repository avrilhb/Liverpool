package com.example.liverpooltest.model;

import com.example.liverpooltest.contract.SearchContract;
import com.example.liverpooltest.entity.Record;
import com.example.liverpooltest.entity.Result;
import com.example.liverpooltest.network.ApiClient;
import com.example.liverpooltest.network.ApiInterface;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchModel implements SearchContract.Model {
    @Override
    public void getSearch(final OnFinishedListener onFinishedListener, String query, int pageNumber) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Result> call = apiService.getSearch(query, pageNumber);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                ArrayList<Record> results = response.body().getPlpResults().records;
                onFinishedListener.onFinished(results);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getMinSortPriceSearch(OnFinishedListener onFinishedListener, String query, int pageNumber, int minSortPrice) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Result> call = apiService.getSearchMinSortPrice(query, pageNumber, minSortPrice);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                ArrayList<Record> results = response.body().getPlpResults().records;
                onFinishedListener.onFinished(results);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
