package com.example.aishna.imagegallery.model.imgur;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImgurData {
    @SerializedName("items")
    public List<ImgurItem> imgurItems;
}
