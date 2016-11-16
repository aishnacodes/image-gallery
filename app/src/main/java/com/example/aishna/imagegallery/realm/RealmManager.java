package com.example.aishna.imagegallery.realm;

import android.content.Context;

import com.example.aishna.imagegallery.model.flickr.Photo;
import com.example.aishna.imagegallery.model.flickr.Photos;
import com.example.aishna.imagegallery.model.imgur.ImgurData;
import com.example.aishna.imagegallery.model.imgur.ImgurItem;
import com.example.aishna.imagegallery.realm.flickr.RealmFlickrPhoto;
import com.example.aishna.imagegallery.realm.flickr.RealmFlickrPhotos;
import com.example.aishna.imagegallery.realm.imgur.RealmImgurData;
import com.example.aishna.imagegallery.realm.imgur.RealmImgurItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Aishna on 26/02/16.
 */
public class RealmManager {

    //Converting Imgur to RealmImgur
    public static RealmImgurItem getRealmItemFromItem(ImgurItem imgurItem) {

        RealmImgurItem realmImgurItem = new RealmImgurItem();

        realmImgurItem.setLink(imgurItem.link);
        realmImgurItem.setAnimated(imgurItem.animated);
        realmImgurItem.setIsAlbum(imgurItem.isAlbum);

        return realmImgurItem;
    }

    private static RealmList<RealmImgurItem> getRealmImgurItemListfromItems(List<ImgurItem> imgurItems) {
        RealmList<RealmImgurItem> realmImgurItems = new RealmList<>();

        for (ImgurItem imgurItem : imgurItems) {
            RealmImgurItem realmImgurItem = getRealmItemFromItem(imgurItem);
            realmImgurItems.add(realmImgurItem);
        }

        return realmImgurItems;
    }

    private static RealmImgurData getRealmImgurDataFromData(ImgurData imgurData) {
        RealmImgurData realmImgurData = new RealmImgurData();

        RealmList<RealmImgurItem> realmImgurItems = getRealmImgurItemListfromItems(imgurData.imgurItems);
        realmImgurData.setItems(realmImgurItems);

        return realmImgurData;
    }

    //Converting RealmImgur to Imgur
    public static ImgurItem getItemFromRealmItem(RealmImgurItem realmImgurItem) {

        String link = realmImgurItem.getLink();
        Boolean animated = realmImgurItem.isAnimated();
        Boolean isAlbum = realmImgurItem.isAlbum();
        ImgurItem imgurItem = new ImgurItem(animated, link, isAlbum);

        return imgurItem;
    }

    private static List<ImgurItem> getImgurItemListfromRealmItems(RealmList<RealmImgurItem> realmImgurItems) {

        List<ImgurItem> imgurItems = new ArrayList<>();

        for (RealmImgurItem realmImgurItem : realmImgurItems) {
            ImgurItem imgurItem = getItemFromRealmItem(realmImgurItem);
            imgurItems.add(imgurItem);
        }

        return imgurItems;
    }

    public static ImgurData getImgurDataFromRealmImgurData(RealmImgurData realmImgurData) {
        ImgurData imgurData = new ImgurData();
        if (realmImgurData != null) {
            List<ImgurItem> imgurItems = getImgurItemListfromRealmItems(realmImgurData.getItems());
            imgurData.imgurItems = imgurItems;
        }
        return imgurData;
    }

    //Converting RealmFlickr to Flickr
    public static Photo getPhotoFromRealmPhoto(RealmFlickrPhoto realmFlickrPhoto) {

        Photo photo = new Photo();
        photo.farm = realmFlickrPhoto.getFarm();
        photo.id = realmFlickrPhoto.getId();
        photo.server = realmFlickrPhoto.getServer();
        photo.secret = realmFlickrPhoto.getSecret();
        return photo;
    }

    private static List<Photo> getFlickrPhotoListfromRealmPhoto(RealmList<RealmFlickrPhoto> realmFlickrPhotos) {

        List<Photo> flickrPhotos = new ArrayList<>();
        for (RealmFlickrPhoto realmFlickrPhoto : realmFlickrPhotos) {
            Photo photo = getPhotoFromRealmPhoto(realmFlickrPhoto);
            flickrPhotos.add(photo);
        }
        return flickrPhotos;
    }

    public static Photos getFlickrPhotosFromRealmFlickrPhotos(RealmFlickrPhotos realmFlickrPhotos) {

        Photos photos = new Photos();
        if (realmFlickrPhotos != null) {
            List<Photo> photoList = getFlickrPhotoListfromRealmPhoto(realmFlickrPhotos.getPhotoList());
            photos.photoList = photoList;
        }
        return photos;
    }

    //Converting Flickr to RealmFlickr
    public static RealmFlickrPhoto getRealmPhotoFromPhoto(Photo photo) {

        RealmFlickrPhoto realmFlickrPhoto = new RealmFlickrPhoto();

        realmFlickrPhoto.setSecret(photo.secret);
        realmFlickrPhoto.setServer(photo.server);
        realmFlickrPhoto.setFarm(photo.farm);
        realmFlickrPhoto.setId(photo.id);

        return realmFlickrPhoto;
    }

    private static RealmList<RealmFlickrPhoto> getRealmFlickrPhotoListfromPhoto(List<Photo> photoList) {

        RealmList<RealmFlickrPhoto> realmFlickrPhotos = new RealmList<>();

        for (Photo photo : photoList) {
            RealmFlickrPhoto realmFlickrPhoto = getRealmPhotoFromPhoto(photo);
            realmFlickrPhotos.add(realmFlickrPhoto);
        }
        return realmFlickrPhotos;
    }

    private static RealmFlickrPhotos getRealmFlickrPhotosFromPhotos(Photos photos) {

        RealmFlickrPhotos realmFlickrPhotos = new RealmFlickrPhotos();
        RealmList<RealmFlickrPhoto> realmFlickrPhotolist = getRealmFlickrPhotoListfromPhoto(photos.photoList);
        realmFlickrPhotos.setPhotoList(realmFlickrPhotolist);

        return realmFlickrPhotos;
    }

    public static void saveImgurData(RealmAllPhotos realmAllPhotos, ImgurData imgurData) {

        realmAllPhotos.setRealmImgurData(getRealmImgurDataFromData(imgurData));

    }

    public static void saveFlickrPhotos(RealmAllPhotos realmAllPhotos, Photos photos) {

        realmAllPhotos.setRealmFlickrPhotos(getRealmFlickrPhotosFromPhotos(photos));
    }

    public static void saveRealmAllPhotosObject(RealmAllPhotos realmAllPhotos, Context context) {

        Realm realm = getRealm(context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmAllPhotos);
        realm.commitTransaction();
    }

    public static RealmAllPhotos getRealmObject(String tag, Context context) {
        RealmResults<RealmAllPhotos> search = getRealm(context).where(RealmAllPhotos.class).equalTo("tag", tag).findAll();
        if (search.size() > 0) {
            return search.get(0);
        }
        return null;
    }

    public static String[] getSearchHistory(Context context) {
        RealmResults<RealmAllPhotos> search = getRealm(context).where(RealmAllPhotos.class).findAll();
        int size = search.size();
        if (size > 0) {
            String[] history = new String[size];
            for (int i = 0; i < size; i++) {
                history[i] = (search.get(i).getTag());
            }
            return history;
        } else {
            return null;
        }
    }

    private static Realm getRealm(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfig);
        return realm;
    }
}
