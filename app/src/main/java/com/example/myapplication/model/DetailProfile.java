package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailProfile {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("googleId")
    @Expose
    private String googleId;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("name")
    @Expose
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailProfile (String googleId, String email, String  imageUrl, String name) {
        this.googleId = googleId;
        this.email = email;
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
