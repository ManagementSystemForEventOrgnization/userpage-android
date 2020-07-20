package com.example.myapplication.model.ListEvent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("map")
    @Expose
    private Map map;
//    27/06
//  add new field
    @SerializedName("detailImage")
    @Expose
    private String detailImage;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    public String getDetailImage() {
        return detailImage;
    }

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

}
