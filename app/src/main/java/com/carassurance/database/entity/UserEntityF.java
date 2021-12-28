package com.carassurance.database.entity;

import androidx.annotation.NonNull;


//@Entity(tableName = "users", primaryKeys = {"email"})
public class UserEntityF {


    private String email;
    private String firstname;
    private String lastname;


    public UserEntityF(){
    }

    public UserEntityF(@NonNull String email, @NonNull String lastname, @NonNull String firstname) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
    }


    @NonNull
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



    @Override
    public boolean equals (Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof UserEntityF)) return false;
        UserEntityF user = (UserEntityF) object;
        if (this.email.equals(user.getEmail())) {
            return  true;
        }

      return false;
    }


}
