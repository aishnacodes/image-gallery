package com.example.aishna.imagegallery.realm.imgur;

import io.realm.RealmObject;

public class RealmImgurItem extends RealmObject {

    private boolean animated = false;
    private String link = null;
    private boolean isAlbum = false;

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(boolean isAlbum) {
        this.isAlbum = isAlbum;
    }


}
