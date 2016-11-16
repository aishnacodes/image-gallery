package com.example.aishna.imagegallery.model.imgur;

import com.google.gson.annotations.SerializedName;

public class ImgurItem {
    public boolean animated = false;
    public String link = null;
    @SerializedName("is_album")
    public boolean isAlbum = false;

    public ImgurItem(boolean animated, String link, boolean isAlbum) {
        this.animated = animated;
        this.link = link;
        this.isAlbum = isAlbum;
    }
}

