package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplyEvent {
    @SerializedName("payType")
    @Expose
    private String payType;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("sessionIds")
    @Expose
    private List<String> sessionIds = null;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<String> getSessionIds() {
        return sessionIds;
    }

    public void setSessionIds(List<String> sessionIds) {
        this.sessionIds = sessionIds;
    }
    public ApplyEvent(String payType, String eventId, List<String> sessionIds) {
        this.eventId = eventId;
        this.payType = payType;
        this.sessionIds = sessionIds;
    }
}
