package com.belajar.submissionuiux.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.belajar.submissionuiux.Model.SearchUser;
import com.belajar.submissionuiux.Network.ApiService;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.Network.ServiceGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchViewModel extends ViewModel {

    private MutableLiveData<SearchUser> mUser = new MutableLiveData<>();

    public void setUserSearch(String username) {
        ApiService apiService = ServiceGenerator.getConnection()
                .create(ApiService.class);
        Call<SearchUser> call = apiService.getSearchUser(username, Const.TOKEN);
        call.enqueue(new Callback<SearchUser>() {
            @Override
            public void onResponse(@NotNull Call<SearchUser> call, @NotNull Response<SearchUser> response) {

                if (response.body() != null) {
                    mUser.setValue(response.body());
                    Log.i("Success", String.valueOf(response.body()));
                } else {
                    Log.i("Failed", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<SearchUser> call, @NotNull Throwable t) {
                Log.e("Fail To Get Data: ", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    public LiveData<SearchUser> getSearchUser() {
        return mUser;
    }
}
