package com.example.yelp;

import java.util.List;

public class DetailJson {
    public String id;
    public String address;
    public String category;
    public String phone;
    public String price;
    public String open;
    public String url;
    public String name;
    public List<String> photo;
    public double latitude;
    public  double longitude;

    public DetailJson(String id,String address, String category, String phone, String price, String open, String url,List<String> photo,double latitude, double longitude,String name){
        this.id=id;
        this.name=name;
        this.address=address;
        this.category=category;
        this.phone=phone;
        this.price=price;
        this.open=open;
        this.url=url;
        this.photo=photo;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String rate) {
        this.open = open;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String distance) {
        this.price = price;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String id){this.url=url;}
    public List<String> getPhoto(){
        return photo;
    }
    public void setPhoto(List<String> photo){this.photo=photo;}
    public double getLatitude(){
        return latitude;
    }
    public void setLatitude(double photo){this.latitude=latitude;}
    public double getLongitude(){
        return longitude;
    }
    public void setLongitude(double longitude){this.longitude=longitude;}
}
