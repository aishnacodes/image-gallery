package com.example.aishna.imagegallery.integration;

import com.example.aishna.imagegallery.api.ImgurApi;
import com.example.aishna.imagegallery.model.imgur.ImgurItem;
import com.example.aishna.imagegallery.model.imgur.ImgurResponse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.truth.Truth.assert_;


public class ImgurTests {

    private ImgurApi imgurApi;

    @Before
    public void init() {
        imgurApi = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ImgurApi.class);
    }

    @Test
    public void getImgurPhotos() {

        final String tag = "sky";

        Call<ImgurResponse> call = imgurApi.getPhotos(tag);

        ImgurResponse imgurResponse = null;

        try {
            imgurResponse = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
        assert_().that(imgurResponse).isNotNull();
        assert_().that(imgurResponse.imgurData).isNotNull();
        assert_().that(imgurResponse.imgurData.imgurItems).isNotNull();

        List<ImgurItem> items = imgurResponse.imgurData.imgurItems;
        assert_().that(items.size()).isEqualTo(60);

        ImgurItem item = items.get(0);
        assert_().that(item).isNotNull();

    }
}
