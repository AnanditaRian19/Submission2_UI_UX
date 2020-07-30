package com.belajar.submissionuiux.Network;

import com.belajar.submissionuiux.Model.SearchUser;
import com.belajar.submissionuiux.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUser(
            @Header("Authorization") String token
    );

    @GET("search/users")
    Call<SearchUser> getSearchUser(
            @Query("q") String username,
            @Header("Authorization") String token
    );
}
