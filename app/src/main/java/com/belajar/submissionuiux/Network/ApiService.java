package com.belajar.submissionuiux.Network;

import com.belajar.submissionuiux.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUser(
            @Header("Authentication") String token
    );
}
