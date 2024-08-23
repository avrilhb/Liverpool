package com.example.liverpooltest.contract;

import com.example.liverpooltest.entity.Record;
import java.util.List;

public interface SearchContract {

    interface View {
        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Record> recordsArrayList);

        void onResponseFailure(Throwable throwable);
        void closeKeyboard();
    }

    interface Presenter {
        void requestDataFromServer(String query, int pageNumber);
        void requestMinSortPriceSearch( String query, int pageNumber, int minSortPrice);
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(List<Record> recordsArrayList);

            void onFailure(Throwable t);
        }
        void getSearch(OnFinishedListener onFinishedListener, String query, int pageNumber);
        void getMinSortPriceSearch(OnFinishedListener onFinishedListener, String query, int pageNumber, int minSortPrice);
    }
}
