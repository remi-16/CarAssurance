package com.carassurance.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class IncidentEntityF {

    private String id;

    private String client;

    private String car_id;

    private String location;

    private String date;

    private String type;

    private Boolean injured;

    private String urlMoreInfo;

    private String description;

    private String status;


    public IncidentEntityF() {

    }

    public IncidentEntityF(String client, String car_id, String location, String date, String type, Boolean injured, String urlMoreInfo, String description) {
        this.client = client;
        this.car_id = car_id;
        this.location = location;
        this.date = date;
        this.type = type;
        this.injured = injured;
        this.urlMoreInfo = urlMoreInfo;
        this.description = description;
        this.status = "Ouvert";
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Exclude
    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getInjured() {
        return injured;
    }

    public void setInjured(Boolean injured) {
        this.injured = injured;
    }

    public String getUrlMoreInfo() {
        return urlMoreInfo;
    }

    public void setUrlMoreInfo(String urlMoreInfo) {
        this.urlMoreInfo = urlMoreInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof IncidentEntityF)) return false;
        IncidentEntityF o = (IncidentEntityF) obj;
        return o.getId().equals(this.getId());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("location", location);
        result.put("date", date);
        result.put("type", type);
        result.put("injured", injured);
        result.put("urlMoreInfo", urlMoreInfo);
        result.put("description", description);
        result.put("status", status);

        return result;
    }

}
