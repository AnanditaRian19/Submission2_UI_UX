package com.belajar.submissionuiux.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.belajar.submissionuiux.Adapter.ListUserAdapter;
import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.Network.ApiService;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.Network.ServiceGenerator;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.ViewModel.MainViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private List<User> users = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private MainViewModel mainViewModel;
    private ListUserAdapter adapter = new ListUserAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        setRecyclerView();
//        getDataUser();

//        ListUserAdapter adapter = new ListUserAdapter();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListUserAdapter();
//        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.getmUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    adapter.setmUser(users);
                }
            }
        });

        mainViewModel.setData();
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

//        mainViewModel.setData();
//        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        mainViewModel.getData();
//        mainViewModel.mUser.observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                adapter.
//            }
//        });
    }

    private void setRecyclerView() {
//        ListUserAdapter listUserAdapter = new ListUserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//        recyclerView.smoothScrollToPosition(0);
//        RecyclerView.Adapter mAdapter = new ListUserAdapter(context, users);
//        recyclerView.setAdapter(mAdapter);
    }

    private void getDataUser() {
        ApiService apiService = ServiceGenerator.getConnection()
                .create(ApiService.class);
        Call<List<User>> call = apiService.getUser(Const.TOKEN);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {

//                ListUserAdapter listUserAdapter = new ListUserAdapter(MainActivity.this, response.body());
//                recyclerView.setAdapter(listUserAdapter);
//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                Log.e("onFailure: ", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView mSearchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            mSearchView.setQueryHint(getResources().getString(R.string.enter_name));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.changeLanguage) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        }
        return true;
    }
}