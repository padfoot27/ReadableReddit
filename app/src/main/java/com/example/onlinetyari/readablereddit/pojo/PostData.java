package com.example.onlinetyari.readablereddit.pojo;

import com.example.onlinetyari.readablereddit.pojo.ImagePreview;
import com.google.gson.annotations.Expose;

/**
 * Created by Siddharth Verma on 24/4/16.
 */
public class PostData {

    @Expose
    public String domain;
    @Expose
    public String selftext;
    @Expose
    public String id;
    @Expose
    public String name;
    @Expose
    public Integer score;
    @Expose
    public Integer downs;
    @Expose
    public String permalink;
    @Expose
    public String url;
    @Expose
    public String title;
    @Expose
    public String num_comments;
    @Expose
    public Integer ups;
    @Expose
    public String post_hint;
    @Expose
    public ImagePreview preview;
}
