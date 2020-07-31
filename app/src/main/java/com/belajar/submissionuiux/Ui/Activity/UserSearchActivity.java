package com.belajar.submissionuiux.Ui.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.submissionuiux.Adapter.ListUserSearchAdapter;
import com.belajar.submissionuiux.Model.SearchUser;
import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.ViewModel.UserSearchViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class UserSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserSearchViewModel userSearchViewModel;
    private Context context;
    private ImageView ivImage, ivNotFound;
    private AVLoadingIndicatorView loading;

    private List<User> users = new ArrayList<>();
    private ListUserSearchAdapter listUserSearchAdapter;
    public static final String TAG = UserSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        Log.i(TAG, "onCreate");

        recyclerView = findViewById(R.id.recyclerView);
        ivNotFound = findViewById(R.id.ivNotFound);
        ivImage = findViewById(R.id.ivImage);
        loading = findViewById(R.id.loading);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Github Users");
        }

        setRecyclerView();
        userSearchViewMode();

    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        listUserSearchAdapter = new ListUserSearchAdapter(this, users);
        recyclerView.setAdapter(listUserSearchAdapter);
        listUserSearchAdapter.notifyDataSetChanged();
    }

    private void userSearchViewMode() {
        userSearchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(UserSearchViewModel.class);
        userSearchViewModel.getSearchUser().observe(this, new Observer<SearchUser>() {
            @Override
            public void onChanged(SearchUser searchUser) {
                Log.i(TAG, "User Search View Model");

                if (searchUser.getTotalCount() > 0) {
                    listUserSearchAdapter.setData(searchUser.getItems());

                    ivImage.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    ivNotFound.setVisibility(View.GONE);
                } else {
                    listUserSearchAdapter.clearList(searchUser.getItems());

                    loading.setVisibility(View.GONE);
                    ivNotFound.setVisibility(View.VISIBLE);
                }
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
            mSearchView.setQuery("", false);
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    loading.setVisibility(View.VISIBLE);
                    ivImage.setVisibility(View.GONE);
                    mSearchView.setVisibility(View.INVISIBLE);
                    mSearchView.setVisibility(View.VISIBLE);

                    userSearchViewModel.setUserSearch(query);
                    Toast.makeText(UserSearchActivity.this, R.string.toast_searching, Toast.LENGTH_SHORT).show();

                    return true;
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