package com.example.myapplication.model.DetailEvent;

import com.example.myapplication.model.ListEvent.EventCategory;
import com.example.myapplication.model.ListEvent.Session;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestEvent {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("typeOfEvent")
    @Expose
    private String typeOfEvent;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("urlWeb")
    @Expose
    private String urlWeb;
    @SerializedName("session")
    @Expose
    private List<Session> session = null;
    @SerializedName("isSellTicket")
    @Expose
    private Boolean isSellTicket;
    @SerializedName("bannerUrl")
    @Expose
    private String bannerUrl;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("isPreview")
    @Expose
    private Boolean isPreview;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("eventCategory")
    @Expose
    private EventCategory eventCategory;
    //    27/06
//    add new field
    @SerializedName("domain")
    @Expose
    private String domain;
    //    add new field 07/07
    @SerializedName("isRequire")
    @Expose
    private Boolean isRequire;
    @SerializedName("isEdit")
    @Expose
    private String isEdit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session = session;
    }

    public Boolean getIsSellTicket() {
        return isSellTicket;
    }

    public void setIsSellTicket(Boolean isSellTicket) {
        this.isSellTicket = isSellTicket;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Boolean getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }
    //    add new field
    public String getDomain(){
        return domain;
    }
    public void setDomain(String domain){
        this.domain = domain;
    }
    //    add new field
    public Boolean getIsRequire() {
        return isRequire;
    }

    public void setIsRequire(Boolean isRequire) {
        this.isRequire = isRequire;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

}
