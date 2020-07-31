package com.belajar.submissionuiux.Network;

import com.belajar.submissionuiux.Model.SearchUser;
import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.Model.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/users")
    Call<SearchUser> getSearchUser(
            @Query("q") String username,
            @Header("Authorization") String token
    );

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(
            @Path("username") String username,
            @Header("Authorization")  String token
    );

    @GET("users/{username}/followers")
    Call<List<User>> getFollowers (
      @Path("username") String username,
      @Header("Authorization") String token
    );
}
