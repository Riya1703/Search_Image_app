package com.example.searchimageapp.network;

import com.example.searchimageapp.network.response.ImageSearchBaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("3/gallery/search/{page}")
    Call<ImageSearchBaseResponse> getImages(@Path(value = "page") Integer page, @Query("q") String imageName);

}
