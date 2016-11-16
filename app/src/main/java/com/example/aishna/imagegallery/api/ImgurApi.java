package com.example.aishna.imagegallery.api;

import com.example.aishna.imagegallery.model.imgur.ImgurResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ImgurApi {
    @Headers("Authorization:Client-ID f9a19403c9ffd47")
    @GET("3/gallery/t/{tag}/top/year")
    Call<ImgurResponse> getPhotos(@Path("tag") String tag);
}