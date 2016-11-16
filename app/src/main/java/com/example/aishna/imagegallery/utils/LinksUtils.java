package com.example.aishna.imagegallery.utils;

import com.example.aishna.imagegallery.model.flickr.Photo;
import com.example.aishna.imagegallery.model.imgur.ImgurItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aishna on 25/02/16.
 */
public abstract class LinksUtils {

    public static List<String> getImgurLinks(List<ImgurItem> items) {
        List<String> list = new ArrayList<>();
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                //We only want images not albums
                //We don't want GIFs so animated should be false
                if (!items.get(i).isAlbum && !items.get(i).animated) {
                    list.add(items.get(i).link);
                }
            }
        }
        return list;
    }

    public static List<String> getFlickrLinks(List<Photo> photos) {
        List<String> list = new ArrayList<>();
        if (photos != null) {
            List<Photo> photoList = photos;
            for (int i = 0; i < photoList.size(); i++) {
                list.add("https://farm" + photoList.get(i).farm + ".staticflickr.com/" + photoList.get(i).server +
                        "/" + photoList.get(i).id + "_" + photoList.get(i).secret + ".jpg");
            }
        }
        return list;
    }
}