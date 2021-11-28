package com.carassurance.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "incidents",
        foreignKeys = {
                @ForeignKey(
                        entity = UserEntity.class,
                        parentColumns = "email",
                        childColumns = "client"
                ),
                @ForeignKey(
                        entity = CarEntity.class,
                        parentColumns = {"id"},
                        childColumns = {"car_id"}
                )
        },
        indices = {
        @Index(value = {"client"}),
        @Index(value = {"car_id"})
})
public class IncidentEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "client")
    private String client;

    @ColumnInfo(name = "car_id")
    private Long car_id;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "injured")
    private Boolean injured;

    @ColumnInfo(name = "urlMoreInfo")
    private String urlMoreInfo;

    @ColumnInfo(name = "Description")
    private String description;

    @ColumnInfo(name = "Status")
    private String status;

    @Ignore
    public IncidentEntity() {

    }

    public IncidentEntity(String client, Long car_id, String location, String date, String type, Boolean injured, String urlMoreInfo, String description) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
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
}
