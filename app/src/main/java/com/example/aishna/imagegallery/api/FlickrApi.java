package com.example.aishna.imagegallery.api;

import com.example.aishna.imagegallery.model.flickr.FlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
    @GET("services/rest/?method=flickr.photos.search&api_key=dca12cd05d9f45baa58d5bea4c122ea5&sort=relevance&format=rest")
    Call<FlickrResponse> getPhotos(@Query("tags") String tag);
}
