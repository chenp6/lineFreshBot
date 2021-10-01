package com.example.bot.spring.StoreInfo;

public class Store {
    String photoURI;
    Integer star;
    String address;
    String phoneNumber;
    String workTime;
    String hotspot;
    String sms1922;
    String storeName;
    public Store(){
        super();
    }
    public Store(String storeName, String photoURI, Integer star, String address,String phoneNumber,String workTime,String hotspot, String sms1922){
        super();
        this.storeName = storeName;
        this.photoURI = photoURI;
        this.star = star;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.workTime = workTime;
        this.hotspot = hotspot;
        this.sms1922 = sms1922;
    }
}
