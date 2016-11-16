package com.example.aishna.imagegallery.api;

import com.example.aishna.imagegallery.fragments.ImageSearchFragment;
import com.example.aishna.imagegallery.model.flickr.FlickrResponse;
import com.example.aishna.imagegallery.model.imgur.ImgurResponse;

import java.io.IOException;

/**
 * Created by Aishna on 02/03/16.
 */
public class ApiManager {
    public static ImgurResponse getImgurResponseByApiCall() {
        ImgurResponse response = null;
        try {
            response = ImageSearchFragment.runImgur();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static FlickrResponse getFlickPhotosByApiCall() {
        FlickrResponse response = null;
        try {
            response = ImageSearchFragment.runFlickr();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
