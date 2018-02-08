package com.monika.chatdemo.Holder;

/**
 * Created by Monika on 2/8/2018.
 */
public class SingleUser {
    private String name, statusMessage, status,profile;
    private long id;

    public SingleUser(String name, long id, String statusmessage, String status, String profile) {
        this.name = name;
        this.id = id;
        this.statusMessage = statusmessage;
        this.status = status;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}

