package com.example.liverpooltest.presenter;

import com.example.liverpooltest.contract.SearchContract;
import com.example.liverpooltest.entity.Record;
import com.example.liverpooltest.model.SearchModel;
import java.util.List;

public class SearchPresenter implements SearchContract.Presenter, SearchContract.Model.OnFinishedListener {

    private SearchContract.View searchView;
    private SearchContract.Model searchModel;

    public SearchPresenter(SearchContract.View searchView) {
        this.searchView = searchView;
        this.searchModel = new SearchModel();
    }

    @Override
    public void requestDataFromServer(String query, int pageNumber) {
        if (searchView != null) {
            searchView.showProgress();
        }
        searchModel.getSearch(this, query, pageNumber);
    }

    @Override
    public void requestMinSortPriceSearch(String query, int pageNumber, int minSortPrice) {
        if (searchView != null) {
            searchView.showProgress();
        }
        searchModel.getMinSortPriceSearch(this, query, pageNumber, minSortPrice);
    }

    @Override
    public void onFinished(List<Record> recordsArrayList) {
        searchView.setDataToRecyclerView(recordsArrayList);
        if (searchView != null) {
            searchView.hideProgress();
            //searchView.closeKeyboard();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        searchView.onResponseFailure(t);
        if (searchView != null) {
            searchView.hideProgress();
            searchView.closeKeyboard();
        }
    }
}
