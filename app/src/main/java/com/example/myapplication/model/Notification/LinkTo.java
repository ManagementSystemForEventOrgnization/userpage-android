package com.example.myapplication.model.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkTo {
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("urlWeb")
    @Expose
    private String urlWeb;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }
}
