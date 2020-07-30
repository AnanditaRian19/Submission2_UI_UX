package com.belajar.submissionuiux.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.belajar.submissionuiux.Adapter.ListUserAdapter;
import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.ViewModel.userViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private List<User> users = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private userViewModel userViewModel;
    private ListUserAdapter adapter = new ListUserAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Github Users");
        }

        setMainRecyclerView();
        userViewModel();
    }

    private void setMainRecyclerView() {
        adapter = new ListUserAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void userViewModel() {
        userViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(userViewModel.class);
        userViewModel.getmUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    adapter.setmUser(users);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
        });

        userViewModel.setData();
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