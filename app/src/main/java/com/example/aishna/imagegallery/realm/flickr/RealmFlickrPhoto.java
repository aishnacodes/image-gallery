package com.example.aishna.imagegallery.realm.flickr;


import io.realm.RealmObject;

public class RealmFlickrPhoto extends RealmObject {

    private String farm;
    private String server;
    private String id;
    private String secret;
    private String owner;
    private String title;
    private String isPublic;
    private String isFriend;
    private String isFamily;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(String isFamily) {
        this.isFamily = isFamily;
    }
}
