package com.example.aishna.imagegallery.model.flickr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rsp")
public class FlickrResponse {

    @Element(name = "photos")
    public Photos photos;
    @Attribute
    public String stat;
}




