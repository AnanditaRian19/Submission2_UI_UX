package com.belajar.submissionuiux.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.Network.ApiService;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.Network.ServiceGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersViewModel extends ViewModel {

    private MutableLiveData<List<User>> mUser = new MutableLiveData<>();

    public LiveData<List<User>> getUserFollower() {
        return mUser;
    }

    public void setDataFollower(String username) {
        ApiService apiService = ServiceGenerator.getConnection()
                .create(ApiService.class);
        Call<List<User>> call = apiService.getFollowers(username, Const.TOKEN);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                if (response.body() != null) {
                    Log.i("Success", String.valueOf(response.body()));
                    mUser.setValue(response.body());
                } else {
                    Log.e("Failed", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                Log.e("FAIL TO GET DATA", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
