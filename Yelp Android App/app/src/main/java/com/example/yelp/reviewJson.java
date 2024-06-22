package com.example.yelp;

public class reviewJson {
    public String name;
    public String rating;
    public String text;
    public String time;
    public reviewJson(String name, String rating, String text, String time){
        this.name=name;
        this.rating=rating;
        this.text=text;
        this.time=time;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){this.time=time;}
}
