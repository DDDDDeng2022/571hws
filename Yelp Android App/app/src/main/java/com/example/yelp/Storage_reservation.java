package com.example.yelp;


public class Storage_reservation {
    public String id;
    public String email;
    public String time;
    public String date;
    public String name;



    public Storage_reservation(String id, String email, String date, String time, String name){
        this.id=id;
        this.date=date;
        this.email=email;
        this.time=time;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }



    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
