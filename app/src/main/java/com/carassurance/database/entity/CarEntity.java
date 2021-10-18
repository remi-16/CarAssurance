package com.carassurance.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity (foreignKeys = @ForeignKey(entity = UserEntity.class, parentColumns = "user_id", childColumns = "owner_id"))
public class CarEntity {
    @PrimaryKey
    private int car_id;

    @ColumnInfo(name = "number_plate")
    private String  plate;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "owner_id")
    private int owner_id;

    public CarEntity(String plate, String brand, String model, String color, int owner_id) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.owner_id = owner_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public int getOwner_id() {
        return owner_id;
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
}
