package com.example.liverpooltest.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.liverpooltest.R;
import com.example.liverpooltest.adapter.SearchAdapter;
import com.example.liverpooltest.contract.SearchContract;
import com.example.liverpooltest.entity.Record;
import com.example.liverpooltest.presenter.SearchPresenter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchContract.View {
    private SearchPresenter presenter;
    private RecyclerView searchRecycler;
    private SearchAdapter searchAdapter;
    private SearchView searchView;
    private ProgressBar progressBar;
    private TextView emptySeachTV;
    private int pageNumber = 1;
    //Constants for load more
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private  LinearLayoutManager layoutManager;
    private ImageView searchCloseBtn;
    private EditText searchEditText;
    private String searchQuery = "";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize Toolbar
        //Initialize UI
        initUI();
        //Initialize presenter
        presenter = new SearchPresenter(this);
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        pageNumber = 1;
        if (item.getItemId() == R.id.predefined_search && searchQuery != ""){
            presenter.requestDataFromServer(searchQuery, pageNumber);
        } else if (item.getItemId() == R.id.lower_price_search  && searchQuery != "") {
            presenter.requestMinSortPriceSearch(searchQuery, pageNumber, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        searchRecycler = findViewById(R.id.search_recycler);
        emptySeachTV = findViewById(R.id.empty_search_tv);
        progressBar = findViewById(R.id.main_progress);
        layoutManager = new LinearLayoutManager(this);
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setNestedScrollingEnabled(false);
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty() && newText != "") {
                    presenter.requestDataFromServer(newText, pageNumber);
                    searchQuery = newText;
                }
                return false;
            }

        });
        // Get the search close button image view
        int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        searchCloseBtn = (ImageView) searchView.findViewById(searchCloseButtonId);
        int searchCloseEditTextId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        searchEditText = (EditText) searchView.findViewById(searchCloseEditTextId);
        searchCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                searchView.setQuery("", false);
                searchRecycler.setVisibility(View.GONE);
                pageNumber = 1;
                searchQuery = "";
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<Record> recordsArrayList) {
        if (!recordsArrayList.isEmpty() && recordsArrayList.size() != 0) {
            searchRecycler.setVisibility(View.VISIBLE);
            searchAdapter = new SearchAdapter((ArrayList<Record>) recordsArrayList, this);
            searchRecycler.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    private void setListeners() {
        searchRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = searchRecycler.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    pageNumber++;
                    presenter.requestDataFromServer(searchQuery, pageNumber);
                    loading = true;
                }
            }
        });
    }
}