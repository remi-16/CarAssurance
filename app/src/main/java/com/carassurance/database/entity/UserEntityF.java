package com.carassurance.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


//@Entity(tableName = "users", primaryKeys = {"email"})
public class UserEntityF implements Comparable{


    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;


    public UserEntityF(){
    }

    public UserEntityF(@NonNull String email, @NonNull String lastname, @NonNull String firstname, String password) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;

    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstname);
        result.put("lastName", lastname);
        result.put("email", email);

        return result;
    }


}
