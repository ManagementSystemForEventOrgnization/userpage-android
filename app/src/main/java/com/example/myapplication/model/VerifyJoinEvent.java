package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyJoinEvent {
    @SerializedName("qrcode")
    @Expose
    private String qrcode;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("sessionId")
    @Expose
    private String sessionId ;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String  sessionId) {
        this.sessionId = sessionId;
    }
    public VerifyJoinEvent(String qrcode, String eventId, String sessionId) {
        this.eventId = eventId;
        this.qrcode = qrcode;
        this.sessionId = sessionId;
    }

}
