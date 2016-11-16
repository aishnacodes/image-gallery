package com.example.aishna.imagegallery.model.flickr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Photos {

    @ElementList(inline = true, required = false)
    public List<Photo> photoList;
    @Attribute
    public int page;
    @Attribute
    public int pages;
    @Attribute
    public int perpage;
    @Attribute
    public long total;
}
