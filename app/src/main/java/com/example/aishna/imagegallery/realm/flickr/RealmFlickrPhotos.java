package com.example.aishna.imagegallery.realm.flickr;

import io.realm.RealmList;
import io.realm.RealmObject;


public class RealmFlickrPhotos extends RealmObject {

    private RealmList<RealmFlickrPhoto> photoList;
    private int page;
    private int pages;
    private int perpage;
    private long total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public RealmList<RealmFlickrPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(RealmList<RealmFlickrPhoto> photoList) {
        this.photoList = photoList;
    }
}
