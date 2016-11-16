package com.example.aishna.imagegallery.model.flickr;


import org.simpleframework.xml.Attribute;

public class Photo {

    @Attribute
    public String farm;
    @Attribute
    public String server;
    @Attribute
    public String id;
    @Attribute
    public String secret;
    @Attribute
    public String owner;
    @Attribute
    public String title;
    @Attribute(name = "ispublic")
    public String isPublic;
    @Attribute(name = "isfriend")
    public String isFriend;
    @Attribute(name = "isfamily")
    public String isFamily;

}