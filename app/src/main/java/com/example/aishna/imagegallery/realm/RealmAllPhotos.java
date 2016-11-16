package com.example.aishna.imagegallery.realm;

import com.example.aishna.imagegallery.realm.flickr.RealmFlickrPhotos;
import com.example.aishna.imagegallery.realm.imgur.RealmImgurData;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmAllPhotos extends RealmObject {

    @PrimaryKey
    private String tag;
    private RealmImgurData realmImgurData;
    private RealmFlickrPhotos realmFlickrPhotos;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public RealmImgurData getRealmImgurData() {
        return realmImgurData;
    }

    public void setRealmImgurData(RealmImgurData imgurData) {
        this.realmImgurData = imgurData;
    }

    public RealmFlickrPhotos getRealmFlickrPhotos() {
        return realmFlickrPhotos;
    }

    public void setRealmFlickrPhotos(RealmFlickrPhotos flickrData) {
        this.realmFlickrPhotos = flickrData;
    }
}
