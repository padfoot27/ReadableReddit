package com.example.onlinetyari.readablereddit.pojo;

import com.example.onlinetyari.readablereddit.pojo.Source;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Siddharth Verma on 24/4/16.
 */
public class Image {

    @Expose
    public Source source;

    @Expose
    public List<Source> resolutions;
}
