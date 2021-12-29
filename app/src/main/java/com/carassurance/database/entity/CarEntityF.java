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


public class CarEntityF {

    private String id;


    private String  plate;


    private String brand;


    private String model;


    private String color;

    private String owner;


    public CarEntityF(){

    }

    public CarEntityF(String plate, String brand, String model, String color, String owner) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;

    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Exclude
    public String getOwner() {
        return owner;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CarEntityF)) return false;
        CarEntityF o = (CarEntityF) obj;
        if (o.plate == plate) return true;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return plate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("plate", plate);
        result.put("brand", brand);
        result.put("model", model);
        result.put("color", color);

        return result;
    }

}
