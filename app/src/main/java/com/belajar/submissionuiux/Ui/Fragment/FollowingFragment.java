package com.belajar.submissionuiux.Ui.Fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.belajar.submissionuiux.Adapter.ListUserSearchAdapter;
import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.Ui.Activity.DetailActivity;
import com.belajar.submissionuiux.ViewModel.FollowingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FollowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListUserSearchAdapter listUserSearchAdapter;
    private FollowingViewModel followingViewModel;
    private List<User> mUser = new ArrayList<>();

    public static final String TAG = FollowingViewModel.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        setRecyclerView();
        getFollowing();
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        listUserSearchAdapter = new ListUserSearchAdapter(getContext(), mUser);
        recyclerView.setAdapter(listUserSearchAdapter);
        listUserSearchAdapter.notifyDataSetChanged();

        listUserSearchAdapter.setOnItemClickCallback(new ListUserSearchAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(User user, int posistion) {
                Intent mIntent = new Intent(getContext(), DetailActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle mBundle = new Bundle();
                mBundle.putString(Const.BUNDLE_DATA, user.getName());
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }

    private void getFollowing() {
        Log.i(TAG, "getFollowing");

        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Const.BUNDLE_DATA);
        followingViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);
        followingViewModel.getUser().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listUserSearchAdapter.setData(users);
            }
        });

        followingViewModel.setDataFollowing(username);
    }
}