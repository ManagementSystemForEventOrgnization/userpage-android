package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Profile {
    @SerializedName("profile")
    @Expose
    private DetailProfile profile;

    public DetailProfile getProfile() {
        return profile;
    }

    public void setProfile(DetailProfile profile) {
        this.profile = profile;
    }
    public Profile (DetailProfile profile) {
        this.profile = profile;
    }

}
