package com.example.aishna.imagegallery.integration;


import com.example.aishna.imagegallery.api.FlickrApi;
import com.example.aishna.imagegallery.model.flickr.FlickrResponse;
import com.example.aishna.imagegallery.model.flickr.Photo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.google.common.truth.Truth.assert_;

public class FlickrTests {
    private FlickrApi flickrApi;

    @Before
    public void init() {
        flickrApi = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(FlickrApi.class);
    }

    @Test
    public void getFlickrPhotos() {

        String tag = "dogs";
        Call<FlickrResponse> call = flickrApi.getPhotos(tag);
        FlickrResponse response = null;
        try {
            response = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }

        assert_().that(response).isNotNull();
        assert_().that(response.photos).isNotNull();
        assert_().that(response.photos.photoList).isNotNull();

        List<Photo> items = response.photos.photoList;
        assert_().that(items.size()).isEqualTo(100);

        Photo photo = items.get(0);
        assert_().that(photo).isNotNull();
    }


}
