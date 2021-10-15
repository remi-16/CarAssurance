package com.carassurance.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class UserEntity {
    @PrimaryKey
    private int user_id;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "password")
    private String password;

    private ArrayList<CarEntity> cars;



    public UserEntity(String email, String lastname, String firstname, String password) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<CarEntity> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CarEntity> cars) {
        this.cars = cars;
    }


}
