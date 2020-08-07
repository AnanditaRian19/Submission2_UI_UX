package com.belajar.submissionuiux.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.belajar.submissionuiux.Model.UserDetail;
import com.belajar.submissionuiux.Network.ApiService;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.Network.ServiceGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<UserDetail> mUserDetail = new MutableLiveData<>();
    public static final String TAG = DetailViewModel.class.getSimpleName();

    public LiveData<UserDetail> getUserDetail() {
        return mUserDetail;
    }

    public void setUserDetail(String username) {
        ApiService apiService = ServiceGenerator.getConnection()
                .create(ApiService.class);
        Call<UserDetail> call = apiService.getUserDetail(username, Const.TOKEN);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(@NotNull Call<UserDetail> call, @NotNull Response<UserDetail> response) {
                if (response.body() != null) {
                    UserDetail userDetail = response.body();
                    mUserDetail.setValue(userDetail);
                    Log.i(TAG, "Success");
                } else {
                    Log.i(TAG, "Failed");
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserDetail> call, @NotNull Throwable t) {
                Log.e("FAIL TO GET DATA", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
