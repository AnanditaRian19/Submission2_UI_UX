package com.belajar.submissionuiux.Ui.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belajar.submissionuiux.Adapter.ListUserSearchAdapter;
import com.belajar.submissionuiux.Model.SearchUser;
import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.Network.ApiService;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.Network.ServiceGenerator;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.ViewModel.FollowersViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListUserSearchAdapter listUserSearchAdapter;
    private List<User> users = new ArrayList<>();
    private FollowersViewModel followersViewModel;

    public static final String TAG = FollowersFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        setRecyclerView();
        getFollowers();

    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        listUserSearchAdapter = new ListUserSearchAdapter(getContext(), users);
        recyclerView.setAdapter(listUserSearchAdapter);
        listUserSearchAdapter.notifyDataSetChanged();
    }

    private void getFollowers() {
        Log.i(TAG, "getFollowers");

        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Const.BUNDLE_DATA);
        followersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);
        followersViewModel.getUserFollower().observe(Objects.requireNonNull(getActivity()), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listUserSearchAdapter.setData(users);
            }
        });
        followersViewModel.setDataFollower(username);
    }
}