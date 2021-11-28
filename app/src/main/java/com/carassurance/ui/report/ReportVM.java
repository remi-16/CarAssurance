package com.carassurance.ui.report;

import android.content.ClipData;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class ReportVM extends ViewModel {

    private String client;
    private Long car_id;
    private String location;
    private String date;
    private String type;
    private Boolean injured;
    private String urlMoreInfo;
    private String description;
    private String status;
    private int actualFragment;

    public int getActualFragment() {
        return actualFragment;
    }

    public void setActualFragment(int actualFragment) {
        this.actualFragment = actualFragment;
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

    private final MutableLiveData<ClipData.Item> selectedItem = new MutableLiveData<ClipData.Item>();

    public void selectItem(ClipData.Item item) {
        selectedItem.setValue(item);
    }
    public LiveData<ClipData.Item> getSelectedItem() {
        return selectedItem;
    }


}

