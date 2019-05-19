package com.example.eatapp.Model;

import java.util.List;
//class that will be saved in my firebase database as the order that need to be delivered
public class Requset {
    private String Phone;
    private String Name;
    private String Address;
    private String total;
    private List<Order> Food;

    public Requset(String phone, String name, String address, String total, List<Order> food) {
        Phone = phone;
        Name = name;
        Address = address;
        this.total = total;
        Food = food;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFood() {
        return Food;
    }

    public void setFood(List<Order> food) {
        Food = food;
    }
}
