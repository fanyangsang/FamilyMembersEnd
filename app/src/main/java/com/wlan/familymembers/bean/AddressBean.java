package com.wlan.familymembers.bean;

import java.io.Serializable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/28.
 */

public class AddressBean implements Serializable {


    /**
     * id : 1537265884650
     * name : laolang
     * address : earth
     * addressServer : 河南省郑州市
     * lon : 123.56
     * lat : 56.123
     * phone : 13569091001
     * defaultAddress : 1
     * userId : 1537263172293
     */

    private String id;
    private String name;
    private String address;
    private String addressServer;
    private String lon;
    private String lat;
    private String phone;
    private int defaultAddress;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressServer() {
        return addressServer;
    }

    public void setAddressServer(String addressServer) {
        this.addressServer = addressServer;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
