package com.carassurance.database.entity;

import android.text.Editable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity (tableName = "cars",
        foreignKeys =
        @ForeignKey(
                entity = UserEntity.class,
                parentColumns = "email",
                childColumns = "owner",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"owner"}
                )})

public class CarEntity {
    @PrimaryKey (autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "number_plate")
    private String  plate;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "owner")
    private String owner;

    @Ignore
    public CarEntity(){

    }

    public CarEntity(String plate, String brand, String model, String color, String owner) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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
}
