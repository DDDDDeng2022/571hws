package com.example.yelp;

public class resultJson {
    public String num;
    public String image;
    public String name;
    public String rates;
    public String distance;
    public String id;
    public resultJson(String num,String image,String name,String rates,String distance,String id){
        this.num=num;
        this.name=name;
        this.image=image;
        this.rates=rates;
        this.distance=distance;
        this.id=id;
    }
    public String getNum(){
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRates() {
        return rates;
    }

    public void setRate(String rate) {
        this.rates = rate;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){this.id=id;}
}
