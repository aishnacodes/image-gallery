package com.example.aishna.imagegallery.utils;

import com.example.aishna.imagegallery.model.flickr.Photos;
import com.example.aishna.imagegallery.model.imgur.ImgurData;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Aishna on 02/03/16.
 */
public class FilterUtils {
    public static void addImgurImages(List<String> imageList, ImgurData imgurData) {
        try {
            List<String> filteredList = LinksUtils.getImgurLinks(imgurData.imgurItems);
            if (filteredList.size() > 0) {
                imageList.addAll(filteredList);
            }
        } catch (Exception e) {
            Timber.e(e, "Imgur Exception");
        }
    }

    public static void addFlickrImages(List<String> imageList, Photos photos) {
        try {
            List<String> filteredList = LinksUtils.getFlickrLinks(photos.photoList);
            if (filteredList.size() > 0) {
                imageList.addAll(filteredList);
            }
        } catch (Exception e) {
            Timber.e(e, "Flickr Exception");
        }
    }
}
