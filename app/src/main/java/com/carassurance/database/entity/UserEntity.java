package com.carassurance.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;


@Entity(tableName = "users", primaryKeys = {"email"})
public class UserEntity {

    @NonNull
    private String email;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "password")
    private String password;




    @Ignore
    public UserEntity(){

    }
    public UserEntity(@NonNull String email, @NonNull String lastname, @NonNull String firstname, @NonNull String password) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail( @NonNull String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(@NonNull String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname( @NonNull String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

   /* public ArrayList<CarEntity> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CarEntity> cars) {
        this.cars = cars;
    }*/

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            UserEntity user = (UserEntity) object;
            if (this.email.equals(user.getEmail()) && this.password.equals(user.getPassword())) {
                result = true;
            }
        }
        return result;
    }


}
